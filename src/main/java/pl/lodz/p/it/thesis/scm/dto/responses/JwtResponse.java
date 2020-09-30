package pl.lodz.p.it.thesis.scm.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
public class JwtResponse implements Serializable {

    @Getter
    private final String token;
}
