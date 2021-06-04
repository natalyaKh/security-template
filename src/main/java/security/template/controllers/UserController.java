package security.template.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import security.template.dto.UserDto;
import security.template.enums.Status;
import security.template.service.UserServiceImpl;

@RestController
@RequestMapping("/users/v1")
public class UserController {

    final
    UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity register(@RequestBody UserDto userDto) {
        return new ResponseEntity(
            userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/test")
    public String test() {
        return "I am work: Test method";
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping()
    public ResponseEntity getAllUsers() {
        return new ResponseEntity(
            userService.getAllUsers(), HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN') or #userEmail == principal.id")
    @GetMapping("/{userEmail}")
    public ResponseEntity getUserByUserUuid(@PathVariable String userEmail) {
        return new ResponseEntity(
            userService.getUserByUserUuid(userEmail), HttpStatus.OK
        );
    }

    //    TODO write normal code with DTO and one parameter
    @PreAuthorize("hasRole('SUPER_ADMIN') or  hasAnyRole('ADMIN', 'USER') and #userEmail == principal.id")
    @PutMapping("put/{userEmail}/{firstName}/{lastName}")
    public ResponseEntity updateUserByUserUuid(@PathVariable String userEmail,
                                               @PathVariable String firstName,
                                               @PathVariable String lastName) {
        return new ResponseEntity(
            userService.updateUserByUserUuid(userEmail, firstName, lastName), HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN') and #userEmail == principal.id")
    @DeleteMapping("/{userEmail}")
    public ResponseEntity deleteUserByUserUuid(@PathVariable String userEmail) {
        boolean deleted = userService.deleteUserByUserUuid(userEmail);
        if (deleted) {
            return new ResponseEntity(
                Status.SUCCESSFUL, HttpStatus.OK
            );
        }
        return new ResponseEntity(
            Status.ERROR, HttpStatus.GONE
        );
    }

}
