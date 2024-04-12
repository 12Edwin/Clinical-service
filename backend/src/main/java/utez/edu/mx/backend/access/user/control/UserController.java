package utez.edu.mx.backend.access.user.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utez.edu.mx.backend.access.user.model.DtoSession;
import utez.edu.mx.backend.access.user.model.UserDto;
import utez.edu.mx.backend.base_catalog.person.model.DtoPerson;
import utez.edu.mx.backend.security.control.CustomRestExceptionHandler;
import utez.edu.mx.backend.security.entity.ApiError;
import utez.edu.mx.backend.security.jwt.JwtProvider;
import utez.edu.mx.backend.security.service.CryptService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.UnsupportedEncodingException;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/user")
@AllArgsConstructor
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {

    private static final String SESSION = "SESSION";

    @Autowired
    private UserService service;

    private final CryptService cryptService;
    private final ObjectMapper mapper;
    private final JwtProvider provider;
    private final CustomRestExceptionHandler<DtoSession> exceptionHandler;
    private final CustomRestExceptionHandler<UserDto> handlerUserDto;
    private final CustomRestExceptionHandler<DtoPerson> handlerDtoPerson;
    @PostMapping("/recovery/")
    ResponseEntity<?> recovery (@RequestBody String str_session) throws IllegalArgumentException {
        try {
            String decrypt = cryptService.decrypt(str_session);
            DtoSession session = mapper.readValue(decrypt, DtoSession.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoSession>> violations = validator.validate(session, DtoSession.ChangePassword.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.recover(session);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verify/")
    ResponseEntity<?> verify (@RequestBody String str_session) throws IllegalArgumentException {
        try {
            String decrypt = cryptService.decrypt(str_session);
            DtoSession session = mapper.readValue(decrypt, DtoSession.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<DtoSession>> violations = validator.validate(session, DtoSession.VerifyCode.class);
            if (!violations.isEmpty())
                return exceptionHandler.handleViolations(violations);

            return service.verifyCode(session);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/profile/")
    public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String str_token, @RequestBody String str_profile) {
        try {
            String token = str_token.replace("Bearer ", "");
            Long idUser = provider.getUserId(token);
            String decrypt = cryptService.decrypt(str_profile);
            UserDto profile = mapper.readValue(decrypt, UserDto.class);

            // Validations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<UserDto>> violations = validator.validate(profile, UserDto.Profile.class);
            if (!violations.isEmpty())
                return handlerUserDto.handleViolations(violations);

            factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
            Set<ConstraintViolation<DtoPerson>> violationsPerson = validator.validate(profile.getPersonProfile(), DtoPerson.Profile.class);
            if (!violations.isEmpty())
                return handlerDtoPerson.handleViolations(violationsPerson);

            return service.updateProfile(profile, idUser);
        }catch (UnsupportedEncodingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/upload/")
    public ResponseEntity<?> uploadProfilePicture(@RequestHeader("Authorization") String str_token, @RequestParam("profile") MultipartFile file) {
        String token = str_token.replace("Bearer ", "");
        Long idUser = provider.getUserId(token);
        return service.uploadProfilePicture(idUser, file);
    }

    @GetMapping("/image/{str_id}")
    public ResponseEntity<?> getImage(@PathVariable String str_id) {
        try {
            String decrypt = cryptService.decrypt(str_id);
            Long id = Long.parseLong(decrypt);
            return service.getProfilePicture(id);
        } catch (UnsupportedEncodingException e) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        }
    }

}
