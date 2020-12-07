package pl.lodz.p.it.thesis.scm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.thesis.scm.repository.RefreshTokenRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
@Transactional
public class TokensPurgeTask {

    private final RefreshTokenRepository tokenRepository;

    @Autowired
    public TokensPurgeTask(RefreshTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpired() {
        LocalDateTime now = LocalDateTime.from(LocalDateTime.now());
        tokenRepository.deleteByExpiryDateLessThan(now);
    }
}
