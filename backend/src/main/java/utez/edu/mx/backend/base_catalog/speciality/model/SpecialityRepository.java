package utez.edu.mx.backend.base_catalog.speciality.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {

    Optional<Speciality> findByName(String name);

    Optional<Speciality> findFirstByNameAndIdNot(String name, Long id);
    boolean existsByName(String name);

    @Modifying
    int deleteSpecialityById(Long id);

}
