package pl.lodz.p.it.thesis.scm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Entity
@ToString(exclude= {"contracts"})
@EqualsAndHashCode(exclude= {"contracts"})
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @NotNull
    private Long version;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "status")
    Collection<Contract> contracts;
}
