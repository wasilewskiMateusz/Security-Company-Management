package pl.lodz.p.it.thesis.scm.service.implementation;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.thesis.scm.dto.workplace.CreateWorkplaceDTO;
import pl.lodz.p.it.thesis.scm.dto.workplace.WorkplaceAvailabilityDTO;
import pl.lodz.p.it.thesis.scm.dto.workplace.WorkplaceEditDTO;
import pl.lodz.p.it.thesis.scm.exception.ResourceNotExistException;
import pl.lodz.p.it.thesis.scm.exception.RestException;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.model.Workplace;
import pl.lodz.p.it.thesis.scm.repository.UserRepository;
import pl.lodz.p.it.thesis.scm.repository.WorkplaceRepository;
import pl.lodz.p.it.thesis.scm.service.IWorkplaceService;

import java.util.List;
import java.util.Optional;

@Service
public class WorkplaceService implements IWorkplaceService {

    private final WorkplaceRepository workplaceRepository;
    private final UserRepository userRepository;

    @Autowired
    public WorkplaceService(WorkplaceRepository workplaceRepository, UserRepository userRepository) {
        this.workplaceRepository = workplaceRepository;
        this.userRepository = userRepository;
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
    public Workplace editWorkplace(Long id, WorkplaceEditDTO workplaceEditDTO) {

        Workplace workplace = checkVersion(id, workplaceEditDTO.getVersion());

        workplace.setName(workplaceEditDTO.getName());
        workplace.setDescription(workplaceEditDTO.getDescription());
        workplace.setStreet(workplaceEditDTO.getStreet());
        workplace.setCity(workplaceEditDTO.getCity());
        return workplaceRepository.save(workplace);
    }

    @Override
    public Workplace addWorkplace(CreateWorkplaceDTO createWorkplaceDTO, Long id) {

        Optional<User> ownerOptional = userRepository.findById(id);

        if(ownerOptional.isEmpty()){
            throw new RestException("Exception.workplace.owner.id.not.found");
        }

        User owner = ownerOptional.get();

        Workplace workplace = new Workplace();
        workplace.setName(createWorkplaceDTO.getName());
        workplace.setDescription(createWorkplaceDTO.getDescription());
        workplace.setStreet(createWorkplaceDTO.getStreet());
        workplace.setCity(createWorkplaceDTO.getCity());
        workplace.setAverageRate(0D);
        workplace.setEnabled(true);
        workplace.setEmployer(owner);
        return workplaceRepository.save(workplace);
    }

    @Override
    public Workplace changeAvailability(Long id, WorkplaceAvailabilityDTO workplaceAvailabilityDTO) {
        Workplace workplace = checkVersion(id, workplaceAvailabilityDTO.getVersion());
        workplace.setEnabled(workplaceAvailabilityDTO.isEnabled());
        return workplaceRepository.save(workplace);
    }

    private Workplace checkVersion(Long id, String version) {
        Optional<Workplace> workplaceOptional = workplaceRepository.findById(id);

        if (workplaceOptional.isEmpty()) {
            throw new ResourceNotExistException();
        }

        Workplace workplace = workplaceOptional.get();

        String currentVersion = DigestUtils.sha256Hex(workplace.getVersion().toString());

        if (!version.equals(currentVersion)) {
            throw new RestException("Exception.different.version");
        }

        return workplace;
    }

}
