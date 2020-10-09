package pl.lodz.p.it.thesis.scm.service;

import pl.lodz.p.it.thesis.scm.model.Workplace;

import java.util.List;
import java.util.Optional;

public interface IWorkplaceService {

    Optional<Workplace> getWorkplace(Long id);

    List<Workplace> getAllWorkplaces();

    Workplace editWorkplace(Workplace workplace);

    Workplace addWorkplace(Workplace workplace);
}

