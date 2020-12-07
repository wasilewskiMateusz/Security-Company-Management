package pl.lodz.p.it.thesis.scm.dto.workplace;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Getter
@NoArgsConstructor
public class WorkplaceDisabilityDTO {
    @NotNull
    private String version;

}
