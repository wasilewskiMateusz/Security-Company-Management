package pl.lodz.p.it.thesis.scm.dto.workplace;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.thesis.scm.model.Workplace;

@Getter
@NoArgsConstructor
public class WorkplaceDTO {

    private Long id;

    private String name;

    private String description;

    private String address;

    private boolean enable;

    private Double averageRate;

    public WorkplaceDTO(Workplace workplace) {
        this.id = workplace.getId();
        this.name = workplace.getName();
        this.description = workplace.getDescription();
        this.address = workplace.getAddress();
        this.enable = workplace.isEnable();
        this.averageRate = workplace.getAverageRate();
    }

    public static Workplace toWorkplace(WorkplaceDTO workplaceDTO) {
        Workplace workplace = new Workplace();
        workplace.setId(workplaceDTO.getId());
        workplace.setName(workplaceDTO.getName());
        workplace.setDescription(workplaceDTO.getDescription());
        workplace.setAddress(workplaceDTO.getAddress());
        workplace.setEnable(workplaceDTO.isEnable());
        workplace.setAverageRate(workplaceDTO.getAverageRate());
        return workplace;
    }
}
