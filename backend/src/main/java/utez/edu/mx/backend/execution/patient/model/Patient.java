package utez.edu.mx.backend.execution.patient.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.backend.base_catalog.person.model.Person;
import utez.edu.mx.backend.execution.expedient.model.Expedient;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@Entity(name = "patients")
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date created_at;

    @Column(name = "place_of_birth", columnDefinition = "VARCHAR(70) NOT NULL")
    private String place_of_birth;

    @Column(name = "marital_status")
    private TypeMaritalStatus marital_status;

    @Column(name = "created_by", columnDefinition = "VARCHAR(50) NOT NULL")
    private String created_by;

    @Column(name = "occupation", columnDefinition = "VARCHAR(70) NOT NULL")
    private String occupation;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @OneToOne(mappedBy = "patient")
    @JsonIgnore
    private Expedient expedient;

    @PrePersist
    protected void onCreate() {
        this.created_at = new Date();
    }
    public Patient(String place_of_birth, TypeMaritalStatus marital_status, String created_by, String occupation, Person person) {
        this.place_of_birth = place_of_birth;
        this.marital_status = marital_status;
        this.created_by = created_by;
        this.occupation = occupation;
        this.person = person;
    }

    public Patient(Long id, String place_of_birth, TypeMaritalStatus marital_status, String created_by, String occupation, Person person) {
        this.id = id;
        this.place_of_birth = place_of_birth;
        this.marital_status = marital_status;
        this.created_by = created_by;
        this.occupation = occupation;
        this.person = person;
    }
}
