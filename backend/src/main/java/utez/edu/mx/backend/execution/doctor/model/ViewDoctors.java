package utez.edu.mx.backend.execution.doctor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import utez.edu.mx.backend.base_catalog.person.model.SexType;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Immutable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewDoctors {

    @Id
    private Long id;
    private String fullname;
    private String name;
    private String code;
    private String password;
    private String surname;
    private String lastname;
    private Date birthday;
    private String phone;
    private SexType sex;
    private String speciality;
    private int speciality_id;
    private String description;
}
