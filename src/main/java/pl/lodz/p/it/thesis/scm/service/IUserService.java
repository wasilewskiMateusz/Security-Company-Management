package pl.lodz.p.it.thesis.scm.service;

import pl.lodz.p.it.thesis.scm.dto.user.*;
import pl.lodz.p.it.thesis.scm.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    Optional<User> getUser(Long id);
    List<User> getAllUsers();
    User editUser(Long id, UserEditDTO userEditDTO);
    User changeAvailability(Long id, UserAvailabilityDTO userAvailabilityDTO);
    User changePassword(Long id, UserPasswordDTO userPasswordDTO);
    User changeRoles(Long id, UserRoleDTO userRoleDTO);
    User getUserByEmail(String email);
    User changeOwnPassword(Long id, UserOwnPasswordDTO userOwnPasswordDTO);
}
