package security.template.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.template.InitialUsersSetup;
import security.template.constants.SecurityConstants;
import security.template.dto.UserDto;
import security.template.enums.Authorities;
import security.template.enums.Roles;
import security.template.models.AuthorityEntity;
import security.template.models.RoleEntity;
import security.template.models.User;
import security.template.repo.UserRepository;
import security.template.security.UserPrincipal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    InitialUsersSetup initialUsersSetup;
    ModelMapper modelMapper = new ModelMapper();

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, InitialUsersSetup initialUsersSetup) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.initialUsersSetup = initialUsersSetup;
    }



    public UserDto createUser(UserDto userDto) {
        AuthorityEntity readAuthority = initialUsersSetup.createAuthority(Authorities.READ.name());
        AuthorityEntity writeAuthority = initialUsersSetup.createAuthority(Authorities.WRITE.name());
        AuthorityEntity deleteAuthority = initialUsersSetup.createAuthority(Authorities.DELETE.name());
        User restoreUser = userRepository.findByUserEmail(userDto.getUserEmail());
        if (restoreUser != null) {
//            TODO тут будет exception
        }
        User user = getUserFromDto(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        String defaultCode = SecurityConstants.getAdminCode();
        if (userDto.getCode()!= "" & userDto.getCode().equals(defaultCode)) {
            RoleEntity adminRole = initialUsersSetup.createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(readAuthority,
                writeAuthority, deleteAuthority));
            user.setRoles(Arrays.asList(adminRole));
        } else {
            RoleEntity userRole = initialUsersSetup.createRole(Roles.ROLE_USER.name(), Arrays.asList(readAuthority,
                writeAuthority));
            user.setRoles(Arrays.asList(userRole));
        }
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
        return rez == 1 ? true : false;
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
