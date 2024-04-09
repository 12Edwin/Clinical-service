package utez.edu.mx.backend.base_catalog.space.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

    Optional<Space> findFirstByName(String name);

    Optional<Space> findFirstByNameAndIdNot(String name, Long id);
}
