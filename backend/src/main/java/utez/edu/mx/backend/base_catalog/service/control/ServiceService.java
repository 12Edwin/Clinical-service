package utez.edu.mx.backend.base_catalog.service.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.access.user.model.UserRepository;
import utez.edu.mx.backend.base_catalog.service.model.Service;
import utez.edu.mx.backend.base_catalog.service.model.ServiceRepository;
import utez.edu.mx.backend.base_catalog.speciality.model.Speciality;
import utez.edu.mx.backend.base_catalog.speciality.model.SpecialityRepository;
import utez.edu.mx.backend.execution.treatment.model.TreatmentRepository;
import utez.edu.mx.backend.utils.entity.BadRequests;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
@AllArgsConstructor
public class ServiceService {

    private final ServiceRepository repository;
    private final SpecialityRepository specialityRepository;
    private final TreatmentRepository treatmentRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<Service> findFirstByName(String name){
        return repository.findByName(name);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findAll(Pageable pageable) throws UnsupportedEncodingException, JsonProcessingException {
        return new ResponseEntity<> ( new Message(repository.findAll(pageable), BadRequests.REQUESTS_SUCCESS.getText(), TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findServices(Long id_user) throws UnsupportedEncodingException, JsonProcessingException {
        Optional<User> user = userRepository.findById(id_user);
        if (user.isEmpty()){
            return new ResponseEntity<>(new Message(BadRequests.USER_NOT_FOUND.getText(), TypeResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<> ( new Message(repository.findAllBySpeciality(user.get().getSpeciality()), BadRequests.REQUESTS_SUCCESS.getText(), TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findById(Long id) throws UnsupportedEncodingException, JsonProcessingException {
        if (id <= 0) throw new IllegalArgumentException(BadRequests.MISSING_FIELDS.getText());
        Optional<Service> service = repository.findById(id);
        if (service.isEmpty()){
            return new ResponseEntity<>(new Message(BadRequests.NOT_FOUND.getText(), TypeResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(service.get(), BadRequests.REQUESTS_SUCCESS.getText(), TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> save(Service service) throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {
        if (service.getName() == null || service.getDescription() == null || service.getPrice() < 0 || service.getSpeciality().getId() <= 0) throw new IllegalArgumentException();
        if (this.findFirstByName(service.getName()).isPresent()){
            return new ResponseEntity<>(new Message("Duplicated service", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Optional<Speciality> speciality = specialityRepository.findById(service.getSpeciality().getId());
        if (speciality.isEmpty()){
            return new ResponseEntity<>(new Message("Speciality not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        service.setSpeciality(speciality.get());
        Service result = repository.save(service);
        if (repository.findByName(result.getName()).isPresent()){
            return new ResponseEntity<>(new Message(result, BadRequests.REQUESTS_SUCCESS.getText(), TypeResponse.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("Unregistered service", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> update(Service service) throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {
        if (service.getName() == null || service.getDescription() == null || service.getPrice() < 0 || service.getSpeciality().getId() <= 0) throw new IllegalArgumentException();
        if (!repository.existsById(service.getId())){
            return new ResponseEntity<>(new Message(BadRequests.NOT_FOUND.getText(), TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (repository.findFirstByNameAndIdNot(service.getName(), service.getId()).isPresent()){
            return new ResponseEntity<>(new Message("Duplicated service", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Optional<Speciality> speciality = specialityRepository.findById(service.getSpeciality().getId());
        if (speciality.isEmpty()){
            return new ResponseEntity<>(new Message("Speciality not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        service.setSpeciality(speciality.get());
        Service result = repository.saveAndFlush(service);
        if (repository.findByName(result.getName()).isPresent()){
            return new ResponseEntity<>(new Message(result, BadRequests.REQUESTS_SUCCESS.getText(), TypeResponse.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("Service not updated", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> delete(Long id) throws IllegalArgumentException{
        if (id <= 0) throw new IllegalArgumentException();
        Optional<Service> service = repository.findById(id);
        if (service.isEmpty()){
            return new ResponseEntity<>(new Message(BadRequests.NOT_FOUND.getText(), TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        if (!treatmentRepository.findAllByService(service.get()).isEmpty()){
            return new ResponseEntity<>(new Message("Service is used", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        boolean result = repository.deleteServiceById(id) == 1;
        if (!result){
            return new ResponseEntity<>(new Message("Service not deleted", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message( BadRequests.REQUESTS_SUCCESS.getText(), TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Service saveInitial(Service service){
        return repository.saveAndFlush(service);
    }
}
