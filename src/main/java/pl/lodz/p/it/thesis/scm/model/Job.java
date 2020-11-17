package pl.lodz.p.it.thesis.scm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity
@ToString(exclude= {"workplace", "contracts"})
@EqualsAndHashCode(exclude= {"workplace", "contracts"})
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private @Version Long version;

    private int vacancy;

    private LocalDateTime startDate;

    private LocalDateTime completionDate;

    private String description;

    private boolean enabled;

    private Double wage;

    @ManyToOne(optional=false)
    @JoinColumn(referencedColumnName = "id")
    private Workplace workplace;

    @OneToMany(mappedBy = "job")
    private Collection<Contract> contracts;









}
