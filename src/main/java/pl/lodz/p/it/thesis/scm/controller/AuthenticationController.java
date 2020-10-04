package pl.lodz.p.it.thesis.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.thesis.scm.dto.requests.JwtRequest;
import pl.lodz.p.it.thesis.scm.dto.requests.UserRequest;
import pl.lodz.p.it.thesis.scm.dto.responses.AppResponse;
import pl.lodz.p.it.thesis.scm.dto.responses.JwtResponse;
import pl.lodz.p.it.thesis.scm.dto.responses.SuccessResponse;
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
    public ResponseEntity<JwtResponse> createAuthenticationToken(@Valid @RequestBody JwtRequest jwtRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

}
