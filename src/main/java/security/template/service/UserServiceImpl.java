package security.template.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.template.dto.UserDto;
import security.template.enums.Status;
import security.template.models.User;
import security.template.repo.UserRepository;
import security.template.security.UserPrincipal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        if (restoreUser != null) {
//            TODO тут будет exception
        }
        User user = getUserFromDto(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        TODO set role
        user.setUuidUser(UUID.randomUUID().toString());

        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    private User getUserFromDto(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private UserDto getDtoFromUser(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepository.findAll();
        if (userList.size() == 0) return new ArrayList<>();
        List<UserDto> userDtoList = userList.stream().map(this::getDtoFromUser).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public UserDto getUserByUserUuid(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail);
        if (user == null) {
//            TODO тут будет exception
        }
        return getDtoFromUser(user);
    }

    @Override
    public UserDto updateUserByUserUuid(String userEmail, String firstName, String lastName) {
        User user = userRepository.findByUserEmailAndDeleted(userEmail, false);
        if (user == null) {
//            TODO тут будет exception
        }
        if (firstName != null) user.setFirstName(firstName);
        if (lastName != null) user.setSecondName(lastName);
        userRepository.save(user);
            return getDtoFromUser(user);
    }

    @Override
    @Transactional
    public Boolean deleteUserByUserUuid(String userEmail) {
        Integer rez = userRepository.deleteUserByUserEmail(userEmail);
//        if (user == null) {
//           return false;
//        }
//        userRepository.delete(user);
        return rez==1?true:false;
    }

    @Override
    public UserPrincipal loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(email);
        if (user == null) {
//            TODO тут будет exception
        }
        return new UserPrincipal(user);
//                new org.springframework.security.core.userdetails.User(user.getUserEmail(),
//                    user.getPassword(), true,
//                    true, true,
//                    true, new ArrayList<>());

    }

}
