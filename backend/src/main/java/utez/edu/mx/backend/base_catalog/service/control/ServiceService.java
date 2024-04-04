package utez.edu.mx.backend.base_catalog.service.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.base_catalog.service.model.Service;
import utez.edu.mx.backend.base_catalog.service.model.ServiceRepository;
import utez.edu.mx.backend.base_catalog.speciality.model.Speciality;
import utez.edu.mx.backend.base_catalog.speciality.model.SpecialityRepository;
import utez.edu.mx.backend.execution.treatment.model.TreatmentRepository;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
public class ServiceService {

    @Autowired
    private ServiceRepository repository;
    @Autowired
    private SpecialityRepository specialityRepository;
    @Autowired
    private TreatmentRepository treatmentRepository;

    @Transactional(readOnly = true)
    public Optional<Service> findFirstByName(String name){
        return repository.findByName(name);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(Pageable pageable) throws UnsupportedEncodingException, JsonProcessingException {
        return new ResponseEntity<> ( new Message(repository.findAll(pageable), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(Long id) throws UnsupportedEncodingException, JsonProcessingException {
        if (id <= 0) throw new IllegalArgumentException("missing fields");
        Optional<Service> service = repository.findById(id);
        if (service.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(service.get(), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<?> save(Service service) throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {
        if (service.getName() == null || service.getDescription() == null || service.getPrice() <= 0 || service.getSpeciality().getId() <= 0) throw new IllegalArgumentException();
        if (findFirstByName(service.getName()).isPresent()){
            return new ResponseEntity<>(new Message("Duplicated service", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Optional<Speciality> speciality = specialityRepository.findById(service.getSpeciality().getId());
        if (speciality.isEmpty()){
            return new ResponseEntity<>(new Message("Speciality not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        service.setSpeciality(speciality.get());
        Service result = repository.save(service);
        if (repository.findByName(result.getName()).isPresent()){
            return new ResponseEntity<>(new Message(result, "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("Unregistered service", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<?> update(Service service) throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {
        if (service.getName() == null || service.getDescription() == null || service.getPrice() <= 0 || service.getSpeciality().getId() <= 0) throw new IllegalArgumentException();
        if (!repository.existsById(service.getId())){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Optional<Speciality> speciality = specialityRepository.findById(service.getSpeciality().getId());
        if (speciality.isEmpty()){
            return new ResponseEntity<>(new Message("Speciality not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        service.setSpeciality(speciality.get());
        Service result = repository.saveAndFlush(service);
        if (repository.findByName(result.getName()).isPresent()){
            return new ResponseEntity<>(new Message(result, "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("Service not updated", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<?> delete(Long id) throws IllegalArgumentException{
        if (id <= 0) throw new IllegalArgumentException();
        Optional<Service> service = repository.findById(id);
        if (service.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        if (!treatmentRepository.findAllByService(service.get()).isEmpty()){
            return new ResponseEntity<>(new Message("Service is used", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        boolean result = repository.deleteServiceById(id) == 1;
        if (!result){
            return new ResponseEntity<>(new Message("Service not deleted", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message( "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Service saveInitial(Service service){
        return repository.saveAndFlush(service);
    }
}
