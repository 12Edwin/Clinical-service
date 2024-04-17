package utez.edu.mx.backend.execution.expedient.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.backend.base_catalog.disease.model.DtoDisease;
import utez.edu.mx.backend.base_catalog.pathology.model.DtoPathological_record;
import utez.edu.mx.backend.execution.expedient.model.DtoExpedient;
import utez.edu.mx.backend.security.control.CustomRestExceptionHandler;
import utez.edu.mx.backend.security.entity.ApiError;
import utez.edu.mx.backend.security.jwt.JwtProvider;
import utez.edu.mx.backend.security.service.CryptService;
import utez.edu.mx.backend.utils.entity.BadRequests;

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

    private final ExpedientService service;
    private final CryptService cryptService;
    private final ObjectMapper mapper;
    private final JwtProvider provider;
    private final CustomRestExceptionHandler<DtoExpedient> exceptionHandler;
    private final CustomRestExceptionHandler<DtoPathological_record> exceptionHandler_pathology;
    private final CustomRestExceptionHandler<DtoDisease> exceptionHandler_disease;

    @PreAuthorize("hasAnyAuthority('EXPEDIENTS')")
    @GetMapping("/")
    ResponseEntity<Object> findAll (@RequestHeader("Authorization") String str_token, Pageable pageable) {
        try {
            String token = getToken(str_token);
            Long idUser = provider.getUserId(token);
            return service.findAll(pageable, idUser);
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.MALFORMED_REQUEST.getText()), HttpStatus.BAD_REQUEST);
        } catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.BAD_ENCODED.getText()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('EXPEDIENTS')")
    @GetMapping("/{str_id}")
    ResponseEntity<Object> findById (@RequestHeader("Authorization") String str_token, @PathVariable(name = "str_id") String str_id) throws IllegalArgumentException{
        try {
            String token = getToken(str_token);
            Long idUser = provider.getUserId(token);
            String id = cryptService.decrypt(str_id);
            return service.findById(Long.valueOf(id), idUser);
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.MALFORMED_REQUEST.getText()), HttpStatus.BAD_REQUEST);
        }catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.BAD_ENCODED.getText()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('EXPEDIENTS')")
    @PostMapping("/findByFolio/")
    ResponseEntity<Object> findByFolio (@RequestHeader("Authorization") String str_token, Pageable pageable, @RequestBody String str_expedient) throws IllegalArgumentException {
        try {
            String token = getToken(str_token);
            Long idUser = provider.getUserId(token);
            String decrypt = cryptService.decrypt(str_expedient);
            DtoExpedient expedient = mapper.readValue(decrypt, DtoExpedient.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoExpedient>> violations = validator.validate(expedient, DtoExpedient.FindByFolio.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.findAllByFolio(pageable, expedient, idUser);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.BAD_ENCODED.getText()), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.MALFORMED_REQUEST.getText()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('EXPEDIENTS')")
    @PostMapping("/findByEmail/")
    ResponseEntity<Object> findByEmail (@RequestHeader("Authorization") String str_token, Pageable pageable, @RequestBody String str_expedient) throws IllegalArgumentException {
        try {
            String token = getToken(str_token);
            Long idUser = provider.getUserId(token);
            String decrypt = cryptService.decrypt(str_expedient);
            DtoExpedient expedient = mapper.readValue(decrypt, DtoExpedient.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoExpedient>> violations = validator.validate(expedient, DtoExpedient.FindByEmail.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.findAllByEmail(pageable, expedient, idUser);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.BAD_ENCODED.getText()), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.MALFORMED_REQUEST.getText()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('EXPEDIENTS')")
    @PostMapping("/")
    ResponseEntity<Object> save (@RequestHeader("Authorization") String str_token, @RequestBody String str_expedient) throws IllegalArgumentException {
        try {
            String token = getToken(str_token);
            Long idUser = provider.getUserId(token);
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

            return service.save(expedient, idUser);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.BAD_ENCODED.getText()), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.MALFORMED_REQUEST.getText()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('EXPEDIENTS')")
    @PutMapping("/")
    ResponseEntity<Object> update (@RequestHeader("Authorization") String str_token, @RequestBody String str_expedient) throws IllegalArgumentException {
        try {
            String token = getToken(str_token);
            Long idUser = provider.getUserId(token);
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

            return service.update(expedient, idUser);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.BAD_ENCODED.getText()), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.MALFORMED_REQUEST.getText()), HttpStatus.BAD_REQUEST);
        }
    }

    private static String getToken(String str_token) {
        return str_token.replace("Bearer ", "");
    }
}
