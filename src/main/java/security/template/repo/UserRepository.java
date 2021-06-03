package security.template.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import security.template.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserEmail(String userEmail);
}
