package pl.lodz.p.it.thesis.scm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.thesis.scm.model.Workplace;
import pl.lodz.p.it.thesis.scm.repository.WorkplaceRepository;
import pl.lodz.p.it.thesis.scm.service.IWorkplaceService;

import java.util.Optional;

@Service
public class WorkplaceService implements IWorkplaceService {

    private final WorkplaceRepository workplaceRepository;

    @Autowired
    public WorkplaceService(WorkplaceRepository workplaceRepository) {
        this.workplaceRepository = workplaceRepository;
    }

    public Optional<Workplace> getWorkplace(Long id){
        return workplaceRepository.findById(id);
    }
}
