package pl.lodz.p.it.thesis.scm.dto.user;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public abstract class UserPasswordAbstractDTO {

    @NotNull
    @Size(min = 8)
    private String password;

    @NotNull
    @Size(min = 8)
    private String rePassword;
}
