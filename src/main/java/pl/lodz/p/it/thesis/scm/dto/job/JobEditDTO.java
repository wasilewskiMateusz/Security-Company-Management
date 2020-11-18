package pl.lodz.p.it.thesis.scm.dto.job;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.thesis.scm.model.Job;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class JobEditDTO {

    private String version;

    private int vacancy;

    private LocalDateTime startDate;

    private LocalDateTime completionDate;

    private String description;

    private Double wage;

    public JobEditDTO(Job job) {
        this.version = DigestUtils.sha256Hex(job.getVersion().toString());
        this.vacancy = job.getVacancy();
        this.description = job.getDescription();
        this.startDate = job.getStartDate();
        this.completionDate = job.getCompletionDate();
        this.wage = job.getWage();
    }

}