package pl.lodz.p.it.thesis.scm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@ToString(exclude= {"status", "employee","job"})
@EqualsAndHashCode(exclude= {"status", "employee","job"})
@NoArgsConstructor
@Table(name="contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @NotNull
    private Long version;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @NotNull
    private Status status;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @NotNull
    private User employee;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @NotNull
    private Job job;

    public Contract(User employee, Job job) {
        this.employee = employee;
        this.job = job;
    }
}
