package security.template.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import security.template.dto.UserDto;

@Service
public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
}
