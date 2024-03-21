package utez.edu.mx.backend.execution.doctor.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.backend.execution.doctor.model.ViewDoctors;
import utez.edu.mx.backend.security.control.CustomRestExceptionHandler;
import utez.edu.mx.backend.security.entity.ApiError;
import utez.edu.mx.backend.security.service.CryptService;

import javax.validation.*;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;


@RestController
@RequestMapping("/api/doctor")
@AllArgsConstructor
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class DoctorController {

    private static final String DOCTOR = "DOCTOR";

    @Autowired
    private final DoctorService service;

    private final CryptService cryptService;
    private final ObjectMapper objectMapper;
    private final CustomRestExceptionHandler<ViewDoctors> exceptionHandler;

    @GetMapping("/")
    ResponseEntity<?> findAllDoctors (Pageable pageable) throws JsonProcessingException {
        try {
            return service.findAllDoctors(pageable);
        }catch (UnsupportedEncodingException | JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Bad encoded text"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchAlgorithmException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Cipher is corrupted"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{str_id}")
    ResponseEntity<?> findById (@PathVariable(name = "str_id") String str_id) throws IllegalArgumentException{
        try {
            String id = cryptService.decrypt(str_id);
            return service.findDoctor(Long.valueOf(id));
        }catch (UnsupportedEncodingException | JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Bad encoded text"), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (NoSuchAlgorithmException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Cipher is corrupted"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    ResponseEntity<?> saveDoctor (@RequestBody String str_doctor) throws IllegalArgumentException {
        try {
            String decrypt = cryptService.decrypt(str_doctor);
            ViewDoctors doctor = objectMapper.readValue(decrypt, ViewDoctors.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<ViewDoctors>> violations = validator.validate(doctor, ViewDoctors.Register.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.saveDoctor(doctor);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        }catch (NoSuchAlgorithmException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Cipher is corrupted"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    ResponseEntity<?> updateDoctor (@RequestBody String str_doctor) throws IllegalArgumentException {
        try {
            String decrypt = cryptService.decrypt(str_doctor);
            ViewDoctors doctor = objectMapper.readValue(decrypt, ViewDoctors.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<ViewDoctors>> violations = validator.validate(doctor, ViewDoctors.Modify.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.updateDoctor(doctor);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        }catch (NoSuchAlgorithmException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Cipher is corrupted"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{str_id}")
    ResponseEntity<?> lockDoctor (@PathVariable String str_id) throws IllegalArgumentException {
        try {
            String id = cryptService.decrypt(str_id);
            return service.lockDoctor(Long.valueOf(id));
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        }catch (NoSuchAlgorithmException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Cipher is corrupted"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
