package pl.lodz.p.it.thesis.scm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@ToString(exclude= {"status", "employee","job"})
@EqualsAndHashCode(exclude= {"status", "employee","job"})
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private @Version Long version;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Status status;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User employee;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Job job;

    public Contract(User employee, Job job) {
        this.employee = employee;
        this.job = job;
    }
}
