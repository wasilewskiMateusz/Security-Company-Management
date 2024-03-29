package pl.lodz.p.it.thesis.scm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude= {"users"})
@EqualsAndHashCode(exclude= {"users"})
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @NotNull
    private Long version;

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;


    public Role(String name) {
        this.name = name;
    }
}
