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

    @Column(name = "token", columnDefinition = "VARCHAR(200) NOT NULL")
    private String token;

    @Column(name = "available", nullable = false)
    private boolean available;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Appoint appoint;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "speciality_id", referencedColumnName = "id")
    private Speciality speciality;

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
}
