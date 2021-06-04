package security.template.models;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class User extends BaseEntity{
    @Column(nullable = false)
    private String uuidUser;

    @Column(nullable = false)
    private String firstName;

    private String secondName;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String password;
    /** noe all users has confirmed email. We dont need this functionality
     * in this project
     */
    @Column(nullable = false)
    Boolean deleted;
    @Column(nullable = false)
    private Boolean confirmEmail = true;

    @JoinTable(name = "users_roles", joinColumns = @JoinColumn
        (name = "users_id", referencedColumnName = "id"))
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Collection<RoleEntity> roles;
}
