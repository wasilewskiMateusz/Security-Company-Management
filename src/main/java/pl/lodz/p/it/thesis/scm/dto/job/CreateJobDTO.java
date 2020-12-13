package pl.lodz.p.it.thesis.scm.dto.job;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CreateJobDTO {

    @NotNull
    @Min(1)
    private int vacancy;

    @NotNull
    @Future
    private LocalDateTime startDate;

    @NotNull
    @Future
    private LocalDateTime completionDate;

    @NotNull
    @Size(max = 256)
    private String description;

    @NotNull
    @Min(1)
    private Double wage;

    @NotNull
    private Long workplaceId;

}
