package pl.lodz.p.it.thesis.scm.dto.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserRequest {

    @NotNull(message = "Email.not.null")
    private String email;

    @NotNull(message = "Password.not.null")
    private String password;
}
