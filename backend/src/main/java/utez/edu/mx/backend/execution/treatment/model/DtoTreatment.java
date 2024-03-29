package utez.edu.mx.backend.execution.treatment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import utez.edu.mx.backend.base_catalog.service.model.Service;
import utez.edu.mx.backend.execution.expedient.model.Expedient;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoTreatment {

    @NotNull(groups = {Modify.class})
    @Min(value = 1, groups = {Modify.class})
    @Max(value = 1000000, groups = {Modify.class})
    private Long id;

    @NotBlank(groups = {Register.class, Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+)*$", groups = {Register.class, Modify.class}, message = "Estudios de laboratorio inválidos")
    @Length(min = 3, max = 150, groups = {Register.class, Modify.class})
    private String studies_description;

    @NotBlank(groups = {Register.class, Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+)*$", groups = {Register.class, Modify.class}, message = "Estudios de laboratorio inválidos")
    @Length(min = 3, max = 150, groups = {Register.class, Modify.class})
    private String support_home;

    @NotBlank(groups = {Register.class, Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+)*$", groups = {Register.class, Modify.class}, message = "Estudios de laboratorio inválidos")
    @Length(min = 3, max = 150, groups = {Register.class, Modify.class})
    private String recommendation;

    @NotNull(groups = {Modify.class})
    @Min(value = 1, groups = {Modify.class})
    @Max(value = 1000000, groups = {Modify.class})
    private Long service;

    @NotNull(groups = {Modify.class})
    @Min(value = 1, groups = {Modify.class})
    @Max(value = 1000000, groups = {Modify.class})
    private Long expedient;

    public interface Register{}
    public interface Modify{}
    public interface Delete{}

    public Treatment cast(){
        Treatment treatment = new Treatment();
        treatment.setId(getId());
        treatment.setRecommendation(getRecommendation());
        treatment.setSupport_home(getSupport_home());
        treatment.setStudies_description(getStudies_description());
        Service service1 = new Service();
        service1.setId(getService());
        Expedient expedient1 = new Expedient();
        expedient1.setId(getExpedient());
        treatment.setService(service1);
        treatment.setExpedient(expedient1);
        return treatment;
    }
}
