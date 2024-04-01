package utez.edu.mx.backend.execution.appoint.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.backend.execution.appoint.model.DtoAppoint;
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
@RequestMapping("/api/appoint")
@AllArgsConstructor
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AppointController {

    private static final String APPOINT = "APPOINT";

    @Autowired
    private AppointService service;

    private final CryptService cryptService;
    private final ObjectMapper mapper;
    private final CustomRestExceptionHandler<DtoAppoint> exceptionHandler;

    @PostMapping("/findByDate/")
    ResponseEntity<?> findByDate (@RequestBody String str_appoint) throws IllegalArgumentException {
        try {
            String decrypt = cryptService.decrypt(str_appoint);
            DtoAppoint appoint = mapper.readValue(decrypt, DtoAppoint.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoAppoint>> violations = validator.validate(appoint, DtoAppoint.FindByDate.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.findByDate(appoint.cast());
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    ResponseEntity<?> save (@RequestBody String str_appoint) throws IllegalArgumentException {
        try {
            String decrypt = cryptService.decrypt(str_appoint);
            DtoAppoint appoint = mapper.readValue(decrypt, DtoAppoint.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoAppoint>> violations = validator.validate(appoint, DtoAppoint.Register.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.save(appoint.cast());
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    ResponseEntity<?> update (@RequestBody String str_appoint) throws IllegalArgumentException {
        try {
            String decrypt = cryptService.decrypt(str_appoint);
            DtoAppoint appoint = mapper.readValue(decrypt, DtoAppoint.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoAppoint>> violations = validator.validate(appoint, DtoAppoint.Modify.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.update(appoint.cast());
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reschedule/")
    ResponseEntity<?> reschedule (@RequestBody String str_appoint) throws IllegalArgumentException {
        try {
            String decrypt = cryptService.decrypt(str_appoint);
            DtoAppoint appoint = mapper.readValue(decrypt, DtoAppoint.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoAppoint>> violations = validator.validate(appoint, DtoAppoint.Reschedule.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.reschedule(appoint.cast());
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/complete/")
    ResponseEntity<?> complete (@RequestBody String str_appoint) throws IllegalArgumentException{
        try {
            String decrypt = cryptService.decrypt(str_appoint);
            DtoAppoint appoint = mapper.readValue(decrypt, DtoAppoint.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoAppoint>> violations = validator.validate(appoint, DtoAppoint.Complete.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.complete(appoint.cast());
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cancel/")
    ResponseEntity<?> cancel (@RequestBody String str_appoint) throws IllegalArgumentException{
        try {
            String decrypt = cryptService.decrypt(str_appoint);
            DtoAppoint appoint = mapper.readValue(decrypt, DtoAppoint.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoAppoint>> violations = validator.validate(appoint, DtoAppoint.Cancel.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.cancel(appoint.cast());
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/space/{str_id}")
    ResponseEntity<?> findBySpace (@PathVariable(name = "str_id") String str_id){
        try {
            String id = cryptService.decrypt(str_id);
            return service.findBySpace(Long.valueOf(id));
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        }
    }
}
