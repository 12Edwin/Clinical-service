package utez.edu.mx.backend.execution.treatment.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.base_catalog.service.model.ServiceRepository;
import utez.edu.mx.backend.execution.expedient.model.Expedient;
import utez.edu.mx.backend.execution.expedient.model.ExpedientRepository;
import utez.edu.mx.backend.execution.treatment.model.Treatment;
import utez.edu.mx.backend.execution.treatment.model.TreatmentRepository;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class TreatmentService {

    @Autowired
    private final TreatmentRepository repository;
    @Autowired
    private final ExpedientRepository expedientRepository;
    @Autowired
    private final ServiceRepository serviceRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(Pageable pageable) throws UnsupportedEncodingException, JsonProcessingException {
        return new ResponseEntity<> ( new Message(repository.findAll(pageable), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(Long id) throws UnsupportedEncodingException, JsonProcessingException {
        if (id <= 0) throw new IllegalArgumentException("missing fields");
        Optional<Treatment> treatment = repository.findById(id);
        if (treatment.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(treatment.get(), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> save(Treatment treatment)throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {
        if (treatment.getRecommendation() == null || treatment.getStudies_description() == null
            || treatment.getSupport_home() == null || treatment.getService().getId() <= 0
            || treatment.getExpedient().getId() <= 0) throw new IllegalArgumentException();

        Optional<Expedient> expedient = expedientRepository.findById(treatment.getExpedient().getId());
        if (expedient.isEmpty()){
            return new ResponseEntity<>(new Message("Invalid expedient", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Optional<utez.edu.mx.backend.base_catalog.service.model.Service> service = serviceRepository.findById(treatment.getService().getId());
        if (service.isEmpty()){
            return new ResponseEntity<>(new Message("Invalid service", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        treatment.setService(service.get());
        treatment.setExpedient(expedient.get());
        Treatment result = repository.save(treatment);
        if (!repository.existsById(result.getId())){
            return new ResponseEntity<>(new Message("Unregistered treatment", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message(result, "Successful request", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> update(Treatment treatment)throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {
        if (treatment.getRecommendation() == null || treatment.getStudies_description() == null || treatment.getId() <= 0
                || treatment.getSupport_home() == null || treatment.getService().getId() <= 0
                || treatment.getExpedient().getId() <= 0) throw new IllegalArgumentException();

        if (!repository.existsById(treatment.getId())){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<Expedient> expedient = expedientRepository.findById(treatment.getExpedient().getId());
        if (expedient.isEmpty()){
            return new ResponseEntity<>(new Message("Invalid expedient", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Optional<utez.edu.mx.backend.base_catalog.service.model.Service> service = serviceRepository.findById(treatment.getService().getId());
        if (service.isEmpty()){
            return new ResponseEntity<>(new Message("Invalid service", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        treatment.setService(service.get());
        treatment.setExpedient(expedient.get());
        Treatment result = repository.saveAndFlush(treatment);
        if (!repository.existsById(result.getId())){
            return new ResponseEntity<>(new Message("Treatment not updated", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message(result, "Successful request", TypeResponse.SUCCESS), HttpStatus.OK);
    }
}
