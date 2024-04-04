package utez.edu.mx.backend.execution.appoint.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.base_catalog.space.model.Space;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at;

    @Column(name = "start_hour", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startHour;

    @Column(name = "end_hour", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endHour;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(70)")
    private StatusAppoint status;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "treatment_id", referencedColumnName = "id")
    private Treatment treatment;

    @ManyToOne
    @JoinColumn(name = "space_id", referencedColumnName = "id")
    private Space space;

    @PrePersist
    protected void onCreate() {
        this.created_at = new Date();
    }
}
