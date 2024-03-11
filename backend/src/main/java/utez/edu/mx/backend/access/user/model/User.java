package utez.edu.mx.backend.access.user.model;

import javax.persistence.*;
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
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code", columnDefinition = "VARCHAR(15) NOT NULL")
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
    private Appoint appoint;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "speciality_id", referencedColumnName = "id")
    private Speciality speciality;
}
