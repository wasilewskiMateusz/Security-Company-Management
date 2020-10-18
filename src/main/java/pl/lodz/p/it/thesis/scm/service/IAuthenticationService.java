package pl.lodz.p.it.thesis.scm.service;

import pl.lodz.p.it.thesis.scm.dto.requests.UserRequest;

import java.time.LocalDateTime;

public interface IAuthenticationService {

    void registerNewUserAccount(UserRequest userRequest);
    void registerRefreshToken(String refreshToken, String email, LocalDateTime expirationTime);
    boolean checkIfTokenExists(String refreshToken, String email);
    void logout(String refreshToken, String email);
}
