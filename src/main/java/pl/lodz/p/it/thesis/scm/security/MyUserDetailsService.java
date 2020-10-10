package pl.lodz.p.it.thesis.scm.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.thesis.scm.repository.RoleRepository;
import pl.lodz.p.it.thesis.scm.repository.UserRepository;
import pl.lodz.p.it.thesis.scm.model.Role;
import pl.lodz.p.it.thesis.scm.model.User;

import javax.transaction.Transactional;
import java.util.*;

@Service("userDetailService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public MyUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }
         return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                 user.isEnabled(), true, true,
                 true, getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {
        List<GrantedAuthority> authorities
                = new ArrayList<>();
        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }
}
