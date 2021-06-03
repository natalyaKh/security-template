package security.template.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import security.template.models.AuthorityEntity;
import security.template.models.RoleEntity;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
    AuthorityEntity findByName(String name);
}
