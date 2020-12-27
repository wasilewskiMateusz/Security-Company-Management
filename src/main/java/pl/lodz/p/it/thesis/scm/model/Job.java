package pl.lodz.p.it.thesis.scm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @Version
    @NotNull
    private Long version;

    @Column
    @NotNull
    @Min(0)
    private int vacancy;

    @Column
    @NotNull
    @Future
    private LocalDateTime startDate;

    @Column
    @NotNull
    @Future
    private LocalDateTime completionDate;

    @Column
    @NotNull
    @Size(max = 256)
    private String description;

    @Column
    @NotNull
    private boolean enabled;

    @Column
    @NotNull
    @Min(1)
    private Double wage;

    @ManyToOne(optional=false)
    @JoinColumn(referencedColumnName = "id")
    @NotNull
    private Workplace workplace;

    @OneToMany(mappedBy = "job")
    private Collection<Contract> contracts;









}
