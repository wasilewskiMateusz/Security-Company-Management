package pl.lodz.p.it.thesis.scm.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class JwtRequest implements Serializable {

    private String username;
    private String password;



}
