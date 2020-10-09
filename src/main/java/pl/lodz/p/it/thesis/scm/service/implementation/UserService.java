package pl.lodz.p.it.thesis.scm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.repository.UserRepository;
import pl.lodz.p.it.thesis.scm.service.IUserService;

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
}
