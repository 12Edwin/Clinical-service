package utez.edu.mx.backend.base_catalog.speciality.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utez.edu.mx.backend.access.user.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {

    Optional<Speciality> findByName(String name);
    boolean existsByName(String name);

    @Modifying
    int deleteSpecialityById(Long id);

}
