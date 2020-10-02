package pl.lodz.p.it.thesis.scm.dto.responses;

import lombok.Getter;


@Getter
public class JwtResponse {

    private final String token;

    public JwtResponse(String token) {
        this.token = token;
    }


}
