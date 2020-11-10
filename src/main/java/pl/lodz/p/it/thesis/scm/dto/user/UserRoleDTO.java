package pl.lodz.p.it.thesis.scm.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter
@NoArgsConstructor
public class UserRoleDTO {

    @NotNull
    private ArrayList<Long> newRolesIds;

    @NotNull
    private String version;
}
