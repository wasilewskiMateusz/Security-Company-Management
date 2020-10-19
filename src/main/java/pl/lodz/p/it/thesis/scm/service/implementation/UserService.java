package pl.lodz.p.it.thesis.scm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.thesis.scm.dto.user.UserDTO;
import pl.lodz.p.it.thesis.scm.dto.user.UserEditDTO;
import pl.lodz.p.it.thesis.scm.exception.RestException;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.repository.UserRepository;
import pl.lodz.p.it.thesis.scm.service.IUserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUser(Long id){
        return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User editUser(User userToEdit, UserEditDTO userEditDTO){

            userToEdit.setName(userEditDTO.getName());
            userToEdit.setLastName(userEditDTO.getLastName());
            userToEdit.setPhoneNumber(userEditDTO.getPhoneNumber());
            return userRepository.save(userToEdit);
    }
}
