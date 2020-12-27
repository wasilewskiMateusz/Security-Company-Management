package pl.lodz.p.it.thesis.scm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
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
public class UserSecurity {

    private final UserRepository userRepository;
    private final WorkplaceRepository workplaceRepository;
    private final JobRepository jobRepository;
    private final ContractRepository contractRepository;

    @Autowired
    public UserSecurity(UserRepository userService,
                        WorkplaceRepository workplaceRepository, JobRepository jobRepository, ContractRepository contractRepository) {
        this.userRepository = userService;
        this.workplaceRepository = workplaceRepository;
        this.jobRepository = jobRepository;
        this.contractRepository = contractRepository;
    }

    public boolean hasUserId(Authentication authentication, Long id) {

        String email = authentication.getName();
        if(email.equals("anonymousUser")) return false;
        User user = userRepository.findByEmail(email);
        return user.getId().equals(id);
    }

    public boolean isUserWorkplaceOwner(Authentication authentication, Long id) {
        String email = authentication.getName();
        if(email.equals("anonymousUser")) return false;
        User user = userRepository.findByEmail(email);

        Optional<Workplace> workplaceOptional = workplaceRepository.findById(id);
        if(workplaceOptional.isEmpty()) return true;
        Workplace workplace = workplaceOptional.get();

        return workplace.getEmployer().getId().equals(user.getId());
    }

    public boolean isJobInUserWorkplace(Authentication authentication, Long id) {
        String email = authentication.getName();
        if(email.equals("anonymousUser")) return false;
        User user = userRepository.findByEmail(email);

        Optional<Job> jobOptional = jobRepository.findById(id);
        if(jobOptional.isEmpty()) return true;
        Job job = jobOptional.get();

        return job.getWorkplace().getEmployer().getId().equals(user.getId());
    }
}
