package pl.lodz.p.it.thesis.scm.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserPasswordDTO {
    private String password;
    private String rePassword;
    private String version;

}
