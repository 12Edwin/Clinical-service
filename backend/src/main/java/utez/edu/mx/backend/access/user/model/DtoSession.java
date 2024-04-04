package utez.edu.mx.backend.access.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoSession {

    @NotNull(groups = {Modify.class})
    private Long id;

    @NotBlank(groups = {ChangePassword.class, VerifyCode.class})
    @Pattern(regexp = "^(\\d{10}|\\d{3}-\\d{3}-\\d{4}|\\d{3} \\d{3} \\d{4}|\\(\\d{3}\\) \\d{3}-\\d{4})$", groups = {ChangePassword.class, VerifyCode.class}, message = "Invalid phone number format")
    private String phone;

    @NotBlank(groups = {VerifyCode.class})
    private String password;

    @NotBlank(groups = {VerifyCode.class})
    private String token;

    public interface ChangePassword{}
    public interface VerifyCode{}
    public interface Modify{}
}
