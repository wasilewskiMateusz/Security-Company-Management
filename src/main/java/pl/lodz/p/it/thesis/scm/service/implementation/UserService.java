package pl.lodz.p.it.thesis.scm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.thesis.scm.dto.user.UserAvailabilityDTO;
import pl.lodz.p.it.thesis.scm.dto.user.UserEditDTO;
import pl.lodz.p.it.thesis.scm.dto.user.UserPasswordDTO;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.repository.UserRepository;
import pl.lodz.p.it.thesis.scm.service.IUserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getUser(Long id){
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public User editUser(User userToEdit, UserEditDTO userEditDTO){

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
}
