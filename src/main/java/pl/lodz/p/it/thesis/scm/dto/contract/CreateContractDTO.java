package pl.lodz.p.it.thesis.scm.dto.contract;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CreateContractDTO {

    @NotNull
    private Long jobId;
}
