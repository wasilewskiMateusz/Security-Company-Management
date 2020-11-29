package pl.lodz.p.it.thesis.scm.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.thesis.scm.dto.role.RoleDTO;
import pl.lodz.p.it.thesis.scm.model.User;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class UserContractDTO {
    private Long id;
    private String email;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String version;

    public UserContractDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.version = DigestUtils.sha256Hex(user.getVersion().toString());
    }
}
