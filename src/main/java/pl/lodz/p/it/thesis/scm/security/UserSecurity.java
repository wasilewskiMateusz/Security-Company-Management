package pl.lodz.p.it.thesis.scm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.repository.UserRepository;


@Component
public class UserSecurity {

    private final UserRepository userRepository;

    @Autowired
    public UserSecurity(UserRepository userService) {
        this.userRepository = userService;
    }

    public boolean hasUserId(Authentication authentication, Long id) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        return user.getId().equals(id);

    }
}