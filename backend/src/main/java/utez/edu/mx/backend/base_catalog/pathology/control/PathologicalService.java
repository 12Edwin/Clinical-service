package utez.edu.mx.backend.base_catalog.pathology.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.base_catalog.pathology.model.PathologicalRepository;
import utez.edu.mx.backend.base_catalog.pathology.model.Pathological_record;
import utez.edu.mx.backend.base_catalog.pathology.model.TypePathological;
import utez.edu.mx.backend.base_catalog.pathology.model.TypePathologicalRepository;
import utez.edu.mx.backend.execution.expedient.model.Expedient;
import utez.edu.mx.backend.execution.expedient.model.ExpedientRepository;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PathologicalService {

    private final PathologicalRepository repository;

    private final TypePathologicalRepository typePathologicalRepository;

    private final ExpedientRepository expedientRepository;

    @Transactional(readOnly = true)
    public Optional<TypePathological> findFirstByName(String name){
        return typePathologicalRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findAllPathologies(Pageable pageable) throws UnsupportedEncodingException, JsonProcessingException {
        return new ResponseEntity<> ( new Message(repository.findAll(pageable), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findAllTypes(Pageable pageable) throws UnsupportedEncodingException, JsonProcessingException {
        return new ResponseEntity<> ( new Message(typePathologicalRepository.findAll(pageable), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Object> findPathologyById(Long id) throws UnsupportedEncodingException, JsonProcessingException {
        if (id <= 0) throw new IllegalArgumentException("missing fields");
        Optional<Pathological_record> pathology = repository.findById(id);
        if (pathology.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(pathology.get(), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findTypeById(Long id) throws UnsupportedEncodingException, JsonProcessingException {
        if (id <= 0) throw new IllegalArgumentException("missing fields");
        Optional<TypePathological> pathology = typePathologicalRepository.findById(id);
        if (pathology.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(pathology.get(), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> savePathology(Pathological_record pathology) throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {
        if (pathology.getName() == null || pathology.getDescription() == null || pathology.getExpedient().getId() <= 0 || pathology.getTypePathological().getId() <= 0) throw new IllegalArgumentException();

        Optional<Expedient> expedient = expedientRepository.findById(pathology.getExpedient().getId());
        if (expedient.isEmpty()){
            return new ResponseEntity<>(new Message("Expedient not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        pathology.setExpedient(expedient.get());
        Optional<TypePathological> type = typePathologicalRepository.findById(pathology.getTypePathological().getId());
        if (type.isEmpty()){
            return new ResponseEntity<>(new Message("Type pathology not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        pathology.setTypePathological(type.get());

        Pathological_record result = repository.save(pathology);
        if (repository.findByName(result.getName()).isPresent()){
            return new ResponseEntity<>(new Message(result, "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("Unregistered pathology", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> saveType(TypePathological pathology) throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {
        if (pathology.getName() == null || pathology.getDescription() == null) throw new IllegalArgumentException();

        if (typePathologicalRepository.findByName(pathology.getName()).isPresent()){
            return new ResponseEntity<>(new Message("Type pathology already exists", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        TypePathological result = typePathologicalRepository.save(pathology);
        if (typePathologicalRepository.findByName(result.getName()).isPresent()){
            return new ResponseEntity<>(new Message(result, "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("Unregistered type pathology", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> updatePathology(Pathological_record pathology) throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {
        if (pathology.getName() == null || pathology.getDescription() == null || pathology.getExpedient().getId() <= 0 || pathology.getTypePathological().getId() <= 0) throw new IllegalArgumentException();
        if (!repository.existsById(pathology.getId())){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Optional<Expedient> expedient = expedientRepository.findById(pathology.getExpedient().getId());
        if (expedient.isEmpty()){
            return new ResponseEntity<>(new Message("Expedient not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        pathology.setExpedient(expedient.get());
        Optional<TypePathological> type = typePathologicalRepository.findById(pathology.getTypePathological().getId());
        if (type.isEmpty()){
            return new ResponseEntity<>(new Message("Type pathology not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        pathology.setTypePathological(type.get());
        Pathological_record result = repository.saveAndFlush(pathology);
        if (repository.findByName(result.getName()).isPresent()){
            return new ResponseEntity<>(new Message(result, "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("Pathology not updated", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> updateType(TypePathological pathology) throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {
        if (pathology.getName() == null || pathology.getDescription() == null) throw new IllegalArgumentException();
        if (!typePathologicalRepository.existsById(pathology.getId())){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        TypePathological result = typePathologicalRepository.saveAndFlush(pathology);
        if (typePathologicalRepository.findByName(result.getName()).isPresent()){
            return new ResponseEntity<>(new Message(result, "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("Type pathology not updated", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> deletePathology(Long id) throws IllegalArgumentException{
        if (id <= 0) throw new IllegalArgumentException();
        Optional<Pathological_record> pathology = repository.findById(id);
        if (pathology.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        boolean result = repository.deletePathological_recordById(id) == 1;
        if (!result){
            return new ResponseEntity<>(new Message("Pathology not deleted", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message( "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> deleteType(Long id) throws IllegalArgumentException{
        if (id <= 0) throw new IllegalArgumentException();
        Optional<TypePathological> pathology = typePathologicalRepository.findById(id);
        if (pathology.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        if (!repository.findAllByTypePathological(pathology.get()).isEmpty()){
            return new ResponseEntity<>(new Message("Type pathology is used", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        boolean result = typePathologicalRepository.deleteTypePathologicalById(id) == 1;
        if (!result){
            return new ResponseEntity<>(new Message("Type pathology not deleted", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message( "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public TypePathological saveInitial(TypePathological typePathological){
        return typePathologicalRepository.save(typePathological);
    }
}
