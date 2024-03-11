package utez.edu.mx.backend.access.privilege.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity(name = "privileges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", columnDefinition = "VARCHAR(50)")
    private PrivilegeNames name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

}
