package security.template.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.template.dto.UserDto;
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
}
