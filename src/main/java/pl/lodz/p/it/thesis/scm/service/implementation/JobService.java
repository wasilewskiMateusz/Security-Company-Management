package pl.lodz.p.it.thesis.scm.service.implementation;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.thesis.scm.dto.job.CreateJobDTO;
import pl.lodz.p.it.thesis.scm.dto.job.JobEditDTO;
import pl.lodz.p.it.thesis.scm.exception.ResourceNotExistException;
import pl.lodz.p.it.thesis.scm.exception.RestException;
import pl.lodz.p.it.thesis.scm.model.Contract;
import pl.lodz.p.it.thesis.scm.model.Job;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.model.Workplace;
import pl.lodz.p.it.thesis.scm.repository.JobRepository;
import pl.lodz.p.it.thesis.scm.repository.WorkplaceRepository;
import pl.lodz.p.it.thesis.scm.service.IJobService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobService implements IJobService {

    private final JobRepository jobRepository;
    private final WorkplaceRepository workplaceRepository;

    @Autowired
    public JobService(JobRepository jobRepository, WorkplaceRepository workplaceRepository) {
        this.jobRepository = jobRepository;
        this.workplaceRepository = workplaceRepository;
    }

    @Override
    public Job addJob(CreateJobDTO createJobDTO) {
        Optional<Workplace> workplaceOptional = workplaceRepository.findById(createJobDTO.getWorkplaceId());

        if (workplaceOptional.isEmpty()) {
            throw new RestException("Exception.job.job.id.not.found");
        }

        Workplace workplace = workplaceOptional.get();

        Job job = new Job();
        job.setDescription(createJobDTO.getDescription());
        job.setStartDate(createJobDTO.getStartDate());
        job.setCompletionDate(createJobDTO.getCompletionDate());
        job.setWage(createJobDTO.getWage());
        job.setVacancy(createJobDTO.getVacancy());
        job.setWorkplace(workplace);

        return jobRepository.save(job);
    }

    @Override
    public Optional<Job> getJob(Long id) {
        return jobRepository.findById(id);
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public Job editJob(Long id, JobEditDTO jobEditDTO) {
        Job job = checkVersion(id, jobEditDTO.getVersion());

        job.setDescription(jobEditDTO.getDescription());
        job.setVacancy(jobEditDTO.getVacancy());
        job.setStartDate(jobEditDTO.getStartDate());
        job.setCompletionDate(jobEditDTO.getCompletionDate());
        job.setWage(jobEditDTO.getWage());

        return jobRepository.save(job);
    }

    @Override
    public List<Contract> getContracts(Long id) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isEmpty()) {
            throw new ResourceNotExistException();
        }

        return new ArrayList<>(jobOptional.get().getContracts());
    }

    private Job checkVersion(Long id, String version) {
        Optional<Job> jobOptional = jobRepository.findById(id);

        if (jobOptional.isEmpty()) {
            throw new ResourceNotExistException();
        }

        Job job = jobOptional.get();

        String currentVersion = DigestUtils.sha256Hex(job.getVersion().toString());

        if (!version.equals(currentVersion)) {
            throw new RestException("Exception.different.version");
        }

        return job;
    }
}
