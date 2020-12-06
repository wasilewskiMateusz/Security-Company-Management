package pl.lodz.p.it.thesis.scm.dto.contract;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.thesis.scm.model.Contract;

@Getter
@NoArgsConstructor
public class ContractCheckInDTO {

    private String version;

    public ContractCheckInDTO(Contract contract) {
        this.version = DigestUtils.sha256Hex(contract.getVersion().toString());
    }
}
