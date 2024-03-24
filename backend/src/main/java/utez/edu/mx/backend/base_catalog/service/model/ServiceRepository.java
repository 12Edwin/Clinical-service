package utez.edu.mx.backend.base_catalog.service.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.backend.base_catalog.speciality.model.Speciality;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByName(String name);
    List<Service> findAllBySpeciality(Speciality speciality);
}
