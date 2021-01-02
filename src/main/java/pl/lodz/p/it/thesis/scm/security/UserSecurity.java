package pl.lodz.p.it.thesis.scm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.thesis.scm.model.Contract;
import pl.lodz.p.it.thesis.scm.model.Job;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.model.Workplace;
import pl.lodz.p.it.thesis.scm.repository.ContractRepository;
import pl.lodz.p.it.thesis.scm.repository.JobRepository;
import pl.lodz.p.it.thesis.scm.repository.UserRepository;
import pl.lodz.p.it.thesis.scm.repository.WorkplaceRepository;

import java.util.Optional;


@Component("userSecurity")
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
public class UserSecurity {

    private final UserRepository userRepository;
    private final WorkplaceRepository workplaceRepository;
    private final JobRepository jobRepository;

    @Autowired
    public UserSecurity(UserRepository userService,
                        WorkplaceRepository workplaceRepository, JobRepository jobRepository) {
        this.userRepository = userService;
        this.workplaceRepository = workplaceRepository;
        this.jobRepository = jobRepository;
    }

    public boolean hasUserId(Authentication authentication, Long id) {

        String email = authentication.getName();
        if(email.equals("anonymousUser")) return false;
        Object userId = authentication.getCredentials();
        return id.equals(userId);
    }

    public boolean isUserWorkplaceOwner(Authentication authentication, Long id) {
        String email = authentication.getName();
        if(email.equals("anonymousUser")) return false;
        Object userId = authentication.getCredentials();

        Optional<Workplace> workplaceOptional = workplaceRepository.findById(id);
        if(workplaceOptional.isEmpty()) return true;
        Workplace workplace = workplaceOptional.get();

        return workplace.getEmployer().getId().equals(userId);
    }

    public boolean isJobInUserWorkplace(Authentication authentication, Long id) {
        String email = authentication.getName();
        if(email.equals("anonymousUser")) return false;
        Object userId = authentication.getCredentials();

        Optional<Job> jobOptional = jobRepository.findById(id);
        if(jobOptional.isEmpty()) return true;
        Job job = jobOptional.get();

        return job.getWorkplace().getEmployer().getId().equals(userId);
    }
}
