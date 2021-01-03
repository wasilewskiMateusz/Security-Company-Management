package pl.lodz.p.it.thesis.scm.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.thesis.scm.validation.PasswordMatches;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@PasswordMatches
public class UserResetPasswordTokenDTO extends UserPasswordAbstractDTO{

    @NotNull
    @Size(min = 30, max = 30)
    private String token;
}
