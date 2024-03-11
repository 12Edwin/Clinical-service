package utez.edu.mx.backend.execution.appoint.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.base_catalog.schedule.model.Schedule;
import utez.edu.mx.backend.execution.treatment.model.Treatment;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "appoints")
public class Appoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at;

    public enum StatusAppoint {Pendiente, Completada, Cancelada};
    @Column(name = "status", nullable = false)
    private StatusAppoint status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "treatment_id", referencedColumnName = "id")
    private Treatment treatment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;
}
