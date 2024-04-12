package utez.edu.mx.backend.access.user.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.backend.access.role.model.Role;
import utez.edu.mx.backend.base_catalog.person.model.Person;
import utez.edu.mx.backend.base_catalog.speciality.model.Speciality;
import utez.edu.mx.backend.execution.appoint.model.Appoint;
import utez.edu.mx.backend.execution.patient.model.Patient;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code", columnDefinition = "VARCHAR(15) UNIQUE NOT NULL")
    private String code;

    @Column(name = "password", columnDefinition = "VARCHAR(100) NOT NULL")
    private String password;

    @Column(name = "img", columnDefinition = "TEXT DEFAULT 'https://res.cloudinary.com/dkrcosw87/image/upload/v1623680134/utez/utez_logo.png'")
    private String img;

    @Column(name = "token", columnDefinition = "VARCHAR(200) NOT NULL")
    private String token;

    @Column(name = "available", nullable = false)
    private boolean available;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiration", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date expiration;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Appoint> appoints;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "speciality_id", referencedColumnName = "id")
    private Speciality speciality;

    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    private List<Patient> patients;

    @PrePersist
    protected void onCreate() {
        this.expiration = new Date();
    }

    public User(String code, String password, String token, Long person, Role role, Speciality speciality) {
        this.code = code;
        this.password = password;
        this.token = token;
        this.available = true;
        Person newPerson = new Person();
        newPerson.setId(person);
        this.person = newPerson;
        this.role = role;
        this.speciality = speciality;
    }

    public User(String code, String password, Date expiration, String token, Long person, Role role, Speciality speciality) {
        this.code = code;
        this.password = password;
        this.token = token;
        this.available = true;
        this.expiration = expiration;
        Person newPerson = new Person();
        newPerson.setId(person);
        this.person = newPerson;
        this.role = role;
        this.speciality = speciality;
    }
}
