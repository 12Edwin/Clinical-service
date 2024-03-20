package utez.edu.mx.backend.access.user.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByCode(String code);
    boolean existsByCode(String code);
}
