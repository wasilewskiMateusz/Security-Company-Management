package pl.lodz.p.it.thesis.scm.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserRegisterDTO {

    private String email;
    private String password;
    private String rePassword;
    private String firstName;
    private String lastName;
    private String phoneNumber;

}
