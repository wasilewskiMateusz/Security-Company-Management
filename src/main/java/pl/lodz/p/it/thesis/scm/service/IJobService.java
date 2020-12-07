package pl.lodz.p.it.thesis.scm.service;

import pl.lodz.p.it.thesis.scm.dto.job.CreateJobDTO;
import pl.lodz.p.it.thesis.scm.dto.job.JobDisabilityDTO;
import pl.lodz.p.it.thesis.scm.dto.job.JobEditDTO;
import pl.lodz.p.it.thesis.scm.model.Contract;
import pl.lodz.p.it.thesis.scm.model.Job;

import java.util.List;
import java.util.Optional;

public interface IJobService {

    Job addJob(CreateJobDTO createJobDTO);

    Optional<Job> getJob(Long id);

    List<Job> getAllJobs();

    Job editJob(Long id, JobEditDTO jobEditDTO);

    List<Contract> getContracts(Long id);

    Job disableJob(Long id, JobDisabilityDTO jobDisabilityDTO);
}
