package pl.lodz.p.it.thesis.scm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.thesis.scm.model.Contract;
import pl.lodz.p.it.thesis.scm.model.Job;
import pl.lodz.p.it.thesis.scm.model.Status;
import pl.lodz.p.it.thesis.scm.repository.ContractRepository;
import pl.lodz.p.it.thesis.scm.repository.JobRepository;
import pl.lodz.p.it.thesis.scm.repository.StatusRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
public class JobDisableTask {

    private final ContractRepository contractRepository;
    private final JobRepository jobRepository;


    @Autowired
    public JobDisableTask(ContractRepository contractRepository, JobRepository jobRepository) {
        this.contractRepository = contractRepository;
        this.jobRepository = jobRepository;
    }

    @Scheduled(cron = "${change.status.cron.expression}")
    public void changeStatus() {
        LocalDateTime now = LocalDateTime.from(LocalDateTime.now());
        List<Job> jobs = jobRepository.findAll();
        jobs.stream()
                .filter(job -> job.getCompletionDate().isBefore(now) && job.isEnabled())
                .forEach(job -> {
                    job.setEnabled(false);
                    jobRepository.save(job);
                });

    }
}
