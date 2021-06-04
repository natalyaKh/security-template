package security.template.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import security.template.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserEmail(String userEmail);

    @Modifying
    @Query(value = "update users set deleted = true where user_email=:userEmail",nativeQuery = true)
    Integer deleteUserByUserEmail(String userEmail);

    User findByUserEmailAndDeleted(String userEmail, boolean b);
}
