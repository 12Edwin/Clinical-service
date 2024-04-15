package utez.edu.mx.backend.access.role.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findFirstByName(RoleTypes name);
}
