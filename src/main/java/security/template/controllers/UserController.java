package security.template.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity register(@RequestBody UserDto userDto){
        return new ResponseEntity(
            userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/test")
    public String test(){
        return "I am work: Test method";
    }

    @GetMapping()
    public ResponseEntity getAllUsers(){
        return new ResponseEntity(
            userService.getAllUsers(), HttpStatus.OK
        );
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity getUserByUserUuid(@PathVariable String userEmail){
        return new ResponseEntity(
            userService.getUserByUserUuid(userEmail), HttpStatus.OK
        );
    }

//    TODO write normal code with DTO and one parameter
    @PutMapping("put/{userEmail}/{firstName}/{lastName}")
    public ResponseEntity updateUserByUserUuid(@PathVariable String userEmail,
                                               @PathVariable String firstName,
                                               @PathVariable String lastName){
        return new ResponseEntity(
            userService.updateUserByUserUuid(userEmail, firstName, lastName), HttpStatus.OK
        );
    }

    @DeleteMapping("/{userEmail}")
    public ResponseEntity deleteUserByUserUuid(@PathVariable String userEmail){
        boolean deleted = userService.deleteUserByUserUuid(userEmail);
        if(deleted){
            return new ResponseEntity(
                Status.SUCCESSFUL, HttpStatus.OK
            );
        }
        return new ResponseEntity(
            Status.ERROR, HttpStatus.GONE
        );
    }

}
