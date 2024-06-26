package utez.edu.mx.backend.base_catalog.pathology.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.backend.execution.expedient.model.Expedient;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "pathological_records")
public class Pathological_record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(50) NOT NULL")
    private String name;

    @Column(name = "description", columnDefinition = "VARCHAR(100) NOT NULL")
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "expedient_id", referencedColumnName = "id")
    private Expedient expedient;

    @ManyToOne
    @JoinColumn(name = "typePathological_id", referencedColumnName = "id")
    private TypePathological typePathological;

    public DtoPathological_record cast (){
        return new DtoPathological_record(getId(), getName(), getDescription(), getExpedient().getId(), getTypePathological().getId());
    }
}
