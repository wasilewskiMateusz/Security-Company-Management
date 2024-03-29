package pl.lodz.p.it.thesis.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import pl.lodz.p.it.thesis.scm.dto.contract.ContractDTO;
import pl.lodz.p.it.thesis.scm.dto.job.CreateJobDTO;
import pl.lodz.p.it.thesis.scm.dto.job.JobDTO;
import pl.lodz.p.it.thesis.scm.dto.job.JobDisabilityDTO;
import pl.lodz.p.it.thesis.scm.dto.job.JobEditDTO;
import pl.lodz.p.it.thesis.scm.exception.ResourceNotExistException;
import pl.lodz.p.it.thesis.scm.model.Contract;
import pl.lodz.p.it.thesis.scm.model.Job;
import pl.lodz.p.it.thesis.scm.service.IJobService;
import pl.lodz.p.it.thesis.scm.util.JwtUtil;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("jobs")
@Transactional(propagation = Propagation.NEVER)
public class JobController {

    private final IJobService jobService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JobController(IJobService jobService, JwtUtil jwtUtil) {
        this.jobService = jobService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping
    public ResponseEntity<JobDTO> addJob(@Valid @RequestBody CreateJobDTO createJobDTO, WebRequest request){
        String tokenHeader = request.getHeader("Authorization");
        String token = tokenHeader.substring(7);
        Long id = jwtUtil.getIdFromToken(token);
        Job addedJob = jobService.addJob(createJobDTO, id);
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
    public ResponseEntity<JobDTO> editJob(@Valid @RequestBody JobEditDTO jobEditDTO,
                                                      @PathVariable Long id) {
        Job editedJob = jobService.editJob(id, jobEditDTO);

        return ResponseEntity.ok(new JobDTO(editedJob));
    }


    @GetMapping("{id}/contracts")
    public ResponseEntity<List<ContractDTO>> getContracts(@PathVariable Long id) {
        List<Contract> contracts = jobService.getContracts(id);
        List<ContractDTO> contractDTOS = new ArrayList<>();
        contracts.forEach(contract -> contractDTOS.add(new ContractDTO(contract)));
        return ResponseEntity.ok(contractDTOS);
    }

    @PutMapping("{id}/disability")
    public ResponseEntity<JobDTO> disableJob(@Valid @RequestBody JobDisabilityDTO jobDisabilityDTO,
                                                         @PathVariable Long id) {
        Job editedJob = jobService.disableJob(id, jobDisabilityDTO);

        return ResponseEntity.ok(new JobDTO(editedJob));
    }
}
