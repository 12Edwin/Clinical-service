package utez.edu.mx.backend.execution.doctor.control;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.execution.doctor.model.ViewDoctors;
import utez.edu.mx.backend.execution.doctor.model.DoctorRepository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class DoctorService {

    @Autowired
    private final DoctorRepository viewRepository;
    @Autowired
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public ResponseEntity<?> findDoctor(Long id){
        Optional<ViewDoctors> doctor = viewRepository.findById(id);
        if (doctor.isPresent()){
            return new ResponseEntity<>(doctor.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findAllDoctors(Pageable pageable){
        return new ResponseEntity<> (viewRepository.findAll(pageable), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> saveDoctor(ViewDoctors doctor){
        Map<String, Boolean> data = new HashMap<>();
        try {

            data.put("result", true);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            data.put("result", false);
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ViewDoctors updateDoctor(ViewDoctors doctor){
        return new ViewDoctors();
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ViewDoctors lockDoctor(Long id){
        return new ViewDoctors();
    }
}
