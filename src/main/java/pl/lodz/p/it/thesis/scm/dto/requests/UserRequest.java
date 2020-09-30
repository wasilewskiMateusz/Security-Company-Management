package pl.lodz.p.it.thesis.scm.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
