package pl.lodz.p.it.thesis.scm.service;

import pl.lodz.p.it.thesis.scm.dto.requests.UserRequest;

public interface IAuthenticationService {

    void registerNewUserAccount(UserRequest userRequest);
    void registerRefreshToken(String refreshToken, String email);
    boolean checkIfTokenExists(String refreshToken, String email);
}
