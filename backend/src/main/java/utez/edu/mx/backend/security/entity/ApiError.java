package utez.edu.mx.backend.security.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private HttpStatus status;
    private String message;
    private Map<String,String> errors;

    public ApiError(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public ApiError(HttpStatus status, String message, String errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = new HashMap<>();
        this.errors.put("error", errors);
    }
}
