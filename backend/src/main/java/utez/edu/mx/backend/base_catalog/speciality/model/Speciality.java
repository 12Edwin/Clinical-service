package utez.edu.mx.backend.base_catalog.speciality.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.base_catalog.service.model.Service;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "specialities")
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(60) NOT NULL")
    private String name;

    @Column(name = "description", columnDefinition = "VARCHAR(60) NOT NULL")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "speciality")
    private List<Service> services;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "speciality")
    private List<User> users;
}

