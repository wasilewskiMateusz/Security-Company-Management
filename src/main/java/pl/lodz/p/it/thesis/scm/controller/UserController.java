package pl.lodz.p.it.thesis.scm.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.thesis.scm.dto.user.*;
import pl.lodz.p.it.thesis.scm.exception.ResourceNotExistException;
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
            throw new ResourceNotExistException();
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

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> editUser(@Valid @RequestBody UserEditDTO userEditDTO,
                                                     @PathVariable Long id) {

        Optional<User> userToEdit = userService.getUser(id);

        if (userToEdit.isEmpty()) {
            throw new ResourceNotExistException();
        }

        String currentVersion = DigestUtils.sha256Hex(userToEdit.get().getVersion().toString());

        if (!userEditDTO.getVersion().equals(currentVersion)) {
            throw new RestException("Exception.different.version");
        }

        User editedUser = userService.editUser(userToEdit.get(), userEditDTO);

        return ResponseEntity.ok(new UserDTO(editedUser));
    }

    @PutMapping("{id}/availability")
    public ResponseEntity<UserDTO> editUserAvailability(@Valid @RequestBody UserAvailabilityDTO userAvailabilityDTO,
                                                     @PathVariable Long id) {

        Optional<User> userToEdit = userService.getUser(id);

        if (userToEdit.isEmpty()) {
            throw new ResourceNotExistException();
        }

        String currentVersion = DigestUtils.sha256Hex(userToEdit.get().getVersion().toString());

        if (!userAvailabilityDTO.getVersion().equals(currentVersion)) {
            throw new RestException("Exception.different.version");
        }

        User editedUser = userService.changeAvailability(userToEdit.get(), userAvailabilityDTO);

        return ResponseEntity.ok(new UserDTO(editedUser));
    }

    @PutMapping("{id}/password")
    public ResponseEntity<UserDTO> editUserPassword(@Valid @RequestBody UserPasswordDTO userPasswordDTO,
                                                        @PathVariable Long id) {

        Optional<User> userToEdit = userService.getUser(id);

        if (userToEdit.isEmpty()) {
            throw new ResourceNotExistException();
        }

        String currentVersion = DigestUtils.sha256Hex(userToEdit.get().getVersion().toString());

        if (!userPasswordDTO.getVersion().equals(currentVersion)) {
            throw new RestException("Exception.different.version");
        }

        User editedUser = userService.changePassword(userToEdit.get(), userPasswordDTO);

        return ResponseEntity.ok(new UserDTO(editedUser));
    }

    @PutMapping("{id}/own-password")
    public ResponseEntity<UserDTO> editOwnPassword(@Valid @RequestBody UserOwnPasswordDTO userOwnPasswordDTO,
                                                 @PathVariable Long id) {

        User editedUser = userService.changeOwnPassword(id, userOwnPasswordDTO);

        return ResponseEntity.ok(new UserDTO(editedUser));
    }

    @PutMapping("{id}/roles")
    public ResponseEntity<UserDTO> editUserRoles(@Valid @RequestBody UserRoleDTO userRoleDTO,
                                                    @PathVariable Long id) {

        Optional<User> userToEdit = userService.getUser(id);

        if (userToEdit.isEmpty()) {
            throw new ResourceNotExistException();
        }

        String currentVersion = DigestUtils.sha256Hex(userToEdit.get().getVersion().toString());

        if (!userRoleDTO.getVersion().equals(currentVersion)) {
            throw new RestException("Exception.different.version");
        }

        User editedUser = userService.changeRoles(userToEdit.get(), userRoleDTO);

        return ResponseEntity.ok(new UserDTO(editedUser));
    }

}
