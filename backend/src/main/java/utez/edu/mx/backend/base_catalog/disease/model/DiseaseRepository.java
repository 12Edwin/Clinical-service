package utez.edu.mx.backend.base_catalog.disease.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.backend.execution.expedient.model.Expedient;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    void deleteByExpedient(Expedient expedient);
     List<Disease> findAllByExpedient(Expedient expedient);
}
