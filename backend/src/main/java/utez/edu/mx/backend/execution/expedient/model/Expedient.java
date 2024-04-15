package utez.edu.mx.backend.execution.expedient.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.backend.base_catalog.pathology.model.Pathological_record;
import utez.edu.mx.backend.base_catalog.physicalRecord.model.Physical_record;
import utez.edu.mx.backend.execution.patient.model.Patient;
import utez.edu.mx.backend.execution.treatment.model.Treatment;

import javax.persistence.*;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date created_at;

    @OneToOne
    @JoinColumn(name = "physical_id", referencedColumnName = "id")
    private Physical_record physicalRecord;

    @OneToMany(mappedBy = "expedient")
    private List<Pathological_record> pathologicalRecord;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToMany(mappedBy = "expedient")
    private List<Treatment> treatments;

    @PrePersist
    protected void onCreate() {
        this.created_at = new Date();
    }
    public Expedient(Physical_record physicalRecord, Patient patient) {
        this.physicalRecord = physicalRecord;
        this.patient = patient;
    }

    public Expedient(Long id, Physical_record physicalRecord, Patient patient) {
        this.id = id;
        this.physicalRecord = physicalRecord;
        this.patient = patient;
    }
}
