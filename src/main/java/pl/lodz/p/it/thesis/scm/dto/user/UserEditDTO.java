package pl.lodz.p.it.thesis.scm.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@NoArgsConstructor
public class UserEditDTO {

    @NotNull
    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    @Size(min = 3, max = 20)
    private String lastName;

    @NotNull
    @Size(min = 9, max = 9)
    @Pattern(regexp = "[0-9]+")
    private String phoneNumber;

    @NotNull
    private String version;
}
