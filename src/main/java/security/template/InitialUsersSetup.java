package security.template;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import security.template.enums.Authorities;
import security.template.enums.Roles;
import security.template.models.AuthorityEntity;
import security.template.models.RoleEntity;
import security.template.models.User;
import security.template.repo.AuthorityRepository;
import security.template.repo.RoleRepository;
import security.template.repo.UserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@Component
public class InitialUsersSetup {

    final AuthorityRepository authorityRepository;
    final RoleRepository roleRepository;
    final UserRepository userRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    public InitialUsersSetup(AuthorityRepository authorityRepository, RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authorityRepository = authorityRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
/**
 * creating authorities
 */
        AuthorityEntity readAuthority = createAuthority(Authorities.READ.name());
        AuthorityEntity writeAuthority = createAuthority(Authorities.WRITE.name());
        AuthorityEntity deleteAuthority = createAuthority(Authorities.DELETE.name());
/**
 * creating roles
 */
/**
 * user can write his profile and read his information
 */
        RoleEntity userRole = createRole(Roles.ROLE_USER.name(), Arrays.asList(readAuthority,
            writeAuthority));
/**
 * admin can write his profile and read all information and delete all profiles (not admin profile)
 */
        RoleEntity adminRole = createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(readAuthority,
            writeAuthority, deleteAuthority));
/**
 * super admin can do everything
 */
        RoleEntity superAdmin = createRole(Roles.ROLE_SUPER_ADMIN.name(), Arrays.asList(readAuthority,
            writeAuthority, deleteAuthority));
/**
 * create superAdministrator
 */
        if (superAdmin == null) return;
        User superAdministrator = User.builder()
            .uuidUser(UUID.randomUUID().toString())
            .firstName("super")
            .secondName("admin")
            .password(bCryptPasswordEncoder.encode("1111"))
            .userEmail("1111@mail.com")
            .confirmEmail(true)
            .roles(Arrays.asList(superAdmin))
            .deleted(false)
            .build();
        userRepository.save(superAdministrator);
    }


    public RoleEntity createRole(String name, Collection<AuthorityEntity> authorityEntities) {
        RoleEntity roleEntity = roleRepository.findByName(name);
        if (roleEntity == null) {
            roleEntity = new RoleEntity(name);
            roleEntity.setAuthorities(authorityEntities);
            roleRepository.save(roleEntity);
        }
        return roleEntity;

    }

    public AuthorityEntity createAuthority(String name) {
        AuthorityEntity authorityEntity = authorityRepository.findByName(name);
        if (authorityEntity == null) {
            authorityEntity = new AuthorityEntity(name);
            authorityRepository.save(authorityEntity);

        }
        return authorityEntity;
    }
}
