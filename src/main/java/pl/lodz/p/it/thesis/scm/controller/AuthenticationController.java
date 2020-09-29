package pl.lodz.p.it.thesis.scm.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @PostMapping("/register")
    public void registerNewUserAccount() {

    }
}
