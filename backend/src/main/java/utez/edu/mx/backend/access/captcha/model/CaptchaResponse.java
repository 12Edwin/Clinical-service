package utez.edu.mx.backend.access.captcha.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaResponse {
    private boolean success;
    private List<String> errors;

    public interface verify{}
}
