package pl.lodz.p.it.thesis.scm.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude="user")
@EqualsAndHashCode(exclude="user")
@Table(name="refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @NotNull
    private Long version;

    @NotNull
    private String token;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @NotNull
    private User user;

    @NotNull
    @Future
    private LocalDateTime expiryDate;

    public RefreshToken(String token, User user, LocalDateTime expiryDate){
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

}
