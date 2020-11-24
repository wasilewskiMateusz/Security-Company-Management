package pl.lodz.p.it.thesis.scm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.thesis.scm.dto.contract.CreateContractDTO;
import pl.lodz.p.it.thesis.scm.exception.ResourceNotExistException;
import pl.lodz.p.it.thesis.scm.exception.RestException;
import pl.lodz.p.it.thesis.scm.model.Contract;
import pl.lodz.p.it.thesis.scm.model.Job;
import pl.lodz.p.it.thesis.scm.model.Status;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.repository.ContractRepository;
import pl.lodz.p.it.thesis.scm.repository.JobRepository;
import pl.lodz.p.it.thesis.scm.repository.StatusRepository;
import pl.lodz.p.it.thesis.scm.repository.UserRepository;
import pl.lodz.p.it.thesis.scm.service.IContractService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ContractService implements IContractService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final StatusRepository statusRepository;
    private final ContractRepository contractRepository;

    @Autowired
    public ContractService(UserRepository userRepository, JobRepository jobRepository, StatusRepository statusRepository, ContractRepository contractRepository) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.statusRepository = statusRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public Optional<Contract> getContract(Long id) {
        return Optional.empty();
    }

    @Override
    public Contract createContract(CreateContractDTO createContractDTO, Long userId) {
        Optional<User> employeeOptional = userRepository.findById(userId);
        Optional<Job> jobOptional = jobRepository.findById(createContractDTO.getJobId());

        if (employeeOptional.isEmpty()) {
            throw new RestException("Exception.contract.employee.id.not.found");
        }

        if (jobOptional.isEmpty()) {
            throw new RestException("Exception.job.id.not.found");
        }

        User employee = employeeOptional.get();

        Job job = jobOptional.get();

        if (job.getVacancy() == 0) {
            throw new RestException("Exception.job.vacancy.full");
        }

        if (!job.isEnabled()) {
            throw new RestException("Exception.job.not.enable");
        }

        if (employee.getContracts().stream().anyMatch(contract -> contract.getJob().getId().equals(job.getId()))) {
            throw new RestException("Exception.job.already.claimed");
        }


        Status claimed = statusRepository.findByName("Claimed");

        Contract contract = new Contract(employee, job);

        contract.setStatus(claimed);

        job.setVacancy(job.getVacancy() - 1);


        return contractRepository.save(contract);
    }

    @Override
    public void deleteContract(Long id) {
        Optional<Contract> contractOptional = contractRepository.findById(id);

        if (contractOptional.isEmpty()) {
            throw new ResourceNotExistException();
        }
        Contract contract = contractOptional.get();
        Job job = contract.getJob();
        if(!LocalDateTime.now().isBefore(job.getStartDate().minusDays(1))){
            throw new RestException("Exception.too.late.to.remove.contract");
        }
        job.setVacancy(contract.getJob().getVacancy()+1);
        jobRepository.save(job);
        contractRepository.delete(contract);
    }

}
