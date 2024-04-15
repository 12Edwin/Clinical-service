package utez.edu.mx.backend.execution.ratting.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.execution.ratting.model.Ratting;
import utez.edu.mx.backend.execution.ratting.model.RattingRepository;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.io.UnsupportedEncodingException;

@Service
@Transactional
@AllArgsConstructor
public class RattingService {

    private final RattingRepository rattingRepository;

    @Transactional
    public ResponseEntity<Object> findAll(Pageable pageable) throws UnsupportedEncodingException, JsonProcessingException {
        return ResponseEntity.ok( new Message(rattingRepository.findAll(pageable), "Request success", TypeResponse.SUCCESS));
    }

    @Transactional
    public ResponseEntity<Object> save(Ratting ratting) throws UnsupportedEncodingException, JsonProcessingException {
        return ResponseEntity.ok(new Message(rattingRepository.save(ratting), "Request success", TypeResponse.SUCCESS));
    }

    @Transactional
    public ResponseEntity<Object> update(Ratting ratting) throws UnsupportedEncodingException, JsonProcessingException {
        return ResponseEntity.ok(new Message(rattingRepository.save(ratting), "Request success", TypeResponse.SUCCESS));
    }

}
