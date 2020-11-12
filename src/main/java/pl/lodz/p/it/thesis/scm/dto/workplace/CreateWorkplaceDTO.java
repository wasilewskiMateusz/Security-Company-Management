package pl.lodz.p.it.thesis.scm.dto.workplace;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.thesis.scm.model.Workplace;

@Getter
@NoArgsConstructor
public class CreateWorkplaceDTO {

    private String name;

    private String description;

    private String street;

    private String city;

}
