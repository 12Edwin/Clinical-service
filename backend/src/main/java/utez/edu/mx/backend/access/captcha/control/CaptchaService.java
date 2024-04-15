package utez.edu.mx.backend.access.captcha.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utez.edu.mx.backend.access.captcha.model.CaptchaResponse;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CaptchaService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${FRIEDLYCAPTCHA.CAPTCHAKEY}")
    private String captchaKey;

    @Value("${FRIEDLYCAPTCHA.SITEKEY}")
    private String siteKey;

    public ResponseEntity<Object> verify(String solution) throws UnsupportedEncodingException, JsonProcessingException {
        String url = "https://api.friendlycaptcha.com/api/v1/siteverify";

        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(
                MediaType.APPLICATION_JSON);
        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("solution", solution);
        requestBody.put("sitekey", siteKey);
        requestBody.put("secret", captchaKey);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<CaptchaResponse> responseEntity = restTemplate.postForEntity(url, requestEntity, CaptchaResponse.class);
        return new ResponseEntity<>(new Message(responseEntity.getBody(), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }
}
