package utez.edu.mx.backend.base_catalog.space.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.backend.base_catalog.space.model.Space;
import utez.edu.mx.backend.security.control.CustomRestExceptionHandler;
import utez.edu.mx.backend.security.entity.ApiError;
import utez.edu.mx.backend.security.service.CryptService;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/space")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET})
@AllArgsConstructor
public class SpaceController {

    private static final String SPACE = "SPACE";

    @Autowired
    private SpaceService service;

    private final CustomRestExceptionHandler<Space> exceptionHandler;

    @GetMapping("/")
    ResponseEntity<?> findAll (Pageable pageable) {
        try {
            return service.findAll(pageable);
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        } catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        }
    }
}
