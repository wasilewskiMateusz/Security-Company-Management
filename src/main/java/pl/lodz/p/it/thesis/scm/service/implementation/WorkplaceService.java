package pl.lodz.p.it.thesis.scm.service.implementation;

import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.thesis.scm.model.Workplace;
import pl.lodz.p.it.thesis.scm.repository.WorkplaceRepository;
import pl.lodz.p.it.thesis.scm.service.IWorkplaceService;

import java.util.List;
import java.util.Optional;

@Service
public class WorkplaceService implements IWorkplaceService {

    private final WorkplaceRepository workplaceRepository;

    @Autowired
    public WorkplaceService(WorkplaceRepository workplaceRepository) {
        this.workplaceRepository = workplaceRepository;
    }

    @Override
    public Optional<Workplace> getWorkplace(Long id){
        return workplaceRepository.findById(id);
    }

    @Override
    public List<Workplace> getAllWorkplaces() {
       return workplaceRepository.findAll();
    }

    @Override
    public Workplace editWorkplace(Workplace workplace) {
        if(workplaceRepository.findById(workplace.getId()).isPresent()){
            return workplaceRepository.save(workplace);
        }
        else return null;
    }

    @Override
    public Workplace addWorkplace(Workplace workplace) {
        return workplaceRepository.save(workplace);
    }


}
