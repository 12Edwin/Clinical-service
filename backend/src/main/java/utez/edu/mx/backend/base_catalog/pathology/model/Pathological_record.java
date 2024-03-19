package utez.edu.mx.backend.base_catalog.pathology.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.backend.execution.expedient.model.Expedient;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "expedient_id", referencedColumnName = "id")
    private Expedient expedient;

}