package utez.edu.mx.backend.access.user.control;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin("*")
public class UserController {

    @GetMapping(value = "/")
    String test(){
        return "Entra";
    }
}
