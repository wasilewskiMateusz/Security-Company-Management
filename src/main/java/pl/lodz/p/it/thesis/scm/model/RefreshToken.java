package pl.lodz.p.it.thesis.scm.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude="user")
@EqualsAndHashCode(exclude="user")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String token;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;

    public RefreshToken(String token, User user){
        this.token = token;
        this.user = user;
    }

}
