package pl.lodz.p.it.thesis.scm.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.thesis.scm.model.User;

@Getter
@NoArgsConstructor
public class UserEditDTO {

    private String name;
    private String lastName;
    private String phoneNumber;
    private String password;

    public UserEditDTO(User user) {
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
    }

    public static User toUser(UserEditDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return user;
    }
}
