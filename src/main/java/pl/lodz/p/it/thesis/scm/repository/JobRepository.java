package pl.lodz.p.it.thesis.scm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.thesis.scm.model.Job;

@Repository
@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
public interface JobRepository extends JpaRepository<Job, Long> {
}
