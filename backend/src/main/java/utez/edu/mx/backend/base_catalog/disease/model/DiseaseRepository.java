package utez.edu.mx.backend.base_catalog.disease.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utez.edu.mx.backend.execution.expedient.model.Expedient;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    @Modifying
    @Query(value = "DELETE FROM diseases d WHERE d.expedient_id = :idExpedient", nativeQuery = true)
    void deleteDiseasesByExpedient(@Param("idExpedient") Long idExpedient);
     List<Disease> findAllByExpedient(Expedient expedient);
}
