package pl.lodz.p.it.thesis.scm.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if("mati".equals(username)) {
            return new User("mati", "$2a$10$tHmXSNhtBUSRa6BmsOtTau0vYWDw9AN9v3W56Cc.rQbTv7WrX7kra", new ArrayList<>());
        }
        else {
            throw new UsernameNotFoundException("User not found with username" + username);
        }
    }
}
