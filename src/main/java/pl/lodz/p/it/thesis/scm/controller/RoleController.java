package pl.lodz.p.it.thesis.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.thesis.scm.dto.role.RoleDTO;
import pl.lodz.p.it.thesis.scm.model.Role;
import pl.lodz.p.it.thesis.scm.service.IRoleService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("roles")
public class RoleController {

    IRoleService roleService;

    @Autowired
    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAll() {
        List<Role> roles = roleService.getAllRoles();
        List<RoleDTO> roleDTOs = new ArrayList<>();
        roles.forEach(role -> roleDTOs.add(new RoleDTO(role)));
        return ResponseEntity.ok(roleDTOs);
    }

}
