package utez.edu.mx.backend.base_catalog.physicalRecord.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Physical_recordRepository extends JpaRepository<Physical_record, Long> {
}
