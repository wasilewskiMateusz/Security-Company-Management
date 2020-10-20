package pl.lodz.p.it.thesis.scm.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.thesis.scm.model.User;

@Getter
@NoArgsConstructor
public class UserEditDTO {

    private String name;
    private String lastName;
    private String phoneNumber;

    public UserEditDTO(User user) {
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
    }
}
