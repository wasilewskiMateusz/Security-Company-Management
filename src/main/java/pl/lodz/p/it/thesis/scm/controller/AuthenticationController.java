package pl.lodz.p.it.thesis.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.thesis.scm.dto.requests.JwtRefreshRequest;
import pl.lodz.p.it.thesis.scm.dto.requests.JwtRequest;
import pl.lodz.p.it.thesis.scm.dto.requests.UserRequest;
import pl.lodz.p.it.thesis.scm.dto.responses.AppResponse;
import pl.lodz.p.it.thesis.scm.dto.responses.JwtAuthenticateResponse;
import pl.lodz.p.it.thesis.scm.dto.responses.JwtRefreshResponse;
import pl.lodz.p.it.thesis.scm.dto.responses.SuccessResponse;
import pl.lodz.p.it.thesis.scm.exception.RestException;
import pl.lodz.p.it.thesis.scm.security.MyUserDetailsService;
import pl.lodz.p.it.thesis.scm.service.implementation.AuthenticationService;
import pl.lodz.p.it.thesis.scm.util.JwtUtil;

import javax.validation.Valid;

@RestController
public class AuthenticationController {


    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;


    @Autowired
    public AuthenticationController(AuthenticationService authenticationService,
                                    AuthenticationManager authenticationManager,
                                    MyUserDetailsService userDetailsService,
                                    JwtUtil jwtUtil) {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<AppResponse> registerNewUserAccount(@Valid @RequestBody UserRequest userRequest) {
        authenticationService.registerNewUserAccount(userRequest);
        return new ResponseEntity<>(new SuccessResponse("success"), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtAuthenticateResponse> createAuthenticationToken(@Valid @RequestBody JwtRequest jwtRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        final String accessToken = jwtUtil.generateAccessToken(userDetails);
        final String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        authenticationService.registerRefreshToken(refreshToken, jwtRequest.getEmail());
        return ResponseEntity.ok(new JwtAuthenticateResponse(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtRefreshResponse> refreshToken(@RequestBody JwtRefreshRequest jwtRefreshRequest){

        String username = jwtUtil.getUsernameFromToken(jwtRefreshRequest.getRefreshToken());
        if(!authenticationService.checkIfTokenExists(jwtRefreshRequest.getRefreshToken(), username)){
            throw new RestException("Exception.refresh.token.is.not.valid");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String accessToken = jwtUtil.generateAccessToken(userDetails);
        return ResponseEntity.ok(new JwtRefreshResponse(accessToken));
    }


}
