package pl.lodz.p.it.thesis.scm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.thesis.scm.model.Workplace;

@Repository
public interface WorkplaceRepository extends JpaRepository<Workplace, Long> {
}
