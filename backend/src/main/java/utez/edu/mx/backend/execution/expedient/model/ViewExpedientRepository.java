package utez.edu.mx.backend.execution.expedient.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import utez.edu.mx.backend.utils.entity.ReadOnlyRepository;

public interface ViewExpedientRepository extends ReadOnlyRepository<ViewExpedients, Long> {

    Page<ViewExpedients> findAllByFolioIsContainingIgnoreCase(Pageable pageable, String folio);
    Page<ViewExpedients> findAllByEmailIsContainingIgnoreCase(Pageable pageable, String phone);
}
