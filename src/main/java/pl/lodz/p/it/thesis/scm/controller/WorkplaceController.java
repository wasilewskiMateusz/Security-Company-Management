package pl.lodz.p.it.thesis.scm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import pl.lodz.p.it.thesis.scm.dto.job.JobDTO;
import pl.lodz.p.it.thesis.scm.dto.workplace.*;
import pl.lodz.p.it.thesis.scm.exception.ResourceNotExistException;
import pl.lodz.p.it.thesis.scm.model.Job;
import pl.lodz.p.it.thesis.scm.model.Workplace;
import pl.lodz.p.it.thesis.scm.service.IWorkplaceService;
import pl.lodz.p.it.thesis.scm.util.JwtUtil;
import pl.lodz.p.it.thesis.scm.util.RestMessage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("workplaces")
public class WorkplaceController {

    private final IWorkplaceService workplaceService;
    private final JwtUtil jwtUtil;


    @Autowired
    public WorkplaceController(IWorkplaceService workplaceService, JwtUtil jwtUtil) {
        this.workplaceService = workplaceService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("{id}")
    public ResponseEntity<WorkplaceDTO> get(@PathVariable Long id) {
        Optional<Workplace> workplace = workplaceService.getWorkplace(id);
        if (workplace.isEmpty()) {
            throw new ResourceNotExistException();
        }
        return ResponseEntity.ok(new WorkplaceDTO(workplace.get()));
    }

    @GetMapping
    public ResponseEntity<List<WorkplaceDTO>> getAll() {
        List<Workplace> workplaces = workplaceService.getAllWorkplaces();
        List<WorkplaceDTO> workplaceDTOS = new ArrayList<>();
        workplaces.forEach(workplace -> workplaceDTOS.add(new WorkplaceDTO(workplace)));
        return ResponseEntity.ok(workplaceDTOS);
    }

    @GetMapping("{id}/jobs")
    public ResponseEntity<List<JobDTO>> getAll(@PathVariable Long id) {
        List<Job> jobs = workplaceService.getAllJobsInWorkplace(id);
        List<JobDTO> jobsDTOS = new ArrayList<>();
        jobs.forEach(job -> jobsDTOS.add(new JobDTO(job)));
        return ResponseEntity.ok(jobsDTOS);
    }

    @PutMapping("{id}")
    public ResponseEntity<WorkplaceDTO> editWorkplace(@Valid @RequestBody WorkplaceEditDTO workplaceEditDTO,
                                                      @PathVariable Long id) {
        Workplace editedWorkplace = workplaceService.editWorkplace(id, workplaceEditDTO);

        return ResponseEntity.ok(new WorkplaceDTO(editedWorkplace));
    }


    @PostMapping
    public ResponseEntity<WorkplaceDTO> addWorkplace(@Valid @RequestBody CreateWorkplaceDTO createWorkplaceDTO,
                                                     WebRequest request){
        String tokenHeader = request.getHeader("Authorization");
        String token = tokenHeader.substring(7);
        Long id = jwtUtil.getIdFromToken(token);
        Workplace addedWorkplace = workplaceService.addWorkplace(createWorkplaceDTO, id);
        return ResponseEntity.ok(new WorkplaceDTO(addedWorkplace));
    }

    @PutMapping("{id}/disability")
    public ResponseEntity<WorkplaceDTO> disableWorkplace(@Valid @RequestBody WorkplaceDisabilityDTO workplaceDisabilityDTO,
                                                        @PathVariable Long id) {
        Workplace editedWorkplace = workplaceService.disableWorkplace(id, workplaceDisabilityDTO);

        return ResponseEntity.ok(new WorkplaceDTO(editedWorkplace));
    }

}
