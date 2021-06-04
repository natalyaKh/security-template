package security.template.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.template.constants.SecurityConstants;

@RestController
@RequestMapping("/ping/v1")
@PreAuthorize("hasRole('SUPER_ADMIN')")
//@PreAuthorize("isAuthenticated()")
public class PingController {

    @GetMapping()
    public String pingAdmin() {
        return "I am work on port: " + SecurityConstants.getPort();
    }
}
