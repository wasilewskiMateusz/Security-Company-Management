package pl.lodz.p.it.thesis.scm.service;

import pl.lodz.p.it.thesis.scm.dto.contract.CreateContractDTO;
import pl.lodz.p.it.thesis.scm.dto.workplace.CreateWorkplaceDTO;
import pl.lodz.p.it.thesis.scm.dto.workplace.WorkplaceAvailabilityDTO;
import pl.lodz.p.it.thesis.scm.dto.workplace.WorkplaceEditDTO;
import pl.lodz.p.it.thesis.scm.model.Contract;
import pl.lodz.p.it.thesis.scm.model.Job;
import pl.lodz.p.it.thesis.scm.model.Workplace;

import java.util.List;
import java.util.Optional;

public interface IContractService {

    Optional<Contract> getContract(Long id);

    Contract createContract(CreateContractDTO createContractDTO, Long userId);
}

