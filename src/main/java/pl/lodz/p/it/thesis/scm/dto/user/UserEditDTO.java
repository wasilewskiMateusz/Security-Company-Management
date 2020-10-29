package pl.lodz.p.it.thesis.scm.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.thesis.scm.model.User;

@Getter
@NoArgsConstructor
public class UserEditDTO {

    private String name;
    private String lastName;
    private String phoneNumber;
    private String version;

    public UserEditDTO(User user) {
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.version = DigestUtils.sha256Hex(user.getVersion().toString());
    }
}
