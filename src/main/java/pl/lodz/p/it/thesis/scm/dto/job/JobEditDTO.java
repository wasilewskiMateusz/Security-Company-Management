package pl.lodz.p.it.thesis.scm.dto.job;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.thesis.scm.model.Job;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class JobEditDTO {

    private int vacancy;

    private LocalDateTime startDate;

    private LocalDateTime completionDate;

    private String description;

    private Double wage;

    private String version;

}
