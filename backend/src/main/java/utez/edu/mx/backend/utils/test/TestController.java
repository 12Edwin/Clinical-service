package utez.edu.mx.backend.utils.test;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.hibernate.mapping.Any;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.backend.security.service.CryptService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController {

    private final CryptService service;
    private final ObjectMapper objectMapper;
    @PostMapping("/encrypt")
    public String encrypt(@RequestBody Object object) throws Exception {
        return service.encrypt(objectMapper.writeValueAsString(object));
    }

    @PostMapping("/decrypt")
    public String decrypt(@RequestBody String string) throws Exception {
        return service.decrypt(string);
    }

    @PostMapping("/encode")
    public String encode(@RequestBody String string) throws Exception{
        byte[] encryptedBytes = string.getBytes(StandardCharsets.UTF_8);
        return Base64.getUrlEncoder().encodeToString(encryptedBytes);
    }

    @PostMapping("/decode")
    public String decode(@RequestBody String string) throws Exception{
        return new String(Base64.getUrlDecoder().decode(string), StandardCharsets.UTF_8);
    }
}
