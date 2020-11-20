package pl.lodz.p.it.thesis.scm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude= {"refreshTokens", "roles", "workplaces"})
@EqualsAndHashCode(exclude= {"refreshTokens", "roles", "workplaces"})
@SecondaryTable(name = "personal_data")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @NotNull
    private Long version;

    @Column
    @NotNull
    @Email
    private String email;

    @Column
    @NotNull
    @Size(min = 8)
    private String password;

    @Column
    @NotNull
    private boolean enabled;

    @Column(table = "personal_data")
    @NotNull
    @Size(min = 3, max = 20)
    private String name;

    @Column(table = "personal_data")
    @NotNull
    @Size(min = 3, max = 20)
    private String lastName;

    @Column(table = "personal_data")
    @NotNull
    @Size(min = 9, max = 9)
    @Pattern(regexp = "[0-9]+")
    private String phoneNumber;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    @NotNull
    private Collection<Role> roles;

    @OneToMany(mappedBy = "user")
    private Collection<RefreshToken> refreshTokens;

    @OneToMany(mappedBy = "user")
    private Collection<Rate> rates;

    @OneToMany(mappedBy = "employee")
    private Collection<Contract> contracts;

    @OneToMany(mappedBy = "employer")
    private Collection<Workplace> workplaces;

}
