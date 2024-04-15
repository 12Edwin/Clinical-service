package utez.edu.mx.backend.execution.appoint.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.backend.execution.appoint.model.DtoAppoint;
import utez.edu.mx.backend.security.control.CustomRestExceptionHandler;
import utez.edu.mx.backend.security.entity.ApiError;
import utez.edu.mx.backend.security.jwt.JwtProvider;
import utez.edu.mx.backend.security.service.CryptService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Set;

@RestController
@RequestMapping("/api/appoint")
@AllArgsConstructor
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AppointController {

    private final AppointService service;

    private final CryptService cryptService;
    private final ObjectMapper mapper;
    private final JwtProvider provider;
    private final CustomRestExceptionHandler<DtoAppoint> exceptionHandler;

    @PreAuthorize("hasAnyAuthority('APPOINTS')")
    @PostMapping("/findByDate/")
    ResponseEntity<Object> findByDate (@RequestBody String str_appoint) throws IllegalArgumentException {
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
        }catch (ParseException ex){
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Invalid format dates"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('APPOINTS')")
    @GetMapping("/{str_id}")
    ResponseEntity<Object> findById (@RequestHeader("Authorization") String str_token, @PathVariable(name = "str_id") String str_id){
        try {
            String token = str_token.replace("Bearer ", "");
            Long idUser = provider.getUserId(token);
            String id = cryptService.decrypt(str_id);
            return service.findById(Long.valueOf(id), idUser);
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('APPOINTS')")
    @PostMapping("/")
    ResponseEntity<Object> save (@RequestHeader("Authorization") String str_token, @RequestBody String str_appoint) throws IllegalArgumentException {
        try {
            String token = str_token.replace("Bearer ", "");
            Long idUser = provider.getUserId(token);
            String decrypt = cryptService.decrypt(str_appoint);
            DtoAppoint appoint = mapper.readValue(decrypt, DtoAppoint.class);

            System.out.println("Hora inicio =>" + appoint.getStart_hour());
            System.out.println("Hora fin =>" + appoint.getEnd_hour());
            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoAppoint>> violations = validator.validate(appoint, DtoAppoint.Register.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.save(appoint.cast(), idUser);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }catch (ParseException ex){
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Invalid format dates"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('APPOINTS')")
    @PutMapping("/")
    ResponseEntity<Object> update (@RequestHeader("Authorization") String str_token, @RequestBody String str_appoint) throws IllegalArgumentException {
        try {
            String token = str_token.replace("Bearer ", "");
            Long idUser = provider.getUserId(token);
            String decrypt = cryptService.decrypt(str_appoint);
            DtoAppoint appoint = mapper.readValue(decrypt, DtoAppoint.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoAppoint>> violations = validator.validate(appoint, DtoAppoint.Modify.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.update(appoint.cast(), idUser);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }catch (ParseException ex){
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Invalid format dates"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('APPOINTS')")
    @PostMapping("/reschedule/")
    ResponseEntity<Object> reschedule (@RequestHeader("Authorization") String str_token, @RequestBody String str_appoint) throws IllegalArgumentException {
        try {
            String token = str_token.replace("Bearer ", "");
            Long idUser = provider.getUserId(token);
            String decrypt = cryptService.decrypt(str_appoint);
            DtoAppoint appoint = mapper.readValue(decrypt, DtoAppoint.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoAppoint>> violations = validator.validate(appoint, DtoAppoint.Reschedule.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.reschedule(appoint.cast(), idUser);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }catch (ParseException ex){
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Invalid format dates"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('APPOINTS')")
    @PostMapping("/complete/")
    ResponseEntity<Object> complete (@RequestHeader("Authorization") String str_token, @RequestBody String str_appoint) throws IllegalArgumentException{
        try {
            String token = str_token.replace("Bearer ", "");
            Long idUser = provider.getUserId(token);
            String decrypt = cryptService.decrypt(str_appoint);
            DtoAppoint appoint = mapper.readValue(decrypt, DtoAppoint.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoAppoint>> violations = validator.validate(appoint, DtoAppoint.Complete.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.complete(appoint.cast(), idUser);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }catch (ParseException ex){
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Invalid format dates"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('APPOINTS')")
    @PostMapping("/cancel/")
    ResponseEntity<Object> cancel (@RequestHeader("Authorization") String str_token, @RequestBody String str_appoint) throws IllegalArgumentException{
        try {
            String token = str_token.replace("Bearer ", "");
            Long idUser = provider.getUserId(token);
            String decrypt = cryptService.decrypt(str_appoint);
            DtoAppoint appoint = mapper.readValue(decrypt, DtoAppoint.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoAppoint>> violations = validator.validate(appoint, DtoAppoint.Cancel.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.cancel(appoint.cast(), idUser);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }catch (ParseException ex){
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Invalid format dates"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('APPOINTS')")
    @GetMapping("/space/{str_id}")
    ResponseEntity<Object> findBySpace (@RequestHeader("Authorization") String str_token, @PathVariable(name = "str_id") String str_id){
        try {
            String token = str_token.replace("Bearer ", "");
            Long idUser = provider.getUserId(token);
            String id = cryptService.decrypt(str_id);
            return service.findBySpace(Long.valueOf(id), idUser);
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        }
    }
}
