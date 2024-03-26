package utez.edu.mx.backend.base_catalog.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import utez.edu.mx.backend.base_catalog.speciality.model.Speciality;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoService {

    @NotNull(groups = {Modify.class,  Delete.class})
    private Long id;

    @NotBlank(groups = { Register.class,  Modify.class})
    @Length(min = 3, max = 60, groups = { Register.class, Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+)*$", groups = {Register.class,  Modify.class}, message = "Nombre inválido")
    private String name;

    @NotBlank(groups = { Register.class,  Modify.class})
    @Length(min = 3, max = 150, groups = { Register.class,  Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+)*$", groups = { Register.class,  Modify.class}, message = "Descripción inválida")
    private String description;

    @NotNull(groups = { Register.class,  Modify.class})
    @Min(value = 0, groups = { Register.class,  Modify.class})
    @Digits(integer = 5, fraction = 2, groups = { Register.class,  Modify.class})
    private float price;

    @NotNull(groups = { Register.class,  Modify.class})
    @Min(value = 1, groups = { Register.class,  Modify.class})
    @Max(value = 1000000, groups = { Register.class,  Modify.class})
    private Long speciality;

    public interface Register{}
    public interface Modify{}
    public interface Delete{}

    public Service cast (){
        Service service = new Service();
        service.setId(getId());
        service.setName(getName());
        service.setDescription(getDescription());
        service.setPrice(getPrice());
        Speciality spe = new Speciality();
        spe.setId(getSpeciality());
        service.setSpeciality(spe);
        return service;
    }
}
