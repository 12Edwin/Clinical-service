package utez.edu.mx.backend.execution.patient.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.backend.access.user.model.User;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndEmailNot(String email, String email2);

    List<Patient> findAllByCreatedBy(User user);
}
