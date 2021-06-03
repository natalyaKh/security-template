package security.template.service;

import lombok.val;
import org.apache.catalina.UserDatabase;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import security.template.dto.UserDto;
import security.template.models.User;
import security.template.repo.UserRepository;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    ModelMapper modelMapper = new ModelMapper();

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDto createUser(UserDto userDto) {
        User restoreUser = userRepository.findByUserEmail(userDto.getUserEmail());
        if(restoreUser != null){
//            TODO тут будет exception
        }
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        TODO set role
        user.setUuidUser(UUID.randomUUID().toString());
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }
}
