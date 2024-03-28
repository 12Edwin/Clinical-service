package utez.edu.mx.backend.execution.expedient.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.backend.base_catalog.disease.model.DtoDisease;
import utez.edu.mx.backend.base_catalog.pathology.control.PathologicalService;
import utez.edu.mx.backend.base_catalog.pathology.model.DtoPathological_record;
import utez.edu.mx.backend.base_catalog.pathology.model.DtoTypePathological;
import utez.edu.mx.backend.execution.expedient.model.DtoExpedient;
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
@RequestMapping("/api/expedient")
@AllArgsConstructor
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class ExpedientController {

    private static final String EXPEDIENT = "EXPEDIENT";

    @Autowired
    private ExpedientService service;

    private final CryptService cryptService;
    private final ObjectMapper mapper;
    private final CustomRestExceptionHandler<DtoExpedient> exceptionHandler;
    private final CustomRestExceptionHandler<DtoPathological_record> exceptionHandler_pathology;
    private final CustomRestExceptionHandler<DtoDisease> exceptionHandler_disease;

    @GetMapping("/")
    ResponseEntity<?> findAllDoctors (Pageable pageable) {
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
    ResponseEntity<?> save (@RequestBody String str_expedient) throws IllegalArgumentException {
        try {
            String decrypt = cryptService.decrypt(str_expedient);
            DtoExpedient expedient = mapper.readValue(decrypt, DtoExpedient.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoExpedient>> violations = validator.validate(expedient, DtoExpedient.Register.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            for (DtoPathological_record pathology : expedient.getPathologicalRecords()){
                Set<ConstraintViolation<DtoPathological_record>> violationsP = validator.validate(pathology, DtoPathological_record.Register.class);
                if (!violationsP.isEmpty())
                    return exceptionHandler_pathology.handleViolations(violationsP);
            }

            for (DtoDisease disease : expedient.getDiseases()){
                Set<ConstraintViolation<DtoDisease>> violationsD = validator.validate(disease, DtoDisease.Register.class);
                if (!violationsD.isEmpty())
                    return exceptionHandler_disease.handleViolations(violationsD);
            }

            return service.save(expedient);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    ResponseEntity<?> update (@RequestBody String str_expedient) throws IllegalArgumentException {
        try {
            String decrypt = cryptService.decrypt(str_expedient);
            DtoExpedient expedient = mapper.readValue(decrypt, DtoExpedient.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoExpedient>> violations = validator.validate(expedient, DtoExpedient.Modify.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            for (DtoPathological_record pathology : expedient.getPathologicalRecords()){
                Set<ConstraintViolation<DtoPathological_record>> violationsP = validator.validate(pathology, DtoPathological_record.Modify.class);
                if (!violationsP.isEmpty())
                    return exceptionHandler_pathology.handleViolations(violationsP);
            }

            for (DtoDisease disease : expedient.getDiseases()){
                Set<ConstraintViolation<DtoDisease>> violationsD = validator.validate(disease, DtoDisease.Modify.class);
                if (!violationsD.isEmpty())
                    return exceptionHandler_disease.handleViolations(violationsD);
            }

            return service.update(expedient);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }
}
