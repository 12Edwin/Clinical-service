package utez.edu.mx.backend.base_catalog.pathology.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface TypePathologicalRepository extends JpaRepository<TypePathological, Long> {

    Optional<TypePathological> findByName(String name);

    @Modifying
    int deleteTypePathologicalById(Long id);
}
