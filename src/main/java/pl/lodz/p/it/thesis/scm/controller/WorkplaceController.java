package pl.lodz.p.it.thesis.scm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.thesis.scm.dto.workplace.CreateWorkplaceDTO;
import pl.lodz.p.it.thesis.scm.dto.workplace.WorkplaceDTO;
import pl.lodz.p.it.thesis.scm.exception.RestException;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.model.Workplace;
import pl.lodz.p.it.thesis.scm.service.IUserService;
import pl.lodz.p.it.thesis.scm.service.IWorkplaceService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workplaces")
public class WorkplaceController {

    private final IWorkplaceService workplaceService;
    private final IUserService userService;

    @Autowired
    public WorkplaceController(IWorkplaceService workplaceService, IUserService userService) {
        this.workplaceService = workplaceService;
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<WorkplaceDTO> get(@PathVariable Long id) {
        Optional<Workplace> workplace = workplaceService.getWorkplace(id);
        if (workplace.isEmpty()) {
            throw new RestException("Exception.workplace.not.found");
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

    @PutMapping
    public ResponseEntity<WorkplaceDTO> editWorkplace(@Valid @RequestBody WorkplaceDTO workplaceDTO) {
        Workplace workplace = WorkplaceDTO.toWorkplace(workplaceDTO);
        Workplace editedWorkplace = workplaceService.editWorkplace(workplace);
        if(editedWorkplace == null) {
            throw new RestException("Exception.not.found.workplace.to.edit");
        }
        return ResponseEntity.ok(new WorkplaceDTO(editedWorkplace));
    }

    @PostMapping
    public ResponseEntity<WorkplaceDTO> addWorkplace(@Valid @RequestBody CreateWorkplaceDTO createWorkplaceDTO){
        Workplace workplace = CreateWorkplaceDTO.toWorkplace(createWorkplaceDTO);
        workplace.setAverageRate(0.);
        workplace.setEnable(false);
        Optional<User> user = userService.getUser(createWorkplaceDTO.getOwnerId());
        //TODO sprawdzanie roli
        if(user.isEmpty()){
            throw new RestException("Exception.workplace.owner.id.not.found");
        }
        workplace.setOwner(user.get());
        Workplace addedWorkplace = workplaceService.addWorkplace(workplace);
        return ResponseEntity.ok(new WorkplaceDTO(addedWorkplace));
    }
}
