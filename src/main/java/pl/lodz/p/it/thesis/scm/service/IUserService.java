package pl.lodz.p.it.thesis.scm.service;

import pl.lodz.p.it.thesis.scm.dto.user.UserAvailabilityDTO;
import pl.lodz.p.it.thesis.scm.dto.user.UserEditDTO;
import pl.lodz.p.it.thesis.scm.dto.user.UserPasswordDTO;
import pl.lodz.p.it.thesis.scm.dto.user.UserRoleDTO;
import pl.lodz.p.it.thesis.scm.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    Optional<User> getUser(Long id);
    List<User> getAllUsers();
    User editUser(User userToEdit, UserEditDTO userEditDTO);
    User changeAvailability(User user, UserAvailabilityDTO userAvailabilityDTO);
    User changePassword(User user, UserPasswordDTO userPasswordDTO);
    User changeRoles(User user, UserRoleDTO userRoleDTO);
    User getUserByEmail(String email);
}
