package pl.lodz.p.it.thesis.scm.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.thesis.scm.service.implementation.UserService;
import pl.lodz.p.it.thesis.scm.util.JwtUtil;

@Component
public class UserSecurity {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @SuppressWarnings("unused")
    public UserSecurity() {
        this(null);
    }
    public UserSecurity(JwtUtil jwtUtil,) {
        this.jwtUtil = jwtUtil;
    }

    public boolean hasUserId(Authentication authentication, Long id) {
        String token = (String) authentication.getCredentials();
        String username = jwtUtil.getUsernameFromToken(token);
        return username.equals(id);

    }
}