package utez.edu.mx.backend.base_catalog.space.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private final SpaceRepository repository;
    @Transactional
    public Space saveInitial(Space space){
        return repository.save(space);
    }

    @Transactional(readOnly = true)
    public Optional<Space> findFirstByName(String name){
        return repository.findFirstByName(name);
    }

    @Transactional
    public ResponseEntity<?> findAll(Pageable pageable) throws UnsupportedEncodingException, JsonProcessingException {
        return new ResponseEntity<>(new Message(repository.findAll(pageable), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }
}
