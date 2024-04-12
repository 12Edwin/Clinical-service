package utez.edu.mx.backend.execution.ratting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "ratting")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ratting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(100) NOT NULL")
    private String name;

    private float score;

    @Column(name = "comment", columnDefinition = "VARCHAR(200) NOT NULL")
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date created_at;

    @PrePersist
    protected void onCreate() {
        this.created_at = new Date();
    }


}
