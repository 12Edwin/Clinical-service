package utez.edu.mx.backend.base_catalog.space.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.backend.access.user.model.DtoSession;
import utez.edu.mx.backend.base_catalog.space.model.DtoSpace;
import utez.edu.mx.backend.base_catalog.space.model.Space;
import utez.edu.mx.backend.execution.treatment.model.DtoTreatment;
import utez.edu.mx.backend.security.control.CustomRestExceptionHandler;
import utez.edu.mx.backend.security.entity.ApiError;
import utez.edu.mx.backend.security.service.CryptService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.UnsupportedEncodingException;
import java.util.Set;

@RestController
@RequestMapping("/api/space")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET})
@AllArgsConstructor
public class SpaceController {

    private static final String SPACE = "SPACE";

    @Autowired
    private SpaceService service;
    @Autowired
    private final CryptService cryptService;
    private final ObjectMapper mapper;
    private final CustomRestExceptionHandler<DtoSpace> exceptionHandler;

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

    @GetMapping("/{str_id}")
    ResponseEntity<?> findById (@PathVariable(name = "str_id") String str_id) throws IllegalArgumentException{
        try {
            String id = cryptService.decrypt(str_id);
            return service.findById(Long.valueOf(id));
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    ResponseEntity<?> save (@RequestBody String str_space) throws IllegalArgumentException {
        try {
            String decrypt = cryptService.decrypt(str_space);
            DtoSpace space = mapper.readValue(decrypt, DtoSpace.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoSpace>> violations = validator.validate(space, DtoSpace.Register.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.save(space.cast());
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    ResponseEntity<?> update (@RequestBody String str_space) throws IllegalArgumentException {
        try {
            String decrypt = cryptService.decrypt(str_space);
            DtoSpace space = mapper.readValue(decrypt, DtoSpace.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoSpace>> violations = validator.validate(space, DtoSpace.Modify.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.update(space.cast());
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{str_id}")
    ResponseEntity<?> delete (@PathVariable(name = "str_id") String str_id) throws IllegalArgumentException {
        try {
            String id = cryptService.decrypt(str_id);
            return service.delete(Long.valueOf(id));
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        }
    }
}
