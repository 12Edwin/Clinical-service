package utez.edu.mx.backend.base_catalog.service.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.backend.base_catalog.speciality.model.Speciality;
import utez.edu.mx.backend.execution.treatment.model.Treatment;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(100) NOT NULL")
    private String name;

    @Column(name = "description", columnDefinition = "VARCHAR(140) NOT NULL")
    private String description;

    @Column(name = "price", nullable = false)
    private float price;

    @ManyToOne
    @JoinColumn(name = "speciality_id", referencedColumnName = "id")
    private Speciality speciality;

    @OneToMany(mappedBy = "service")
    private List<Treatment> treatments;

    public Service(String name, String description, float price, Speciality speciality) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.speciality = speciality;
    }
}
