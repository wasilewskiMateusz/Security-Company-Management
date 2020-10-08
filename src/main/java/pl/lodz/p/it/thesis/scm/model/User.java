package pl.lodz.p.it.thesis.scm.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude= {"refreshTokens", "roles"})
@SecondaryTable(name="personal_data", pkJoinColumns = @PrimaryKeyJoinColumn(name="id"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean enabled;

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

    @Embedded
    private PersonalData personalData;

    @OneToMany(mappedBy = "user")
    private Collection<Rate> rates;

    @OneToMany(mappedBy = "user")
    private Collection<Contract> contracts;

}
