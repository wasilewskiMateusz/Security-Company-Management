package pl.lodz.p.it.thesis.scm.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.thesis.scm.model.Role;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.model.Workplace;
import pl.lodz.p.it.thesis.scm.repository.RoleRepository;
import pl.lodz.p.it.thesis.scm.repository.UserRepository;
import pl.lodz.p.it.thesis.scm.repository.WorkplaceRepository;

import javax.transaction.Transactional;
import java.util.*;

@Component
@Profile("dev")
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final WorkplaceRepository workplaceRepository;

    @Autowired
    public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, WorkplaceRepository workplaceRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.workplaceRepository = workplaceRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) return;

        Role adminRole = createRoleIfNotFound("ROLE_ADMIN");
        Role employerRole = createRoleIfNotFound("ROLE_EMPLOYER");
        Role employeeRole = createRoleIfNotFound("ROLE_EMPLOYEE");

        createUserIfNotFound("admin@edu.pl", "admin123", Arrays.asList(adminRole, employeeRole), "Mateusz", "Wasilewski", "530060645");
        User employer = createUserIfNotFound("employer@edu.pl", "employer", new ArrayList<>(Collections.singletonList(employerRole)), "Jan", "Kowalski", "123456789");
        User employee = createUserIfNotFound("employee@edu.pl",  "employee", new ArrayList<>(Collections.singletonList(employeeRole)), "Szymon", "Tarwid", "111222333");

        Workplace workplace = createWorkPlaceIfNotFound("Lordis club", "Best sound club", "Piotrkowska 101", true, 5.0, employer);


        alreadySetup = true;


    }

    @Transactional
    Workplace createWorkPlaceIfNotFound(final String name,
                                        final String description,
                                        final String address,
                                        final boolean enable,
                                        final Double avgRate,
                                        final User employer) {

        Workplace workplace = workplaceRepository.findByName(name);
        if (workplace == null) {
            workplace = new Workplace();
            workplace.setName(name);
            workplace.setDescription(description);
            workplace.setAddress(address);
            workplace.setEnable(enable);
            workplace.setAverageRate(avgRate);
            workplace.setEmployer(employer);
            workplaceRepository.save(workplace);
        }
        return workplace;
    }

    @Transactional
    Role createRoleIfNotFound(final String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role = roleRepository.save(role);
        return role;
    }

    @Transactional
    User createUserIfNotFound(final String login, final String password,
                              final Collection<Role> roles, final String name,
                              final String lastName, final String phoneNumber) {
        User user = userRepository.findByEmail(login);
        if (user == null) {
            user = new User();
            user.setEmail(login);
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(password));
            user.setName(name);
            user.setLastName(lastName);
            user.setPhoneNumber(phoneNumber);
        }
        user.setRoles(roles);
        user = userRepository.save(user);
        return user;
    }

}
