package utez.edu.mx.backend.access.sms.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.backend.security.entity.ApiError;
import utez.edu.mx.backend.security.service.CryptService;
import utez.edu.mx.backend.utils.entity.BadRequests;

import java.io.UnsupportedEncodingException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/sms")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.POST})
public class SmsController {

    private final SmsService service;
    private static final String SMS = "SMS";
    private final CryptService cryptService;

    @PostMapping("/")
    ResponseEntity<Object> sendSMS (@RequestBody String str_phone) throws IllegalArgumentException{
        try {
            String phone = cryptService.decrypt(str_phone);
            return service.sendSMS(phone, "CODE");
        }catch (JsonProcessingException ex) {
            return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.MALFORMED_REQUEST.getText()), HttpStatus.BAD_REQUEST);
        }catch (UnsupportedEncodingException ex){
            return new  ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, BadRequests.BAD_ENCODED.getText()), HttpStatus.BAD_REQUEST);
        }
    }

}
