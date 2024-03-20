package utez.edu.mx.backend.execution.doctor.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.backend.execution.doctor.model.ViewDoctors;
import utez.edu.mx.backend.security.service.CryptService;

import java.sql.SQLException;


@RestController
@RequestMapping("/api/doctor")
@AllArgsConstructor
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class DoctorController {

    private static final String DOCTOR = "DOCTOR";

    @Autowired
    private final DoctorService service;

    private final CryptService cryptService;
    private final ObjectMapper objectMapper;

    @GetMapping("/")
    ResponseEntity<?> findAllDoctors (Pageable pageable){
        return service.findAllDoctors(pageable);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findAllDoctors (@PathVariable(name = "id") Long id) throws SQLException {
        return service.findDoctor(id);
    }

    @PostMapping("/")
    ResponseEntity<?> saveDoctor (@RequestBody ViewDoctors doctor) throws SQLException {
        return service.saveDoctor(doctor);
    }

    @PutMapping("/")
    ResponseEntity<?> updateDoctor (@RequestBody ViewDoctors doctor) throws SQLException {
        return service.updateDoctor(doctor);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> lockDoctor (@PathVariable Long id) throws SQLException {
        return service.lockDoctor(id);
    }

}
