package pl.lodz.p.it.thesis.scm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.thesis.scm.model.Workplace;
import pl.lodz.p.it.thesis.scm.service.IWorkplaceService;

import java.util.Optional;

@RestController
@RequestMapping("/workplaces")
public class WorkplaceController {

    private final IWorkplaceService workplaceService;

    @Autowired
    public WorkplaceController(IWorkplaceService workplaceService) {
        this.workplaceService = workplaceService;
    }

    @GetMapping("{id}")
    public ResponseEntity<> get(@PathVariable Long id){
        Optional<Workplace> workplace = workplaceService.getWorkplace(id);
    }
}
