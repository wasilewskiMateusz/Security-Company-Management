package pl.lodz.p.it.thesis.scm.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@ToString(exclude= {"rates", "jobs"})
@EqualsAndHashCode(exclude= {"rates", "jobs"})
public class Workplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String address;

    private boolean enable;

    private Double averageRate;


    @OneToMany(mappedBy = "workplace")
    private Collection<Rate> rates;

    @OneToMany(mappedBy = "workplace")
    private Collection<Rate> jobs;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User owner;
}
