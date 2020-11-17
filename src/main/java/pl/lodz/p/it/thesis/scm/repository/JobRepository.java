package pl.lodz.p.it.thesis.scm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.thesis.scm.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}
