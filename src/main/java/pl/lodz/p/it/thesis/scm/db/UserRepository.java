package pl.lodz.p.it.thesis.scm.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.thesis.scm.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     User findByEmail(String email);
}
