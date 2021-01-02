package pl.lodz.p.it.thesis.scm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.thesis.scm.model.Role;
import pl.lodz.p.it.thesis.scm.repository.RoleRepository;
import pl.lodz.p.it.thesis.scm.service.IRoleService;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
public class RoleService implements IRoleService {

    RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
       return this.roleRepository.findAll();
    }
}
