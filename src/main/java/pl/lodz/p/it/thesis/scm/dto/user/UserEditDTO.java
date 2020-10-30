package pl.lodz.p.it.thesis.scm.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserEditDTO {

    private String name;
    private String lastName;
    private String phoneNumber;
    private String version;
}
