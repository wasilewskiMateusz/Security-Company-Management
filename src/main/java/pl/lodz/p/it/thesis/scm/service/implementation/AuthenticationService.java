package pl.lodz.p.it.thesis.scm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.thesis.scm.dto.user.UserRegisterDTO;
import pl.lodz.p.it.thesis.scm.exception.RestException;
import pl.lodz.p.it.thesis.scm.model.RefreshToken;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.repository.RefreshTokenRepository;
import pl.lodz.p.it.thesis.scm.repository.RoleRepository;
import pl.lodz.p.it.thesis.scm.repository.UserRepository;
import pl.lodz.p.it.thesis.scm.service.IAuthenticationService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
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
    public void registerNewUserAccount(UserRegisterDTO userRegisterDTO) {
        if (emailExists(userRegisterDTO.getEmail())) {
            throw new RestException("Exception.user.with.this.email.exist");
        }
        final User user = new User();
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        user.setPhoneNumber(userRegisterDTO.getPhoneNumber());
        user.setEnabled(false);

        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_EMPLOYEE")));
        userRepository.save(user);

    }

    @Override
    public void registerRefreshToken(String refreshToken, String email, LocalDateTime expirationTime){
        User user = userRepository.findByEmail(email);
        RefreshToken token = new RefreshToken(passwordEncoder.encode(refreshToken), user, expirationTime);
        refreshTokenRepository.save(token);
    }

    @Override
    public boolean checkIfTokenExists(String refreshToken, String email){
        List<RefreshToken> userTokenList = refreshTokenRepository.findByUserEmail(email);
        return userTokenList.stream().anyMatch(token -> passwordEncoder.matches(refreshToken, token.getToken()));
    }

    @Override
    public void logout(String refreshToken, String email){
        List<RefreshToken> userTokenList = refreshTokenRepository.findByUserEmail(email);
        for(RefreshToken rToken: userTokenList){
            if(passwordEncoder.matches(refreshToken, rToken.getToken())){
                refreshTokenRepository.deleteById(rToken.getId());
                break;
            }
        }
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
