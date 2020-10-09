package pl.lodz.p.it.thesis.scm.service;

import pl.lodz.p.it.thesis.scm.model.Workplace;

import java.util.Optional;

public interface IWorkplaceService {

    Optional<Workplace> getWorkplace(Long id);
}
