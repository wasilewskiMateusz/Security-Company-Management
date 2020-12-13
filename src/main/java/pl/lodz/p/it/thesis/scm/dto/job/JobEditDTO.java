package pl.lodz.p.it.thesis.scm.dto.job;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.thesis.scm.model.Job;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class JobEditDTO {

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
    private String version;

}
