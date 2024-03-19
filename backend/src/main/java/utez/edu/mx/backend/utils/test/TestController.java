package utez.edu.mx.backend.utils.test;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.hibernate.mapping.Any;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.backend.security.service.CryptService;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController {

    private final CryptService service;
    @PostMapping("/encrypt")
    public String encrypt(@RequestBody Object object) throws Exception {
        return service.encrypt(object);
    }

    @PostMapping("/decrypt")
    public String decrypt(@RequestBody String string) throws Exception {
        return service.decrypt(string);
    }
}
