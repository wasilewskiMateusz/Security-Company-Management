package pl.lodz.p.it.thesis.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import pl.lodz.p.it.thesis.scm.dto.job.CreateJobDTO;
import pl.lodz.p.it.thesis.scm.dto.job.JobDTO;
import pl.lodz.p.it.thesis.scm.dto.workplace.WorkplaceDTO;
import pl.lodz.p.it.thesis.scm.model.Job;
import pl.lodz.p.it.thesis.scm.model.Workplace;
import pl.lodz.p.it.thesis.scm.service.IJobService;

import javax.validation.Valid;

@RestController
@RequestMapping("jobs")
public class JobController {

    private final IJobService jobService;

    @Autowired
    public JobController(IJobService jobService) {
        this.jobService = jobService;
    }


    @PostMapping
    public ResponseEntity<JobDTO> addJob(@Valid @RequestBody CreateJobDTO createJobDTO,
                                                     WebRequest request){
        Job addedJob = jobService.addJob(createJobDTO);
        return ResponseEntity.ok(new JobDTO(addedJob));
    }
}
