package pl.lodz.p.it.thesis.scm.dto.contract;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.thesis.scm.dto.job.JobDTO;
import pl.lodz.p.it.thesis.scm.dto.user.UserContractDTO;
import pl.lodz.p.it.thesis.scm.model.Contract;


@Getter
@NoArgsConstructor
public class ContractDTO {

    private Long id;

    private String version;

    private String status;

    private JobDTO job;

    private UserContractDTO user;

    public ContractDTO(Contract contract) {
        this.id = contract.getId();
        this.version = DigestUtils.sha256Hex(contract.getVersion().toString());
        this.status = contract.getStatus().getName();
        this.job = new JobDTO(contract.getJob());
        this.user = new UserContractDTO(contract.getEmployee());
    }
}
