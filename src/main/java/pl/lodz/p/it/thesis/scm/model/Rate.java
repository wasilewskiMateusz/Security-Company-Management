package pl.lodz.p.it.thesis.scm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
@ToString(exclude= {"workplace", "user"})
@EqualsAndHashCode(exclude= {"workplace", "user"})
@NoArgsConstructor
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @NotNull
    private Long version;

    @Column
    @NotNull
    @Min(0)
    @Max(5)
    private int value;

    @ManyToOne(optional=false)
    @JoinColumn(referencedColumnName = "id")
    @NotNull
    private Workplace workplace;

    @ManyToOne(optional=false)
    @JoinColumn(referencedColumnName = "id")
    @NotNull
    private User user;

    public Rate(int value, Workplace workplace, User user) {
        this.value = value;
        this.workplace = workplace;
        this.user = user;
    }
}
