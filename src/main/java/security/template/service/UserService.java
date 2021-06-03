package security.template.service;

import org.springframework.stereotype.Service;
import security.template.dto.UserDto;

@Service
public interface UserService {
    UserDto createUser(UserDto userDto);
}
