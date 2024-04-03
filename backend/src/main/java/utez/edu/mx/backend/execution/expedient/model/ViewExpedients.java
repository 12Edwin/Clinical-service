package utez.edu.mx.backend.execution.expedient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import utez.edu.mx.backend.execution.doctor.model.ViewDoctors;

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
public class ViewExpedients {
    @NotNull(groups = {Modify.class})
    @Id
    private Long id;

    private String folio;

    //Physical records
    @NotNull(groups = {Modify.class})
    @Min(value = 1, groups = {Modify.class})
    @Max(value = 1000000, groups = {Modify.class})
    private Long physic_id;
    @NotNull(groups = {Register.class, Modify.class})
    @Min(value = 10, groups = {Register.class, Modify.class})
    @Digits(integer = 3, fraction = 2, groups = { Register.class, Modify.class})
    private float weight;
    @NotNull(groups = { Register.class,  Modify.class})
    @Min(value = 1, groups = { Register.class,  Modify.class})
    @Max(value = 3, groups = { Register.class,  Modify.class})
    @Digits(integer = 1, fraction = 2, groups = {  Register.class,  Modify.class})
    private float height;
    @NotBlank(groups = { Register.class,  Modify.class})
    @Pattern(regexp = "^(Masculino|Femenino)$", groups = { Register.class,  Modify.class}, message = "Género inválido")
    private String gender;
    @Null
    @Size(min = 1)
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+)*$", groups = { Register.class,  Modify.class}, message = "Alergía inválida")
    private String allergies;

    //Patient
    @NotNull(groups = { Modify.class})
    @Min(value = 1, groups = { Modify.class})
    @Max(value = 1000000, groups = { Modify.class})
    private Long patient_id;
    @NotBlank(groups = { Register.class,  Modify.class})
    @Length(min = 3, max = 70, groups = { Register.class,  Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ,.0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ,.0-9]+)*$", groups = { Register.class,  Modify.class}, message = "Lugar de nacimiento inválido")
    private String place_of_birth;
    @NotBlank(groups = { Register.class,  Modify.class})
    @Pattern(regexp = "^(Casado|Soltero|Viudo)$", groups = { Register.class,  Modify.class}, message = "Estado civil inválido")
    private String marital_status;
    @NotBlank(groups = { Register.class,  Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]+)*$", groups = { Register.class,  Modify.class}, message = "Responsable inválido")
    @Length(min = 3, max = 50, groups = { Register.class,  Modify.class})
    private String created_by;
    @NotBlank(groups = { Register.class,  Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+)*$", groups = { Register.class,  Modify.class}, message = "Ocupación inválido")
    @Length(min = 3, max = 70, groups = { Register.class,  Modify.class})
    private String occupation;
    @NotBlank(groups = {DtoExpedient.Register.class, DtoExpedient.Modify.class})
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", groups = {DtoExpedient.Register.class, DtoExpedient.Modify.class}, message = "Invalid email")
    private String email;

    //Person
    @NotNull(groups = { Modify.class})
    @Min(value = 1, groups = { Modify.class})
    @Max(value = 1000000, groups = { Modify.class})
    private Long person_id;
    @NotBlank(groups = { Register.class,  Modify.class})
    @Length(min = 3, max = 70, groups = { Register.class,  Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]+$", groups = { Register.class,  Modify.class}, message = "Nombre invalido")
    private String name;
    @NotBlank(groups = { Register.class,  Modify.class})
    @Length(min = 3, max = 70, groups = { Register.class,  Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]+)*$", groups = { Register.class,  Modify.class}, message = "Apellido inválido")
    private String surname;
    @Null
    @Length(min = 3, max = 70, groups = { Register.class,  Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]+)*$", groups = {ViewDoctors.Register.class, ViewDoctors.Modify.class}, message = "Apellido inválido")
    private String lastname;
    @NotNull(groups = { Register.class,  Modify.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @NotNull(groups = { Register.class,  Modify.class})
    @Pattern(regexp = "^(Masculino|Femenino)$", groups = { Register.class,  Modify.class}, message = "Sexo inválido")
    private String sex;
    @NotBlank(groups = { Register.class,  Modify.class})
    @Length(min = 10, max = 10, groups = { Register.class, Modify.class})
    private String phone;

    public interface Register{}
    public interface Modify{}
    public interface ChangeStatus{}

    public DtoExpedient cast(){
        return new DtoExpedient(getId(), getFolio(), getPhysic_id(), getWeight(), getHeight(), getGender(), getAllergies(),
                null, null, getPatient_id(), getPlace_of_birth(), getMarital_status(), getCreated_by(),
                getOccupation(), getEmail(), getPerson_id(), getName(), getSurname(), getLastname(), getBirthday(), getSex(), getPhone());
    }
}
