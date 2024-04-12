package utez.edu.mx.backend.execution.ratting.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RattingRepository extends JpaRepository<Ratting, Long> {
}
