package utez.edu.mx.backend.execution.patient.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.base_catalog.person.model.Person;
import utez.edu.mx.backend.execution.expedient.model.Expedient;

import javax.persistence.*;
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

    @Column(name = "email", columnDefinition = "VARCHAR(100) UNIQUE NOT NULL")
    private String email;

    @Column(name = "place_of_birth", columnDefinition = "VARCHAR(70) NOT NULL")
    private String place_of_birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", columnDefinition = "VARCHAR(50) NOT NULL")
    private TypeMaritalStatus marital_status;

    @Column(name = "occupation", columnDefinition = "VARCHAR(70) NOT NULL")
    private String occupation;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @OneToOne(mappedBy = "patient")
    @JsonIgnore
    private Expedient expedient;

    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false, nullable = false, referencedColumnName = "id")
    private User createdBy;

    @PrePersist
    protected void onCreate() {
        this.created_at = new Date();
    }
    public Patient(String place_of_birth, TypeMaritalStatus marital_status, String occupation, User created_by, String email, Person person) {
        this.place_of_birth = place_of_birth;
        this.marital_status = marital_status;
        this.occupation = occupation;
        this.createdBy = created_by;
        this.email = email;
        this.person = person;
    }

    public Patient(Long id, String place_of_birth, TypeMaritalStatus marital_status, String occupation, String email, Person person) {
        this.id = id;
        this.place_of_birth = place_of_birth;
        this.marital_status = marital_status;
        this.occupation = occupation;
        this.email = email;
        this.person = person;
    }
}
