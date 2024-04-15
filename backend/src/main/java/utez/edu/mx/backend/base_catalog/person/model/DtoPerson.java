package utez.edu.mx.backend.base_catalog.person.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoPerson {

    private Long id;

    @NotBlank(groups = {Profile.class})
    @Length(min = 3, max = 60, groups = {Profile.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+)*$", groups = {Profile.class}, message = "Nombre inválido")
    private String name;

    @NotBlank(groups = {Profile.class})
    @Length(min = 3, max = 60, groups = {Profile.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+)*$", groups = {Profile.class}, message = "Apellido inválido")
    private String surname;

    @Null
    @Length(min = 3, max = 60, groups = {Profile.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+)*$", groups = {Profile.class}, message = "Apellido inválido")
    private String lastname;

    @NotNull(groups = {Profile.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @NotNull(groups = {Profile.class})
    @Pattern(regexp = "^(Masculino|Femenino)$", groups = {Profile.class}, message = "Sexo inválido")
    private String sex;

    @NotBlank(groups = {Profile.class})
    @Length(min = 10, max = 10, groups = {Profile.class})
    private String phone;

    public interface Profile{}
}
