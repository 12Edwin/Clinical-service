package utez.edu.mx.backend.security.control;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.backend.access.user.control.UserService;
import utez.edu.mx.backend.access.user.model.UserDto;
import utez.edu.mx.backend.security.jwt.LoginDto;
import utez.edu.mx.backend.security.service.AccessService;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class AccessControl {

    private final AccessService accessService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) throws Exception {
        return accessService.login(loginDto);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@Validated({UserDto.VerifyCode.class}) @RequestBody UserDto dto){
        return userService.verifyCode(dto);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Validated({UserDto.ChangePassword.class}) @RequestBody UserDto dto){
        return userService.updatePassword(dto);
    }

}
