package pl.lodz.p.it.thesis.scm.service;

import pl.lodz.p.it.thesis.scm.model.User;

import java.util.Optional;

public interface IUserService {

    Optional<User> getUser(Long id);
}
