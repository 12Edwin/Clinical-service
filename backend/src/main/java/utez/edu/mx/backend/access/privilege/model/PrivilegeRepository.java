package utez.edu.mx.backend.access.privilege.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Optional<Privilege> findFirstByName(PrivilegeNames name);
}
