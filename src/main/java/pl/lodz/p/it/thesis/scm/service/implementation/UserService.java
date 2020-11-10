package pl.lodz.p.it.thesis.scm.service.implementation;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.thesis.scm.dto.user.*;
import pl.lodz.p.it.thesis.scm.exception.ResourceNotExistException;
import pl.lodz.p.it.thesis.scm.exception.RestException;
import pl.lodz.p.it.thesis.scm.model.Role;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.repository.RoleRepository;
import pl.lodz.p.it.thesis.scm.repository.UserRepository;
import pl.lodz.p.it.thesis.scm.service.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User editUser(User userToEdit, UserEditDTO userEditDTO) {

        userToEdit.setName(userEditDTO.getName());
        userToEdit.setLastName(userEditDTO.getLastName());
        userToEdit.setPhoneNumber(userEditDTO.getPhoneNumber());
        return userRepository.save(userToEdit);
    }

    @Override
    public User changeAvailability(User user, UserAvailabilityDTO userAvailabilityDTO) {
        user.setEnabled(userAvailabilityDTO.isEnable());
        return userRepository.save(user);
    }

    @Override
    public User changePassword(User user, UserPasswordDTO userPasswordDTO) {
        user.setPassword(passwordEncoder.encode(userPasswordDTO.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User changeRoles(User user, UserRoleDTO userRoleDTO) {
        List<Role> newRoles = new ArrayList<>();
        userRoleDTO.getNewRolesIds().forEach(roleId ->
                roleRepository.findById(roleId).ifPresent(newRoles::add)
        );
        user.setRoles(newRoles);
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User changeOwnPassword(Long id, UserOwnPasswordDTO userOwnPasswordDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new ResourceNotExistException();
        }

        User user = userOptional.get();

        String currentVersion = DigestUtils.sha256Hex(user.getVersion().toString());

        if (!userOwnPasswordDTO.getVersion().equals(currentVersion)) {
            throw new RestException("Exception.different.version");
        }

        if (!passwordEncoder.matches(userOwnPasswordDTO.getPreviousPassword(), user.getPassword())) {
            throw new RestException("Exception.bad.previous.password");
        }

        user.setPassword(passwordEncoder.encode(userOwnPasswordDTO.getPassword()));

        return userRepository.save(user);

    }
}
