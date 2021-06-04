package security.template.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping/v1")
public class PingController {
    @GetMapping()
    public String ping(){
        return "I am work. Access only for SuperAdministrator";
    }
}
