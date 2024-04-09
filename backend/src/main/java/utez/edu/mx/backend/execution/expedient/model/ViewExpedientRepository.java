package utez.edu.mx.backend.execution.expedient.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import utez.edu.mx.backend.utils.entity.ReadOnlyRepository;

import java.util.List;

public interface ViewExpedientRepository extends ReadOnlyRepository<ViewExpedients, Long> {

    Page<ViewExpedients> findAllByFolioIsContainingIgnoreCaseAndCreatedByIn(Pageable pageable, String folio, List<Long> createdBy);
    Page<ViewExpedients> findAllByEmailIsContainingIgnoreCaseAndCreatedByIn(Pageable pageable, String phone, List<Long> createdBy);

    Page<ViewExpedients> findAllByCreatedByIn(Pageable pageable, List<Long> createdBy);
}
