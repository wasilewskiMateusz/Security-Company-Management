package pl.lodz.p.it.thesis.scm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString(exclude= {"workplace", "user"})
@EqualsAndHashCode(exclude= {"workplace", "user"})
@NoArgsConstructor
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private @Version Long version;

    private int value;

    @ManyToOne(optional=false)
    @JoinColumn(referencedColumnName = "id")
    private Workplace workplace;

    @ManyToOne(optional=false)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    public Rate(int value, Workplace workplace, User user) {
        this.value = value;
        this.workplace = workplace;
        this.user = user;
    }
}
