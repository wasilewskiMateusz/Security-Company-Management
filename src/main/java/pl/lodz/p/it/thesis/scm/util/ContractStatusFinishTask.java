package pl.lodz.p.it.thesis.scm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.thesis.scm.model.Contract;
import pl.lodz.p.it.thesis.scm.model.Status;
import pl.lodz.p.it.thesis.scm.repository.ContractRepository;
import pl.lodz.p.it.thesis.scm.repository.StatusRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ContractStatusFinishTask {

    private final StatusRepository statusRepository;
    private final ContractRepository contractRepository;

    @Autowired
    public ContractStatusFinishTask(StatusRepository statusRepository, ContractRepository contractRepository) {
        this.statusRepository = statusRepository;
        this.contractRepository = contractRepository;
    }

    @Scheduled(cron = "${change.status.cron.expression}")
    public void changeStatus() {
        LocalDateTime now = LocalDateTime.from(LocalDateTime.now());
        Status status = statusRepository.findByName("Finished");
        List<Contract> contracts = contractRepository.findAll();
        contracts.stream()
                .filter(contract -> contract.getStatus().getName().equals("Started") && contract.getJob().getCompletionDate().isBefore(now))
                .forEach(contract -> {
                    contract.setStatus(status);
                    contractRepository.save(contract);
                });

    }
}
