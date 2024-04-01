package utez.edu.mx.backend.access.sms.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.io.UnsupportedEncodingException;

@Service
public class SmsService {

    @Value("${TWILIO_ACCOUNT_SID}")
    private String ACCOUNT_SID;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String AUTH_TOKEN;

    @Value("${TWILIO_PHONE}")
    private String PHONE;

    public ResponseEntity<?> sendSMS(String toPhone, String code) throws UnsupportedEncodingException, JsonProcessingException {
        try {
            String pattern = "^/d(?:-/d{3}){3}/d$";
            if (!toPhone.matches(pattern)){
                return new ResponseEntity<>(new utez.edu.mx.backend.utils.entity.Message("Invalid phone number", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
            }
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(new com.twilio.type.PhoneNumber("+52" + toPhone),
                            new com.twilio.type.PhoneNumber(PHONE),

                            "CLINICAL-SERVICE\nYour recovery code is: "+ code)
                    .create();

            return new ResponseEntity<>( new utez.edu.mx.backend.utils.entity.Message(message.getStatus(), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK) ;
        }catch (Exception e){
            if (e.getMessage().contains("unverified")){
                return new ResponseEntity<>(new utez.edu.mx.backend.utils.entity.Message("Phone not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(new utez.edu.mx.backend.utils.entity.Message("Cannot send sms", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
