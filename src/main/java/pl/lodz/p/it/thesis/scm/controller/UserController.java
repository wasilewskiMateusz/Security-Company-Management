package pl.lodz.p.it.thesis.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.thesis.scm.dto.user.*;
import pl.lodz.p.it.thesis.scm.dto.workplace.WorkplaceDTO;
import pl.lodz.p.it.thesis.scm.exception.ResourceNotExistException;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.model.Workplace;
import pl.lodz.p.it.thesis.scm.service.IUserService;
import pl.lodz.p.it.thesis.scm.service.IWorkplaceService;

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

        User editedUser = userService.editUser(id, userEditDTO);

        return ResponseEntity.ok(new UserDTO(editedUser));
    }

    @PutMapping("{id}/availability")
    public ResponseEntity<UserDTO> editUserAvailability(@Valid @RequestBody UserAvailabilityDTO userAvailabilityDTO,
                                                     @PathVariable Long id) {

        User editedUser = userService.changeAvailability(id, userAvailabilityDTO);

        return ResponseEntity.ok(new UserDTO(editedUser));
    }

    @PutMapping("{id}/password")
    public ResponseEntity<UserDTO> editUserPassword(@Valid @RequestBody UserPasswordDTO userPasswordDTO,
                                                        @PathVariable Long id) {

        User editedUser = userService.changePassword(id, userPasswordDTO);

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

        User editedUser = userService.changeRoles(id, userRoleDTO);

        return ResponseEntity.ok(new UserDTO(editedUser));
    }

    @GetMapping("{id}/workplaces")
    public ResponseEntity<List<WorkplaceDTO>> getUserWorkplaces(@PathVariable Long id) {

        List<Workplace> workplaces = userService.getUserWorkplaces(id);
        List<WorkplaceDTO> workplaceDTOS = new ArrayList<>();
        workplaces.forEach(workplace -> workplaceDTOS.add(new WorkplaceDTO(workplace)));
        return ResponseEntity.ok(workplaceDTOS);
    }

}
