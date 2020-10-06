package pl.lodz.p.it.thesis.scm.dto.responses;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtAuthenticateResponse {

    private final String accessToken;
    private String refreshToken;

}
