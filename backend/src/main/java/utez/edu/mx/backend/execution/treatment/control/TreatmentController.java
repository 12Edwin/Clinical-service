package utez.edu.mx.backend.execution.treatment.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.backend.execution.treatment.model.DtoTreatment;
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
@RequestMapping("/api/treatment")
@AllArgsConstructor
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class TreatmentController {

    private final TreatmentService service;
    private final CryptService cryptService;
    private final ObjectMapper mapper;
    private final JwtProvider provider;
    private final CustomRestExceptionHandler<DtoTreatment> exceptionHandler;

    @PreAuthorize("hasAnyAuthority('TREATMENTS')")
    @GetMapping("/")
    ResponseEntity<Object> findAll ( @RequestHeader("Authorization") String str_token, Pageable pageable) {
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

    @PreAuthorize("hasAnyAuthority('TREATMENTS')")
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

    @PreAuthorize("hasAnyAuthority('TREATMENTS')")
    @GetMapping("/findByExp/{str_id}")
    ResponseEntity<Object> findByExpedient (@RequestHeader("Authorization") String str_token, @PathVariable(name = "str_id") String str_id) throws IllegalArgumentException{
        try {
            String token = getToken(str_token);
            Long idUser = provider.getUserId(token);
            String id = cryptService.decrypt(str_id);
            return service.findByExpedient(Long.valueOf(id), idUser);
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.MALFORMED_REQUEST.getText()), HttpStatus.BAD_REQUEST);
        }catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.BAD_ENCODED.getText()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('TREATMENTS')")
    @PostMapping("/")
    ResponseEntity<Object> save (@RequestHeader("Authorization") String str_token, @RequestBody String str_treatment) throws IllegalArgumentException {
        try {
            String token = getToken(str_token);
            Long idUser = provider.getUserId(token);
            String decrypt = cryptService.decrypt(str_treatment);
            DtoTreatment treatment = mapper.readValue(decrypt, DtoTreatment.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoTreatment>> violations = validator.validate(treatment, DtoTreatment.Register.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.save(treatment.cast(), idUser);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.BAD_ENCODED.getText()), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.MALFORMED_REQUEST.getText()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('TREATMENTS')")
    @PutMapping("/")
    ResponseEntity<Object> update (@RequestHeader("Authorization") String str_token, @RequestBody String str_treatment) throws IllegalArgumentException {
        try {
            String token = getToken(str_token);
            Long idUser = provider.getUserId(token);
            String decrypt = cryptService.decrypt(str_treatment);
            DtoTreatment treatment = mapper.readValue(decrypt, DtoTreatment.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoTreatment>> violations = validator.validate(treatment, DtoTreatment.Modify.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.update(treatment.cast(), idUser);
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
