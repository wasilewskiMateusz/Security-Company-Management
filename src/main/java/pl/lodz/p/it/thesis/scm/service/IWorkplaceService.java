package pl.lodz.p.it.thesis.scm.service;

import pl.lodz.p.it.thesis.scm.dto.workplace.CreateWorkplaceDTO;
import pl.lodz.p.it.thesis.scm.dto.workplace.DeleteWorkplaceDTO;
import pl.lodz.p.it.thesis.scm.dto.workplace.WorkplaceDisabilityDTO;
import pl.lodz.p.it.thesis.scm.dto.workplace.WorkplaceEditDTO;
import pl.lodz.p.it.thesis.scm.model.Job;
import pl.lodz.p.it.thesis.scm.model.Workplace;

import java.util.List;
import java.util.Optional;

public interface IWorkplaceService {

    Optional<Workplace> getWorkplace(Long id);

    List<Workplace> getAllWorkplaces();

    Workplace editWorkplace(Long id, WorkplaceEditDTO workplaceEditDTO);

    Workplace addWorkplace(CreateWorkplaceDTO createWorkplaceDTO, Long id);

    Workplace disableWorkplace(Long id, WorkplaceDisabilityDTO workplaceDisabilityDTO);

    List<Job> getAllJobsInWorkplace(Long id);
}

