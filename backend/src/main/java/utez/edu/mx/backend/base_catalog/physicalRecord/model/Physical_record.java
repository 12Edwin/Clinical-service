package utez.edu.mx.backend.base_catalog.physicalRecord.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.backend.execution.expedient.model.Expedient;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "physical_records")
public class Physical_record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "weight", columnDefinition = "FLOAT NOT NULL")
    private float weight;

    @Column(name = "height", columnDefinition = "FLOAT NOT NULL")
    private float height;

    @Column(name = "gender")
    private TypeGender gender;

    @Column(name = "allergies", columnDefinition = "VARCHAR(150) NOT NULL")
    private String allergies;

    @OneToOne(mappedBy = "physicalRecord")
    private Expedient expedient;

    public Physical_record(float weight, float height, TypeGender gender, String allergies) {
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.allergies = allergies;
    }
}
