package pl.lodz.p.it.thesis.scm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@ToString(exclude= {"status", "user","job"})
@EqualsAndHashCode(exclude= {"status", "user","job"})
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Status status;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Job job;

}
