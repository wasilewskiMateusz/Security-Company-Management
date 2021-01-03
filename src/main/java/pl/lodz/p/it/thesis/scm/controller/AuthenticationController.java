package pl.lodz.p.it.thesis.scm.controller;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import pl.lodz.p.it.thesis.scm.dto.requests.JwtRefreshRequest;
import pl.lodz.p.it.thesis.scm.dto.user.UserAuthenticateDTO;
import pl.lodz.p.it.thesis.scm.dto.responses.JwtAuthenticateResponse;
import pl.lodz.p.it.thesis.scm.dto.responses.JwtRefreshResponse;
import pl.lodz.p.it.thesis.scm.dto.user.UserRegisterDTO;
import pl.lodz.p.it.thesis.scm.dto.user.UserResetPasswordTokenDTO;
import pl.lodz.p.it.thesis.scm.exception.EmailProblemException;
import pl.lodz.p.it.thesis.scm.exception.RestException;
import pl.lodz.p.it.thesis.scm.security.MyUserDetailsService;
import pl.lodz.p.it.thesis.scm.service.IAuthenticationService;
import pl.lodz.p.it.thesis.scm.service.IUserService;
import pl.lodz.p.it.thesis.scm.util.EmailUtil;
import pl.lodz.p.it.thesis.scm.util.JwtUtil;
import pl.lodz.p.it.thesis.scm.util.RestMessage;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@Transactional(propagation = Propagation.NEVER)
public class AuthenticationController {

    private final IUserService userService;
    private final IAuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final EmailUtil emailUtil;


    @Autowired
    public AuthenticationController(IUserService userService, IAuthenticationService authenticationService,
                                    AuthenticationManager authenticationManager,
                                    MyUserDetailsService userDetailsService,
                                    JwtUtil jwtUtil, EmailUtil emailUtil) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.emailUtil = emailUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<RestMessage> registerNewUserAccount(@Valid @RequestBody UserRegisterDTO userRegisterDTO, WebRequest webRequest) {
        authenticationService.registerNewUserAccount(userRegisterDTO);
        try {
            emailUtil.sendRegistrationEmail(userRegisterDTO.getEmail(), webRequest.getLocale());
        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new EmailProblemException();
        }
        return ResponseEntity.ok(new RestMessage("Success"));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtAuthenticateResponse> createAuthenticationToken(@Valid @RequestBody UserAuthenticateDTO userAuthenticateDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthenticateDTO.getEmail(), userAuthenticateDTO.getPassword()));
        UserDetails userDetails;
        try {
           userDetails = userDetailsService.loadUserByUsername(userAuthenticateDTO.getEmail());
        }
        catch (UsernameNotFoundException ex) {
            throw new RestException("Exception.bad.credentials");
        }
        Long id = this.userService.getUserByEmail(userAuthenticateDTO.getEmail()).getId();
        final String accessToken = jwtUtil.generateAccessToken(userDetails, id);
        final String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        LocalDateTime expirationTime = LocalDateTime.ofInstant(jwtUtil.getExpirationDateFromToken(refreshToken).toInstant(), ZoneId.systemDefault());
        authenticationService.registerRefreshToken(refreshToken, userAuthenticateDTO.getEmail(), expirationTime);
        return ResponseEntity.ok(new JwtAuthenticateResponse(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtRefreshResponse> refreshToken(@RequestBody JwtRefreshRequest jwtRefreshRequest) {
        String username;
        try {
             username = jwtUtil.getUsernameFromToken(jwtRefreshRequest.getRefreshToken());
        } catch (JwtException ex) {
            throw new RestException("Exception.refresh.token.is.not.valid");
        }
        if (!authenticationService.checkIfTokenExists(jwtRefreshRequest.getRefreshToken(), username)) {
            throw new RestException("Exception.refresh.token.is.not.valid");
        }
        Long id = userService.getUserByEmail(username).getId();
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(!userDetails.isEnabled()) throw new DisabledException("");
        final String accessToken = jwtUtil.generateAccessToken(userDetails, id);
        return ResponseEntity.ok(new JwtRefreshResponse(accessToken));
    }

    @PostMapping("/removeToken")
    public ResponseEntity<RestMessage> logout(@RequestBody JwtRefreshRequest jwtRefreshRequest,
                                              @RequestHeader("Authorization") String accessToken) {
        String username;
        username = jwtUtil.getUsernameFromToken(accessToken.substring(7));
        authenticationService.logout(jwtRefreshRequest.getRefreshToken(), username);

        return ResponseEntity.ok(new RestMessage("Success"));
    }

    @PostMapping("/forgot_password/{email}")
    public ResponseEntity<RestMessage> processForgotPassword(@PathVariable String email, WebRequest webRequest) {
        String token = userService.updateResetPasswordToken(email);
        String resetPasswordLink = "https://localhost:4200/reset_password?token=" + token;
        try {
            emailUtil.sendResetEmail(email, resetPasswordLink, webRequest.getLocale());
        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new EmailProblemException();
        }

        return ResponseEntity.ok(new RestMessage("Success"));
    }

    @PostMapping("/reset_password")
    public ResponseEntity<RestMessage> processResetPassword(@Valid @RequestBody UserResetPasswordTokenDTO userResetPasswordTokenDTO) {
        userService.updateResetPassword(userResetPasswordTokenDTO);
        return ResponseEntity.ok(new RestMessage("Success"));
    }


}
