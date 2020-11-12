package pl.lodz.p.it.thesis.scm.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.thesis.scm.dto.role.RoleDTO;
import pl.lodz.p.it.thesis.scm.model.User;

import java.util.ArrayList;
import java.util.Set;


@Getter
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private boolean enabled;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String version;
    private ArrayList<RoleDTO> roles;

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.version = DigestUtils.sha256Hex(user.getVersion().toString());
        ArrayList<RoleDTO> roleDTOs = new ArrayList<>();
        user.getRoles().forEach(role -> roleDTOs.add(new RoleDTO(role)));
        this.roles = roleDTOs;

    }
}
