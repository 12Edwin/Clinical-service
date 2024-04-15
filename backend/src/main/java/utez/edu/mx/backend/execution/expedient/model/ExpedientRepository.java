package utez.edu.mx.backend.execution.expedient.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpedientRepository extends JpaRepository<Expedient, Long> {
}
