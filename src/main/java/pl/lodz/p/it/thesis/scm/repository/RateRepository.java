package pl.lodz.p.it.thesis.scm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.thesis.scm.model.Rate;
import pl.lodz.p.it.thesis.scm.model.Role;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
}
