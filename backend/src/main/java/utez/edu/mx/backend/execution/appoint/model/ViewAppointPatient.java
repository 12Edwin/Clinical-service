package utez.edu.mx.backend.execution.appoint.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
@Immutable
@AllArgsConstructor
@NoArgsConstructor
public class ViewAppointPatient {

    // Appoint
    @Id
    private Long id;

    private Date start_hour;

    private Date end_hour;

    private String status;

    // Person
    private Long person_id;

    private String fullname;

    private Date birthday;

    private String phone;

    private String sex;

    private String email;

    // Space
    private Long space_id;

    private String space_name;

    // Treatment
    private Long treatment_id;

    private String studies_description;

    private String recommendation;

    private String support_home;

}
