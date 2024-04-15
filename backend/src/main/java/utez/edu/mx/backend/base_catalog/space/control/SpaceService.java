package utez.edu.mx.backend.base_catalog.space.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.base_catalog.space.model.Space;
import utez.edu.mx.backend.base_catalog.space.model.SpaceRepository;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class SpaceService {

    private final SpaceRepository repository;
    @Transactional
    public Space saveInitial(Space space){
        return repository.save(space);
    }

    @Transactional(readOnly = true)
    public Optional<Space> findFirstByName(String name){
        return repository.findFirstByName(name);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findAll(Pageable pageable) throws UnsupportedEncodingException, JsonProcessingException {
        return new ResponseEntity<>(new Message(repository.findAll(pageable), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findById(Long id) throws UnsupportedEncodingException, JsonProcessingException {
        if (id <= 0) throw new IllegalArgumentException("missing fields");
        Optional<Space> space = repository.findById(id);
        if (space.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(space.get(), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Object> save(Space space) throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {
        if (space.getName() == null || space.getDescription() == null) throw new IllegalArgumentException("missing fields");
        if (repository.findFirstByName(space.getName()).isPresent()) {
            return new ResponseEntity<>(new Message("Space already exists", TypeResponse.ERROR), HttpStatus.CONFLICT);
        }
        Space newSpace = repository.save(space);
        if (repository.findFirstByName(space.getName()).isEmpty()) {
            return new ResponseEntity<>(new Message("Unregistered space", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message(newSpace, "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Object> update(Space space) throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {
        if (space.getId() <= 0 || space.getName() == null || space.getDescription() == null) throw new IllegalArgumentException("missing fields");
        Optional<Space> oldSpace = repository.findById(space.getId());
        if (oldSpace.isEmpty()) {
            return new ResponseEntity<>(new Message("Space not found", TypeResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        if (repository.findFirstByNameAndIdNot(space.getName(), space.getId()).isPresent()) {
            return new ResponseEntity<>(new Message("Space already exists", TypeResponse.ERROR), HttpStatus.CONFLICT);
        }
        Space newSpace = repository.saveAndFlush(space);
        if (repository.findFirstByName(space.getName()).isEmpty()) {
            return new ResponseEntity<>(new Message("Unregistered space", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message(newSpace, "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Object> delete(Long id) throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {
        if (id <= 0) throw new IllegalArgumentException("missing fields");
        Optional<Space> space = repository.findById(id);
        if (space.isEmpty()) {
            return new ResponseEntity<>(new Message("Space not found", TypeResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        if (repository.findById(id).isPresent()) {
            return new ResponseEntity<>(new Message("Space not deleted", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message("Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }
}
