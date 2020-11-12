package pl.lodz.p.it.thesis.scm.dto.workplace;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WorkplaceEditDTO {

    private String name;

    private String description;

    private String street;

    private String city;

    private String version;
}
