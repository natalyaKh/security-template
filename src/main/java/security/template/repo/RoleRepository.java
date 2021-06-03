package security.template.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import security.template.models.RoleEntity;
import security.template.models.User;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
