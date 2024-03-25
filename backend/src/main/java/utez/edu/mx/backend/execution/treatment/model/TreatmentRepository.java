package utez.edu.mx.backend.execution.treatment.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.backend.base_catalog.service.model.Service;

import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    List<Treatment> findAllByService(Service service);
}
