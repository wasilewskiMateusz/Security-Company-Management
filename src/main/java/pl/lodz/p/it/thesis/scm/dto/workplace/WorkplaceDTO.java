package pl.lodz.p.it.thesis.scm.dto.workplace;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import pl.lodz.p.it.thesis.scm.model.Workplace;

@Getter
@NoArgsConstructor
public class WorkplaceDTO {

    private Long id;

    private String name;

    private String description;

    private String street;

    private String city;

    private boolean enabled;

    private Double averageRate;

    private String version;

    public WorkplaceDTO(Workplace workplace) {
        this.id = workplace.getId();
        this.name = workplace.getName();
        this.description = workplace.getDescription();
        this.street = workplace.getStreet();
        this.city = workplace.getCity();
        this.enabled = workplace.isEnabled();
        this.averageRate = workplace.getAverageRate();
        this.version = DigestUtils.sha256Hex(workplace.getVersion().toString());

    }
}
