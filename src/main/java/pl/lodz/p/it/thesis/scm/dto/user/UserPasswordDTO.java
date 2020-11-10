package pl.lodz.p.it.thesis.scm.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.thesis.scm.validation.PasswordMatches;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@NoArgsConstructor
@PasswordMatches
public class UserPasswordDTO extends UserPasswordAbstractDTO{

    @NotNull
    private String version;
}
