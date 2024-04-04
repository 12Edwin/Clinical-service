package utez.edu.mx.backend.base_catalog.pathology.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import utez.edu.mx.backend.execution.expedient.model.Expedient;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface PathologicalRepository extends JpaRepository<Pathological_record, Long> {

    Optional<Pathological_record> findByName(String name);

    List<Pathological_record> findAllByExpedient(Expedient expedient);

    void deletePathological_recordByExpedient(Expedient expedient);
    @Modifying
    int deletePathological_recordById(Long id);

    List<Pathological_record> findAllByTypePathological(TypePathological pathological);
}
