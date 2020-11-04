package pl.lodz.p.it.thesis.scm.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class UserRoleDTO {

    private ArrayList<Long> newRolesIds;
    private String version;
}
