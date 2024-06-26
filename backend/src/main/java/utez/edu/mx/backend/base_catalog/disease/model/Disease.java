package utez.edu.mx.backend.base_catalog.disease.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.backend.execution.expedient.model.Expedient;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "diseases")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(50) NOT NULL")
    private String name;

    @Column(name = "description", columnDefinition = "VARCHAR(150) NOT NULL")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "expedient_id", referencedColumnName = "id")
    private Expedient expedient;

    public DtoDisease cast(){
        return new DtoDisease(getId(), getName(), getDescription());
    }
}
