package pl.lodz.p.it.thesis.scm.dto.requests;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class JwtRequest implements Serializable {

    @NotNull(message = "Email.not.null")
    private String email;
    @NotNull(message = "Password.not.null")
    private String password;



}
