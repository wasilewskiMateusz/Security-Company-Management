package pl.lodz.p.it.thesis.scm.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.thesis.scm.model.User;


@Getter
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private boolean enabled;
    private String name;
    private String lastName;
    private String phoneNumber;

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
        this.name = user.getPersonalData().getName();
        this.lastName = user.getPersonalData().getLastName();
        this.phoneNumber = user.getPersonalData().getPhoneNumber();

    }

    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(user.getEmail());
        user.setEnabled(userDTO.isEnabled());
        user.getPersonalData().setName(userDTO.getName());
        user.getPersonalData().setLastName(userDTO.getLastName());
        user.getPersonalData().setPhoneNumber(userDTO.getPhoneNumber());
        return user;
    }
}
