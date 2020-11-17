package pl.lodz.p.it.thesis.scm.service;

import pl.lodz.p.it.thesis.scm.dto.job.CreateJobDTO;
import pl.lodz.p.it.thesis.scm.model.Job;

public interface IJobService {

    Job addJob(CreateJobDTO createJobDTO);
}
