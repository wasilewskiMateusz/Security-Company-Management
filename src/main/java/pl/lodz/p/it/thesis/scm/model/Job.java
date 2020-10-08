package pl.lodz.p.it.thesis.scm.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int vacancy;

    private LocalDateTime startDate;

    private LocalDateTime completionDate;

    private String description;

    private Double wage;

    @ManyToOne(optional=false)
    @JoinColumn(referencedColumnName = "id")
    private Workplace workplace;

    @OneToMany(mappedBy = "job")
    private Collection<Contract> contracts;









}
