package security.template.models;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
public class RoleEntity extends BaseEntity{
    @Column(nullable = false, length = 20)
    String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
    @ManyToMany(cascade= { CascadeType.PERSIST }, fetch = FetchType.EAGER )
    @JoinTable(name="roles_authorities",
        joinColumns=@JoinColumn(name="roles_id",referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="authorities_id",referencedColumnName="id"))
    private Collection<AuthorityEntity> authorities;

    public RoleEntity(String name) {
        this.name = name;
    }
}
