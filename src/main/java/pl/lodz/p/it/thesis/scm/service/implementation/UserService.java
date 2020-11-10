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
    public User editUser(Long id, UserEditDTO userEditDTO) {

        User user = checkVersion(id, userEditDTO.getVersion());

        user.setName(userEditDTO.getName());
        user.setLastName(userEditDTO.getLastName());
        user.setPhoneNumber(userEditDTO.getPhoneNumber());
        return userRepository.save(user);
    }

    @Override
    public User changeAvailability(Long id, UserAvailabilityDTO userAvailabilityDTO) {
        User user = checkVersion(id, userAvailabilityDTO.getVersion());
        user.setEnabled(userAvailabilityDTO.isEnable());
        return userRepository.save(user);
    }

    @Override
    public User changePassword(Long id, UserPasswordDTO userPasswordDTO) {
        User user = checkVersion(id, userPasswordDTO.getVersion());
        user.setPassword(passwordEncoder.encode(userPasswordDTO.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User changeRoles(Long id, UserRoleDTO userRoleDTO) {

        User user = checkVersion(id, userRoleDTO.getVersion());

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

        User user = checkVersion(id, userOwnPasswordDTO.getVersion());

        if (!passwordEncoder.matches(userOwnPasswordDTO.getPreviousPassword(), user.getPassword())) {
            throw new RestException("Exception.bad.previous.password");
        }

        user.setPassword(passwordEncoder.encode(userOwnPasswordDTO.getPassword()));

        return userRepository.save(user);
    }


    private User checkVersion(Long id, String version) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new ResourceNotExistException();
        }

        User user = userOptional.get();

        String currentVersion = DigestUtils.sha256Hex(user.getVersion().toString());

        if (!version.equals(currentVersion)) {
            throw new RestException("Exception.different.version");
        }

        return user;
    }
}
