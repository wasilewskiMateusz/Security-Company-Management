package pl.lodz.p.it.thesis.scm.dto.workplace;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.thesis.scm.model.Workplace;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class CreateWorkplaceDTO {

    @NotNull
    @Size(max = 20)
    private String name;

    @NotNull
    @Size(max = 256)
    private String description;

    @NotNull
    @Size(max = 30)
    private String street;

    @NotNull
    @Size(max = 20)
    private String city;

}
