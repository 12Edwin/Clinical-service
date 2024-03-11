package utez.edu.mx.backend.base_catalog.person.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.execution.patient.model.Patient;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(70) NOT NULL")
    private String name;

    @Column(name = "surname", columnDefinition = "VARCHAR(70) NOT NULL")
    private String surname;

    @Column(name = "lastname", columnDefinition = "VARCHAR(70) NOT NULL")
    private String lastname;

    @Column(name = "birthday")
    private Date birthday;

    public enum Sex {Masculino, Femenino};
    @Column(name = "sex")
    private Sex sex;

    @Column(name = "phone")
    private Long phone;

    @OneToOne(mappedBy = "person")
    private User user;

    @OneToOne(mappedBy = "person")
    private Patient patient;
}
