package pl.lodz.p.it.thesis.scm.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtRefreshResponse {

    private final String accessToken;
}
