package utez.edu.mx.backend.base_catalog.speciality.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import utez.edu.mx.backend.execution.doctor.model.ViewDoctors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoSpeciality {

    @NotNull(groups = {Modify.class, Delete.class})
    private Long id;

    @NotBlank(groups = {Register.class, Modify.class})
    @Length(min = 3, max = 60, groups = {Register.class, Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+)*$", groups = {Register.class, Modify.class}, message = "Nombre inválido")
    private String name;

    @NotBlank(groups = {Register.class, Modify.class})
    @Length(min = 3, max = 150, groups = {Register.class, Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+)*$", groups = {Register.class, Modify.class}, message = "Descripción inválida")
    private String description;

    public interface Register{}
    public interface Modify{}
    public interface Delete{}

    public Speciality cast (){
        Speciality speciality = new Speciality();
        speciality.setId(getId());
        speciality.setName(getName());
        speciality.setDescription(getDescription());
        return speciality;
    }
}
