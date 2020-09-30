package pl.lodz.p.it.thesis.scm.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.thesis.scm.model.Privilege;
import pl.lodz.p.it.thesis.scm.model.Role;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.repository.PrivilegeRepository;
import pl.lodz.p.it.thesis.scm.repository.RoleRepository;
import pl.lodz.p.it.thesis.scm.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.*;

@Component
@Profile("dev")
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PrivilegeRepository privilegeRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository,
                           PrivilegeRepository privilegeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) return;

        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        final List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        final List<Privilege> userPrivileges = new ArrayList<>(Collections.singletonList(readPrivilege));

        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        Role userRole = createRoleIfNotFound("ROLE_USER", userPrivileges);

        createUserIfNotFound("admin@edu.pl", "admin", new ArrayList<>(Collections.singletonList(adminRole)));
        User user = createUserIfNotFound("user@edu.pl",  "user", new ArrayList<>(Collections.singletonList(userRole)));


        alreadySetup = true;


    }

    @Transactional
    Privilege createPrivilegeIfNotFound(final String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.setPrivileges(privileges);
        role = roleRepository.save(role);
        return role;
    }

    @Transactional
    User createUserIfNotFound(final String login, final String password, final Collection<Role> roles) {
        User user = userRepository.findByEmail(login);
        if (user == null) {
            user = new User();
            user.setEmail(login);
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(password));
        }
        user.setRoles(roles);
        user = userRepository.save(user);
        return user;
    }

}