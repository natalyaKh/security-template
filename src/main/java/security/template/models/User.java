package security.template.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
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
    private Boolean confirmEmail = true;
}
