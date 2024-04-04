package utez.edu.mx.backend.utils.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import utez.edu.mx.backend.security.control.MainSecurity;
import utez.edu.mx.backend.security.service.CryptService;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Getter
@Setter
@AllArgsConstructor
public class Message {

    private String text;
    private TypeResponse type;
    private Object result;
    private static final CryptService cryptService = new CryptService();

    public Message(String text, TypeResponse type) {
        this.text = text;
        this.type = type;
    }

    public Message(Object result, String text, TypeResponse type ) throws JsonProcessingException, UnsupportedEncodingException {
        this.text = text;
        this.type = type;
        ObjectMapper mapper = new ObjectMapper();
        String str_result = mapper.writeValueAsString(result);
        this.result = cryptService.encrypt( str_result );
    }
}
