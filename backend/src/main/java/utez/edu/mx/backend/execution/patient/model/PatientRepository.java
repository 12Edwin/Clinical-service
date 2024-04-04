package utez.edu.mx.backend.execution.patient.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndEmailNot(String email, String email2);
}
