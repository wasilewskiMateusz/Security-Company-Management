package pl.lodz.p.it.thesis.scm.dto.job;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.thesis.scm.dto.workplace.WorkplaceDTO;
import pl.lodz.p.it.thesis.scm.model.Job;
import pl.lodz.p.it.thesis.scm.model.Workplace;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class JobDTO {

    private Long id;

    private String version;

    private int vacancy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completionDate;

    private String description;

    private boolean enabled;

    private Double wage;

    private WorkplaceDTO workplace;

    public JobDTO(Job job) {
        this.id = job.getId();
        this.version = DigestUtils.sha256Hex(job.getVersion().toString());
        this.vacancy = job.getVacancy();
        this.description = job.getDescription();
        this.startDate = job.getStartDate();
        this.completionDate = job.getCompletionDate();
        this.enabled = job.isEnabled();
        this.wage = job.getWage();
        this.workplace = new WorkplaceDTO(job.getWorkplace());

    }
}
