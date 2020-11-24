package pl.lodz.p.it.thesis.scm.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.thesis.scm.model.*;
import pl.lodz.p.it.thesis.scm.repository.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Profile("dev")
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final JobRepository jobRepository;

    private final PasswordEncoder passwordEncoder;

    private final WorkplaceRepository workplaceRepository;

    private final StatusRepository statusRepository;

    @Autowired
    public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository,
                           JobRepository jobRepository, PasswordEncoder passwordEncoder, WorkplaceRepository workplaceRepository, StatusRepository statusRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jobRepository = jobRepository;
        this.passwordEncoder = passwordEncoder;
        this.workplaceRepository = workplaceRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) return;

        Role adminRole = createRoleIfNotFound("ROLE_ADMIN");
        Role employerRole = createRoleIfNotFound("ROLE_EMPLOYER");
        Role employeeRole = createRoleIfNotFound("ROLE_EMPLOYEE");

        User admin = createUserIfNotFound("admin@edu.pl", "admin123", Arrays.asList(adminRole, employerRole, employeeRole), "Mateusz", "Wasilewski", "530060645");
        User employer = createUserIfNotFound("employer@edu.pl", "employer", new ArrayList<>(Collections.singletonList(employerRole)), "Jan", "Kowalski", "123456789");
        User employee = createUserIfNotFound("employee@edu.pl", "employee", new ArrayList<>(Collections.singletonList(employeeRole)), "Szymon", "Tarwid", "111222333");

        Workplace workplace = createWorkPlaceIfNotFound("Lordis club", "Best sound club", "Piotrkowska 101", "Łódź", true, 5.0, employer);
        Workplace workplace1 = createWorkPlaceIfNotFound("El Cubano", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis,.", "Moniuszki 1", "Łódź", true, 3.0, employer);
        Workplace workplace2 = createWorkPlaceIfNotFound("Prywatka", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque", "Tuwima 40", "Łódź", true, 1.0, employer);
        Workplace workplace3 = createWorkPlaceIfNotFound("Woodstock", "Najbardziej brudna impreza w Polsce.", "Mickiewicza 113", "Kostrzyn nad odrą", true, 3.35, admin);

        Job job1 = createJobIfNotFound("Sprawdzenie posiadania opasek", 80D, LocalDateTime.of(2020, 12, 25, 4, 0),LocalDateTime.of(2020, 12, 24, 21, 59), 3, workplace);
        Job job2 = createJobIfNotFound("Selekcja", 120D, LocalDateTime.of(2020, 12, 25, 4, 0),LocalDateTime.of(2020, 12, 24, 21, 59), 1, workplace1);
        Job job3 = createJobIfNotFound("Pilnowanie porządku pod prysznicami", 200D, LocalDateTime.of(2020, 12, 25, 4, 0),LocalDateTime.of(2020, 12, 24, 21, 59), 10, workplace3);
        Job job4 = createJobIfNotFound("Pilnowanie vipów", 40D, LocalDateTime.of(2020, 12, 25, 4, 0),LocalDateTime.of(2020, 12, 24, 21, 59), 1, workplace2);

        Status claimed = createStatusIfNotFound("Claimed");
        Status started = createStatusIfNotFound("Started");
        Status finished = createStatusIfNotFound("Finished");

        alreadySetup = true;


    }

    @Transactional
    Workplace createWorkPlaceIfNotFound(final String name,
                                        final String description,
                                        final String street,
                                        final String city,
                                        final boolean enabled,
                                        final Double avgRate,
                                        final User employer) {

        Workplace workplace = workplaceRepository.findByName(name);
        if (workplace == null) {
            workplace = new Workplace();
            workplace.setName(name);
            workplace.setDescription(description);
            workplace.setStreet(street);
            workplace.setCity(city);
            workplace.setEnabled(enabled);
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

    @Transactional
    Job createJobIfNotFound(final String description, final Double wage,
                            final LocalDateTime completionDate, final LocalDateTime startDate,
                            final int vacancy, final Workplace workplace) {
        Job job = new Job();
        job.setDescription(description);
        job.setWage(wage);
        job.setCompletionDate(completionDate);
        job.setStartDate(startDate);
        job.setVacancy(vacancy);
        job.setWorkplace(workplace);
        job.setEnabled(true);

        job = jobRepository.save(job);
        return job;
    }

    @Transactional
    Status createStatusIfNotFound(final String name) {
        Status status = new Status();
        status.setName(name);

        status = statusRepository.save(status);
        return status;
    }

}
