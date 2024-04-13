package utez.edu.mx.backend.execution.ratting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoRatting {

    @NotNull(groups = {Modify.class})
    @Min(value = 1, groups = {Modify.class})
    @Max(value = 1000000, groups = {Modify.class})
    private Long id;
    @NotBlank(groups = {Register.class, Modify.class})
    @Length(min = 3, max = 60, groups = {Register.class, Modify.class})
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+)*$", groups = {Register.class, Modify.class}, message = "Nombre inválido")
    private String name;
    @NotNull(groups = {Register.class, Modify.class})
    @Pattern(regexp = "^(0|0.5|1|1.5|2|2.5|3|3.5|4|4.5|5)$", groups = { Register.class, Modify.class }, message = "Invalid score")
    private String score;
    @NotBlank(groups = {Register.class, Modify.class})
    @Length(min = 3, max = 200, groups = {Register.class, Modify.class})
    private String comment;

    public interface Modify{}
    public interface Register{}

    public Ratting cast() {
        return new Ratting(
            this.id,
            this.name,
            Float.parseFloat(this.score),
            this.comment,
            new Date()
        );
    }
}
