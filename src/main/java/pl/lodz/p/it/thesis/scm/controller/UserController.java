package pl.lodz.p.it.thesis.scm.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import pl.lodz.p.it.thesis.scm.dto.user.UserDTO;
import pl.lodz.p.it.thesis.scm.dto.user.UserEditDTO;
import pl.lodz.p.it.thesis.scm.exception.IfMatchValueException;
import pl.lodz.p.it.thesis.scm.exception.RestException;
import pl.lodz.p.it.thesis.scm.model.User;
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
        String version = DigestUtils.sha256Hex(user.get().getVersion().toString());

        return ResponseEntity.ok().eTag(version).body(new UserDTO(user.get()));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> usersDTOS = new ArrayList<>();
        users.forEach(user -> usersDTOS.add(new UserDTO(user)));
        return ResponseEntity.ok(usersDTOS);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserEditDTO> editWorkplace(WebRequest request, @Valid @RequestBody UserEditDTO userEditDTO, @PathVariable Long id) {
        String ifMatchValue = request.getHeader("If-Match");

        Optional<User> userToEdit = userService.getUser(id);

        if (userToEdit.isEmpty()) {
            throw new RestException("Exception.user.not.found");
        }

        if (ifMatchValue == null) {
            throw new RestException("Exception.header.ifMatch.not.found");
        }

        String currentVersion = DigestUtils.sha256Hex(userToEdit.get().getVersion().toString());

        if (!ifMatchValue.equals(currentVersion)) {
            throw new IfMatchValueException();
        }

        User editedUser = userService.editUser(userToEdit.get(), userEditDTO);
        String version = DigestUtils.sha256Hex(editedUser.getVersion().toString());

        return ResponseEntity.ok().eTag(version).body(new UserEditDTO(editedUser));
    }

}
