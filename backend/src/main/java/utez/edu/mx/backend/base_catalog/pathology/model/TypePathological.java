package utez.edu.mx.backend.base_catalog.pathology.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "type_pathological")
public class TypePathological {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(70) NOT NULL")
    private String name;

    @Column(name = "description", columnDefinition = "VARCHAR(250) NOT NULL")
    private String description;

    @OneToMany(mappedBy = "typePathological")
    private List<Pathological_record> pathologicalRecords;

    public TypePathological(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
