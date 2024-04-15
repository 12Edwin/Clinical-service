package utez.edu.mx.backend.execution.treatment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.backend.base_catalog.service.model.Service;
import utez.edu.mx.backend.execution.appoint.model.Appoint;
import utez.edu.mx.backend.execution.expedient.model.Expedient;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "treatments")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "studies_description", columnDefinition = "VARCHAR(150) NOT NULL")
    private String studies_description;

    @Column(name = "support_home", columnDefinition = "VARCHAR(150) NOT NULL")
    private String support_home;

    @Column(name = "recommendation", columnDefinition = "VARCHAR(150) NOT NULL")
    private String recommendation;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Service service;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "expedient_id", referencedColumnName = "id")
    private Expedient expedient;

    @OneToMany(mappedBy = "treatment", fetch = FetchType.LAZY)
    private List<Appoint> appoints;
}
