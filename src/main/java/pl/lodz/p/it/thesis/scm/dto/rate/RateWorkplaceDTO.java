package pl.lodz.p.it.thesis.scm.dto.rate;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class RateWorkplaceDTO {

    @NotNull
    private Long workplaceId;

    @NotNull
    @Min(0)
    @Max(5)
    private int rate;
}
