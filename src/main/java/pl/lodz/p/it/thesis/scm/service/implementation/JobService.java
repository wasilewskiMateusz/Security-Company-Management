package pl.lodz.p.it.thesis.scm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.thesis.scm.dto.job.CreateJobDTO;
import pl.lodz.p.it.thesis.scm.exception.RestException;
import pl.lodz.p.it.thesis.scm.model.Job;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.model.Workplace;
import pl.lodz.p.it.thesis.scm.repository.JobRepository;
import pl.lodz.p.it.thesis.scm.repository.WorkplaceRepository;
import pl.lodz.p.it.thesis.scm.service.IJobService;

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

        if(workplaceOptional.isEmpty()){
            throw new RestException("Exception.job.workplace.id.not.found");
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
}
