package pl.lodz.p.it.thesis.scm.dto.job;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class JobDisabilityDTO {
    @NotNull
    private String version;
}
