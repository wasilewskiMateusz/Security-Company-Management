package pl.lodz.p.it.thesis.scm.service;

import pl.lodz.p.it.thesis.scm.dto.contract.ContractCheckInDTO;
import pl.lodz.p.it.thesis.scm.dto.contract.CreateContractDTO;
import pl.lodz.p.it.thesis.scm.model.Contract;

import java.util.Optional;

public interface IContractService {

    Optional<Contract> getContract(Long id);

    Contract createContract(CreateContractDTO createContractDTO, Long userId);

    void deleteContract(Long id, Long userId);

    Contract checkIn(ContractCheckInDTO contractCheckInDTO, Long userId, Long id);
}

