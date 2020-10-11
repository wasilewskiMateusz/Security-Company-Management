package pl.lodz.p.it.thesis.scm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
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

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean enabled;

    @Column(table = "personal_data")
    private String name;

    @Column(table = "personal_data")
    private String lastName;

    @Column(table = "personal_data")
    private String phoneNumber;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @OneToMany(mappedBy = "user")
    private Collection<RefreshToken> refreshTokens;

    @OneToMany(mappedBy = "user")
    private Collection<Rate> rates;

    @OneToMany(mappedBy = "user")
    private Collection<Contract> contracts;

    @OneToMany(mappedBy = "employer")
    private Collection<Workplace> workplaces;

}
