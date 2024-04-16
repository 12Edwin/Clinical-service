package utez.edu.mx.backend.base_catalog.service.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import utez.edu.mx.backend.base_catalog.speciality.model.Speciality;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByName(String name);

    Optional<Service> findFirstByNameAndIdNot(String name, Long id);
    List<Service> findAllBySpeciality(Speciality speciality);

    @Modifying
    int deleteServiceById(Long id);
}
