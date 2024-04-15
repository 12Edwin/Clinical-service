package utez.edu.mx.backend.access.captcha.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.backend.security.entity.ApiError;
import utez.edu.mx.backend.security.service.CryptService;

import java.io.UnsupportedEncodingException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/captcha")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.POST})
public class CaptchaController {

    private final CaptchaService service;
    private final CryptService cryptService;
    @PostMapping("/")
    ResponseEntity<Object> verify (@RequestBody String str_solution) throws IllegalArgumentException{
        try {
            String solution = cryptService.decrypt(str_solution);
            return service.verify(solution);
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Malformed request"), HttpStatus.BAD_REQUEST);
        }catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad encoded text"), HttpStatus.BAD_REQUEST);
        }
    }
}
