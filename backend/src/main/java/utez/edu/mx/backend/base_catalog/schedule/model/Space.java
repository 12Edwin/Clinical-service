package utez.edu.mx.backend.base_catalog.schedule.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.backend.execution.appoint.model.Appoint;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "spaces")
public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(70) NOT NULL")
    private String name;

    @Column(name = "description", columnDefinition = "VARCHAR(150) NOT NULL")
    private String description;

    @Column(name = "busy_spaces", columnDefinition = "INT NOT NULL")
    private int busy_spaces;

    @OneToMany(mappedBy = "space")
    @JsonIgnore
    private List<Appoint> appoints;

    public Space(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
