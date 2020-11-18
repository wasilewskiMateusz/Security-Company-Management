package pl.lodz.p.it.thesis.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import pl.lodz.p.it.thesis.scm.dto.job.CreateJobDTO;
import pl.lodz.p.it.thesis.scm.dto.job.JobDTO;
import pl.lodz.p.it.thesis.scm.dto.job.JobEditDTO;
import pl.lodz.p.it.thesis.scm.exception.ResourceNotExistException;
import pl.lodz.p.it.thesis.scm.model.Job;
import pl.lodz.p.it.thesis.scm.service.IJobService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("jobs")
public class JobController {

    private final IJobService jobService;

    @Autowired
    public JobController(IJobService jobService) {
        this.jobService = jobService;
    }


    @PostMapping
    public ResponseEntity<JobDTO> addJob(@Valid @RequestBody CreateJobDTO createJobDTO){
        Job addedJob = jobService.addJob(createJobDTO);
        return ResponseEntity.ok(new JobDTO(addedJob));
    }

    @GetMapping("{id}")
    public ResponseEntity<JobDTO> get(@PathVariable Long id) {
        Optional<Job> job = jobService.getJob(id);
        if (job.isEmpty()) {
            throw new ResourceNotExistException();
        }
        return ResponseEntity.ok(new JobDTO(job.get()));
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAll() {
        List<Job> jobs = jobService.getAllJobs();
        List<JobDTO> jobDTOS = new ArrayList<>();
        jobs.forEach(job -> jobDTOS.add(new JobDTO(job)));
        return ResponseEntity.ok(jobDTOS);
    }

    @PutMapping("{id}")
    public ResponseEntity<JobDTO> editjob(@Valid @RequestBody JobEditDTO jobEditDTO,
                                                      @PathVariable Long id) {
        Job editedJob = jobService.editJob(id, jobEditDTO);

        return ResponseEntity.ok(new JobDTO(editedJob));
    }
}
