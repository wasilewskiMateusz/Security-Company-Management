package pl.lodz.p.it.thesis.scm.service;

import pl.lodz.p.it.thesis.scm.dto.requests.UserRequest;

public interface IAuthenticationService {

    void registerNewUserAccount(UserRequest userRequest);
}
