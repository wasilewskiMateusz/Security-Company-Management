package pl.lodz.p.it.thesis.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.thesis.scm.dto.user.UserDTO;
import pl.lodz.p.it.thesis.scm.dto.workplace.WorkplaceDTO;
import pl.lodz.p.it.thesis.scm.exception.RestException;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.model.Workplace;
import pl.lodz.p.it.thesis.scm.service.IUserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> get(@PathVariable Long id) {
        Optional<User> user = userService.getUser(id);
        if (user.isEmpty()) {
            throw new RestException("Exception.user.not.found");
        }
        return ResponseEntity.ok(new UserDTO(user.get()));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> usersDTOS = new ArrayList<>();
        users.forEach(user -> usersDTOS.add(new UserDTO(user)));
        return ResponseEntity.ok(usersDTOS);
    }

    @PutMapping
    public ResponseEntity<UserDTO> editWorkplace(@Valid @RequestBody UserDTO userDTO) {
        User editedUser = userService.editUser(userDTO);
        if(editedUser == null) {
            throw new RestException("Exception.user.not.found");
        }
        return ResponseEntity.ok(new UserDTO(editedUser));
    }

}
