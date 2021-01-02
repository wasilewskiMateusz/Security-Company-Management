package pl.lodz.p.it.thesis.scm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.thesis.scm.model.RefreshToken;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    List<RefreshToken> findByUserEmail(String user);
    void deleteByExpiryDateLessThan(LocalDateTime now);
}
