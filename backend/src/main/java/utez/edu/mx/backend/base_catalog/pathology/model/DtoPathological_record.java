package utez.edu.mx.backend.base_catalog.pathology.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import utez.edu.mx.backend.execution.expedient.model.Expedient;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoPathological_record {

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

    private Long expedient;

    @NotNull(groups = { Register.class,  Modify.class})
    @Min(value = 1, groups = {Register.class, Modify.class})
    @Max(value = 1000000, groups = { Register.class, Modify.class})
    private Long typePathological;

    public interface Register{}
    public interface Modify{}
    public interface Delete{}

    public Pathological_record cast(){
        Pathological_record pathology = new Pathological_record();
        pathology.setId(getId());
        pathology.setName(getName());
        pathology.setDescription(getDescription());
        Expedient exp = new Expedient();
        exp.setId(getExpedient());
        pathology.setExpedient(exp);
        TypePathological type = new TypePathological();
        type.setId(getTypePathological());
        pathology.setTypePathological(type);
        return pathology;
    }
}
