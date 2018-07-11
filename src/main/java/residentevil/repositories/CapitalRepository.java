package residentevil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import residentevil.entities.Capital;

@Repository
public interface CapitalRepository extends JpaRepository<Capital, Long> {
}
