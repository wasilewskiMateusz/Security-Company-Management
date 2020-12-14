package pl.lodz.p.it.thesis.scm.dto.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class JwtRefreshRequest {

    @NotNull
    private String refreshToken;
}
