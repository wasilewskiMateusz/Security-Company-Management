package pl.lodz.p.it.thesis.scm.dto.role;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.thesis.scm.model.Role;

@NoArgsConstructor
@Getter
public class RoleDTO {
    private Long id;
    private String version;
    private String name;

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.version = DigestUtils.sha256Hex(role.getVersion().toString());
        this.name = role.getName();
    }
}
