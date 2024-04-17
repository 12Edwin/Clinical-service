package utez.edu.mx.backend.execution.doctor.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.backend.execution.doctor.model.ViewDoctors;
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
@RequestMapping("/api/doctor")
@AllArgsConstructor
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class DoctorController {

    private final DoctorService service;
    private final CryptService cryptService;
    private final ObjectMapper objectMapper;
    private final CustomRestExceptionHandler<ViewDoctors> exceptionHandler;


    @GetMapping("/")
    ResponseEntity<Object> findAllDoctors (Pageable pageable) {
        try {
            return service.findAllDoctors(pageable);
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        } catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('DOCTORS')")
    @GetMapping("/all/")
    ResponseEntity<Object> findAllDoctorsForAdmin (Pageable pageable) {
        try {
            return service.findAllDoctors(pageable);
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        } catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('DOCTORS')")
    @GetMapping("/{str_id}")
    ResponseEntity<Object> findById (@PathVariable(name = "str_id") String str_id) throws IllegalArgumentException{
        try {
            String id = cryptService.decrypt(str_id);
            return service.findDoctor(Long.valueOf(id));
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('DOCTORS')")
    @PostMapping("/")
    ResponseEntity<Object> saveDoctor (@RequestBody String str_doctor) throws IllegalArgumentException {
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
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('DOCTORS')")
    @PutMapping("/")
    ResponseEntity<Object> updateDoctor (@RequestBody String str_doctor) throws IllegalArgumentException {
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
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('DOCTORS')")
    @DeleteMapping("/{str_id}")
    ResponseEntity<Object> lockDoctor (@PathVariable String str_id) throws IllegalArgumentException {
        try {
            String id = cryptService.decrypt(str_id);
            return service.lockDoctor(Long.valueOf(id));
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        }
    }

}
