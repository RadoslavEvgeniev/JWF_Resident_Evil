package residentevil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import residentevil.entities.UserRole;

public interface RoleRepository extends JpaRepository<UserRole, String> {

    UserRole findByAuthority(String authority);
}
