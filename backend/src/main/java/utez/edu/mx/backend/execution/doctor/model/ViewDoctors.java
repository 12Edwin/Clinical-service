package utez.edu.mx.backend.execution.doctor.model;

import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import utez.edu.mx.backend.base_catalog.person.model.SexType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Immutable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewDoctors {

    @NotNull(groups = {Modify.class})
    @Id
    private Long id;

    private String fullname;
    @NotBlank(groups = {Register.class, Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]+$", groups = {Register.class, Modify.class}, message = "Nombre invalido")
    private String name;
    @NotBlank(groups = {Register.class})
    private String code;
    @NotBlank(groups = {Register.class})
    private String password;
    @NotBlank(groups = {Register.class, Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]+)*$", groups = {Register.class, Modify.class}, message = "Apellido inválido")
    private String surname;
    @Null
    @Size(min = 1)
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]+)*$", groups = {Register.class, Modify.class}, message = "Apellido inválido")
    private String lastname;
    @NotNull(groups = {Register.class, Modify.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @NotBlank(groups = {Register.class})
    @Length(min = 10, max = 10, groups = {Register.class, Modify.class})
    private String phone;
    @NotNull(groups = {Register.class, Modify.class})
    @Pattern(regexp = "^(Masculino|Femenino)$", groups = {Register.class, Modify.class}, message = "Sexo inválido")
    private String sex;
    private String speciality;
    @NotNull(groups = {Register.class, Modify.class})
    @Min(value = 1, groups = {Register.class, Modify.class})
    private int speciality_id;
    private String description;

    public interface Register{}
    public interface Modify{}
    public interface ChangeStatus{}
}
