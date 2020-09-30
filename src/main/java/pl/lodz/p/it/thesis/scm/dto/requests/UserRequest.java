package pl.lodz.p.it.thesis.scm.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotNull(message = "field.ca.not.be.null")
    private String email;

    @NotNull(message = "field.ca.not.be.null")
    private String password;
}
