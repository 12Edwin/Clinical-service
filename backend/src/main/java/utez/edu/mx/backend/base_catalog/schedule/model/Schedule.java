package utez.edu.mx.backend.base_catalog.schedule.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import utez.edu.mx.backend.execution.appoint.model.Appoint;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "start_hour", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start_hour;

    @Column(name = "end_hour", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end_hour;

    @Column(name = "busy_spaces", columnDefinition = "INT NOT NULL")
    private int busy_spaces;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    private List<Appoint> appoints;
}
