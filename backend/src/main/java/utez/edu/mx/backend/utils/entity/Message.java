package utez.edu.mx.backend.utils.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import utez.edu.mx.backend.security.service.CryptService;

import java.io.UnsupportedEncodingException;

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
