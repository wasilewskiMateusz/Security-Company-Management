package pl.lodz.p.it.thesis.scm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.thesis.scm.dto.requests.UserRequest;
import pl.lodz.p.it.thesis.scm.model.RefreshToken;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.repository.RefreshTokenRepository;
import pl.lodz.p.it.thesis.scm.repository.RoleRepository;
import pl.lodz.p.it.thesis.scm.repository.UserRepository;
import pl.lodz.p.it.thesis.scm.service.IAuthenticationService;

import java.util.Collections;
import java.util.List;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder,
                                 RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void registerNewUserAccount(UserRequest userRequest) {
        if (emailExists(userRequest.getEmail())) {
        }
        final User user = new User();

        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEnabled(true);

        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        userRepository.save(user);

    }

    @Override
    public void registerRefreshToken(String refreshToken, String email){
        User user = userRepository.findByEmail(email);
        RefreshToken token = new RefreshToken(passwordEncoder.encode(refreshToken), user);
        refreshTokenRepository.save(token);
    }

    @Override
    public boolean checkIfTokenExists(String refreshToken, String email){
        List<RefreshToken> tokenList = refreshTokenRepository.findByUserEmail(email);
        return tokenList.stream().anyMatch(token -> passwordEncoder.encode(token.getToken()).equals(refreshToken));
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
