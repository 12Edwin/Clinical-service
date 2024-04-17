package utez.edu.mx.backend.base_catalog.service.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.backend.base_catalog.service.model.DtoService;
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
@RequestMapping("/api/service")
@AllArgsConstructor
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class ServiceController {

    private final ServiceService serv;

    private final CryptService cryptService;
    private final JwtProvider provider;
    private final ObjectMapper mapper;
    private final CustomRestExceptionHandler<DtoService> exceptionHandler;

    @PreAuthorize("hasAnyAuthority('SERVICES')")
    @GetMapping("/all/")
    ResponseEntity<Object> findAllForAdmin (Pageable pageable) {
        try {
            return serv.findAll(pageable);
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.MALFORMED_REQUEST.getText()), HttpStatus.BAD_REQUEST);
        } catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.BAD_ENCODED.getText()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    ResponseEntity<Object> findAll (Pageable pageable) {
        try {
            return serv.findAll(pageable);
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.MALFORMED_REQUEST.getText()), HttpStatus.BAD_REQUEST);
        } catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.BAD_ENCODED.getText()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByUser/")
    ResponseEntity<Object> findServices (@RequestHeader("Authorization") String str_token) {
        try {
            String token = str_token.replace("Bearer ", "");
            Long idUser = provider.getUserId(token);
            return serv.findServices(idUser);
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.MALFORMED_REQUEST.getText()), HttpStatus.BAD_REQUEST);
        } catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.BAD_ENCODED.getText()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{str_id}")
    ResponseEntity<Object> findById (@PathVariable(name = "str_id") String str_id) throws IllegalArgumentException{
        try {
            String id = cryptService.decrypt(str_id);
            return serv.findById(Long.valueOf(id));
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.MALFORMED_REQUEST.getText()), HttpStatus.BAD_REQUEST);
        }catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.BAD_ENCODED.getText()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('SERVICES')")
    @PostMapping("/")
    ResponseEntity<Object> save (@RequestBody String str_service) throws IllegalArgumentException {
        try {
            String decrypt = cryptService.decrypt(str_service);
            DtoService service = mapper.readValue(decrypt, DtoService.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoService>> violations = validator.validate(service, DtoService.Register.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return serv.save(service.cast());
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.BAD_ENCODED.getText()), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.MALFORMED_REQUEST.getText()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('SERVICES')")
    @PutMapping("/")
    ResponseEntity<Object> update (@RequestBody String str_service) throws IllegalArgumentException {
        try {
            String decrypt = cryptService.decrypt(str_service);
            DtoService service = mapper.readValue(decrypt, DtoService.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoService>> violations = validator.validate(service, DtoService.Modify.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return serv.update(service.cast());
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.BAD_ENCODED.getText()), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.MALFORMED_REQUEST.getText()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('SERVICES')")
    @DeleteMapping("/{str_id}")
    ResponseEntity<Object> delete (@PathVariable String str_id) throws IllegalArgumentException {
        try {
            String id = cryptService.decrypt(str_id);
            return serv.delete(Long.valueOf(id));
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.BAD_ENCODED.getText()), HttpStatus.BAD_REQUEST);
        }
    }
}
