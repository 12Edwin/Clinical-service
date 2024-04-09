package utez.edu.mx.backend.base_catalog.person.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.execution.patient.model.Patient;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(70) NOT NULL")
    private String name;

    @Column(name = "surname", columnDefinition = "VARCHAR(70) NOT NULL")
    private String surname;

    @Column(name = "lastname", columnDefinition = "VARCHAR(70)")
    private String lastname;

    @Column(name = "birthday", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", columnDefinition = "VARCHAR(40) NOT NULL")
    private SexType sex;

    @Column(name = "phone", columnDefinition = "VARCHAR(12) UNIQUE NOT NULL")
    @Length(min = 10, max = 12)
    private String phone;

    @OneToOne(mappedBy = "person")
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "person")
    @JsonIgnore
    private Patient patient;

    public Person(String name, String surname, String lastname, Date birthday, SexType sex, String phone) {
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.sex = sex;
        this.phone = phone;
    }
    public Person(Long id, String name, String surname, String lastname, Date birthday, SexType sex, String phone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.sex = sex;
        this.phone = phone;
    }
}
