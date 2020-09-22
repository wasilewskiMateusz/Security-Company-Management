package pl.lodz.p.it.thesis.scm.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
public class JwtResponse implements Serializable {

    @Getter
    private final String token;
}
