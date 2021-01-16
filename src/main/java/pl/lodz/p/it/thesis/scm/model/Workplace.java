package pl.lodz.p.it.thesis.scm.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Data
@ToString(exclude= {"rates", "jobs"})
@EqualsAndHashCode(exclude= {"rates", "jobs"})
@SecondaryTable(name = "address")
@Table(name="workplace")
public class Workplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @NotNull
    private Long version;

    @Column
    @NotNull
    @Size(max = 20)
    private String name;

    @Column
    @NotNull
    @Size(max = 256)
    private String description;

    @Column(table = "address")
    @NotNull
    @Size(max = 20)
    private String city;

    @Column(table = "address")
    @NotNull
    @Size(max = 30)
    private String street;

    @Column
    @NotNull
    private boolean enabled;

    @Column
    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private Double averageRate;


    @OneToMany(mappedBy = "workplace")
    private Collection<Rate> rates;

    @OneToMany(mappedBy = "workplace")
    private Collection<Job> jobs;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @NotNull
    private User employer;
}
