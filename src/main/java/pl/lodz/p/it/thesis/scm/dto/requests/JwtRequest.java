package pl.lodz.p.it.thesis.scm.dto.requests;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class JwtRequest implements Serializable {

    @NotNull(message = "field.can.not.be.null")
    private String email;
    @NotNull(message = "field.can.not.be.null")
    private String password;



}
