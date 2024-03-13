package utez.edu.mx.backend.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.backend.access.role.model.Role;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtDto {

    private String token;
    private String bearer = "Bearer";
    private String email;
    private String name;
    private Long identKey;
    private List<Role> roles;

    public JwtDto(String token, String email, List<Role> roles) {
        this.token = token;
        this.email = email;
        this.roles = roles;
    }
}
