package utez.edu.mx.backend.base_catalog.speciality.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.access.user.model.UserRepository;
import utez.edu.mx.backend.base_catalog.service.model.ServiceRepository;
import utez.edu.mx.backend.base_catalog.speciality.model.Speciality;
import utez.edu.mx.backend.base_catalog.speciality.model.SpecialityRepository;
import utez.edu.mx.backend.execution.doctor.model.ViewDoctors;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class SpecialityService {

    @Autowired
    private SpecialityRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Transactional(readOnly = true)
    public Optional<Speciality> findFirsByName(String name){
        return repository.findByName(name);
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name){
        return repository.existsByName(name);
    }

    @Transactional(readOnly = true)
    public Optional<Speciality> findById(Long id){
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findSpecialityById(Long id) throws UnsupportedEncodingException, JsonProcessingException {
        if (id <= 0) throw new IllegalArgumentException("missing fields");
        Optional<Speciality> speciality = repository.findById(id);
        if (speciality.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(speciality.get(), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(Pageable pageable) throws UnsupportedEncodingException, JsonProcessingException {
        return new ResponseEntity<> ( new Message(repository.findAll(pageable), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<?> save(Speciality speciality) throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {
        if (speciality.getName() == null || speciality.getDescription() == null) throw new IllegalArgumentException();
        if (existsByName(speciality.getName())){
            return new ResponseEntity<>(new Message("Duplicated speciality", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Speciality result = repository.save(speciality);
        if (repository.existsByName(result.getName())){
            return new ResponseEntity<>(new Message(result, "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("Unregistered speciality", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<?> update(Speciality speciality) throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {
        if (speciality.getName() == null || speciality.getDescription() == null || speciality.getId() <= 0) throw new IllegalArgumentException();
        if (!repository.existsById(speciality.getId())){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Speciality result = repository.saveAndFlush(speciality);
        if (result.getName() == null || result.getName().isEmpty()){
            return new ResponseEntity<>(new Message("Speciality not updated", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message(result, "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<?> delete(Long id) throws IllegalArgumentException{
        if (id <= 0) throw new IllegalArgumentException();
        Optional<Speciality> speciality = findById(id);
        if (speciality.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        if (!userRepository.findAllBySpeciality(speciality.get()).isEmpty()){
            return new ResponseEntity<>(new Message("Speciality is used", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (!serviceRepository.findAllBySpeciality(speciality.get()).isEmpty()){
            return new ResponseEntity<>(new Message("Speciality is used", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        boolean result = repository.deleteSpecialityById(id) == 1;
        if (!result){
            return new ResponseEntity<>(new Message("Speciality not deleted", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message( "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Speciality saveInitial(Speciality speciality){
        return repository.save(speciality);
    }
}
