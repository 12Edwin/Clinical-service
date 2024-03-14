package utez.edu.mx.backend.execution.expedient.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.backend.base_catalog.pathology.model.Pathological_record;
import utez.edu.mx.backend.base_catalog.physicalRecord.model.Physical_record;
import utez.edu.mx.backend.execution.patient.model.Patient;
import utez.edu.mx.backend.execution.treatment.model.Treatment;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "expedients")
public class Expedient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "folio", columnDefinition = "VARCHAR(10) NOT NULL")
    private String folio;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date created_at;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "physical_id", referencedColumnName = "id")
    private Physical_record physicalRecord;

    @OneToMany(mappedBy = "expedient")
    private List<Pathological_record> pathologicalRecord;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToMany(mappedBy = "expedient")
    private List<Treatment> treatments;
}
