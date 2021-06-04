package security.template.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import security.template.models.AuthorityEntity;
import security.template.models.RoleEntity;
import security.template.models.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private User user;
    private String id;
    public UserPrincipal(User user) {
        this.user = user;
        this.id = user.getUserEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<AuthorityEntity> authorityEntities = new ArrayList<>();
        Collection<RoleEntity> roles = user.getRoles();
        if(roles == null) return authorities;
        roles.forEach((role)-> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorityEntities.addAll(role.getAuthorities());
        });
        authorityEntities.forEach(authorityEntity -> {
          authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()));
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.user.getDeleted();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getConfirmEmail();
    }

    public UserPrincipal() {
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
