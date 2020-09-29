package pl.lodz.p.it.thesis.scm.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.thesis.scm.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
