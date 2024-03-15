package utez.edu.mx.backend.base_catalog.pathology.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypePathologicalRepository extends JpaRepository<TypePathological, Long> {

    Optional<TypePathological> findByName(String name);
}
