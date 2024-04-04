package utez.edu.mx.backend.base_catalog.disease.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoDisease {

    @NotNull(groups = {Modify.class, Delete.class})
    private Long id;

    @NotBlank(groups = { Register.class,  Modify.class})
    @Length(min = 3, max = 60, groups = { Register.class, Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+)*$", groups = {Register.class,  Modify.class}, message = "Nombre inválido")
    private String name;

    @NotBlank(groups = { Register.class,  Modify.class})
    @Length(min = 3, max = 150, groups = { Register.class, Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+)*$", groups = {Register.class,  Modify.class}, message = "Descripción inválida")
    private String description;

    public interface Register{}
    public interface Modify{}
    public interface Delete{}

    public Disease cast(){
        Disease disease = new Disease();
        disease.setId(getId());
        disease.setName(getName());
        disease.setDescription(getDescription());
        return disease;
    }
}
