package pl.lodz.p.it.thesis.scm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.thesis.scm.model.Workplace;
import pl.lodz.p.it.thesis.scm.repository.RefreshTokenRepository;
import pl.lodz.p.it.thesis.scm.repository.WorkplaceRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
public class WorkplacePurgeTask {

    private final WorkplaceRepository workplaceRepository;

    @Autowired
    public WorkplacePurgeTask(WorkplaceRepository workplaceRepository) {
        this.workplaceRepository = workplaceRepository;
    }

    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeDisabled() {
       List<Workplace> workplaces = workplaceRepository.findAll();
        workplaces.stream()
                .filter(workplace -> workplace.getJobs().size() == 0 && !workplace.isEnabled())
                .forEach(workplaceRepository::delete);
    }
}
