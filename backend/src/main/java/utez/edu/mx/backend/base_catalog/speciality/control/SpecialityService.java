package utez.edu.mx.backend.base_catalog.speciality.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.base_catalog.speciality.model.Speciality;
import utez.edu.mx.backend.base_catalog.speciality.model.SpecialityRepository;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class SpecialityService {

    @Autowired
    private SpecialityRepository repository;

    @Transactional(readOnly = true)
    public Optional<Speciality> findFirsByName(String name){
        return repository.findByName(name);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Speciality saveInitial(Speciality speciality){
        return repository.save(speciality);
    }
}
