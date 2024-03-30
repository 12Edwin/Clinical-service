package utez.edu.mx.backend.execution.appoint.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.base_catalog.space.model.Space;
import utez.edu.mx.backend.execution.treatment.model.Treatment;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoAppoint {

    @NotNull(groups = {Modify.class})
    @Min(value = 1, groups = {Modify.class})
    @Max(value = 1000000, groups = {Modify.class})
    private Long id;

    @NotNull(groups = {Modify.class})
    @Pattern(regexp = "^(Cancelada|Completada|Pendiente|Reprogramada)$", groups = {Modify.class}, message = "Status inválido")
    private String status;

    @NotNull(groups = {Register.class, Modify.class, Reschedule.class, FindByDate.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start_hour;

    @NotNull(groups = {Register.class, Modify.class, Reschedule.class, FindByDate.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end_hour;

    @NotNull(groups = {Modify.class, Cancel.class, Complete.class, Reschedule.class})
    @Min(value = 1, groups = {Modify.class})
    @Max(value = 1000000, groups = {Modify.class})
    private Long user;

    @NotNull(groups = {Modify.class, Register.class})
    @Min(value = 1, groups = {Modify.class})
    @Max(value = 1000000, groups = {Modify.class})
    private Long treatment;

    @NotNull(groups = {Modify.class, Register.class})
    @Min(value = 1, groups = {Modify.class})
    @Max(value = 1000000, groups = {Modify.class})
    private Long space;

    public interface Register{}
    public interface Modify{}
    public interface Cancel{}
    public interface Complete{}
    public interface Reschedule{}
    public interface FindByDate{}
    public interface Delete{}

    public Appoint cast(){
        Appoint appoint = new Appoint();
        appoint.setId(getId());
        appoint.setStatus(getStatus() == null ? null : StatusAppoint.valueOf(getStatus()));
        User user1 = new User();
        user1.setId(getUser());
        appoint.setUser(user1);
        Treatment treatment1 = new Treatment();
        treatment1.setId(getTreatment());
        appoint.setTreatment(treatment1);
        Space space1 = new Space();
        space1.setId(getSpace());
        appoint.setSpace(space1);
        appoint.setStartHour(getStart_hour());
        appoint.setEndHour(getEnd_hour());
        return appoint;
    }
}
