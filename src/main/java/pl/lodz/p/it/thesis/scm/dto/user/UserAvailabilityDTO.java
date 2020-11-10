package pl.lodz.p.it.thesis.scm.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Getter
@NoArgsConstructor
public class UserAvailabilityDTO {
    @NotNull
    private boolean enable;
    @NotNull
    private String version;

}
