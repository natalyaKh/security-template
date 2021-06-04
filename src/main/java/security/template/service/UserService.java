package security.template.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import security.template.dto.UserDto;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    List<UserDto> getAllUsers();

    UserDto getUserByUserUuid(String userEmail);

    UserDto updateUserByUserUuid(String userEmail, String firstName, String lastName);

    Boolean deleteUserByUserUuid(String userEmail);
}
