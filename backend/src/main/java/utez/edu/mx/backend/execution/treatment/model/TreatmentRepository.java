package utez.edu.mx.backend.execution.treatment.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.backend.base_catalog.service.model.Service;
import utez.edu.mx.backend.execution.expedient.model.Expedient;

import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    List<Treatment> findAllByService(Service service);

    List<Treatment> findAllByExpedient(Expedient expedient);

    Page<Treatment> findAllByExpedientIn(List<Expedient> expedients, Pageable pageable);

    List<Treatment> findAllByExpedientIn(List<Expedient> expedients);

}
