package pl.lodz.p.it.thesis.scm.dto;


import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.thesis.scm.model.Rate;
import pl.lodz.p.it.thesis.scm.model.User;

import java.util.Collection;

@Getter
@Setter
public class WorkplaceDTO {

    private Long id;

    private String name;

    private String description;

    private String address;

    private boolean enable;

    private Double averageRate;


    private Collection<Rate> rates;

    private Collection<Rate> jobs;

    private User user;

}
