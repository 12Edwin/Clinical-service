package utez.edu.mx.backend.base_catalog.pathology.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.base_catalog.pathology.model.PathologicalRepository;
import utez.edu.mx.backend.base_catalog.pathology.model.TypePathological;
import utez.edu.mx.backend.base_catalog.pathology.model.TypePathologicalRepository;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class PathologicalService {

    @Autowired
    private PathologicalRepository pathologicalRepository;

    @Autowired
    private TypePathologicalRepository typePathologicalRepository;

    @Transactional(readOnly = true)
    public Optional<TypePathological> findFirstByName(String name){
        return typePathologicalRepository.findByName(name);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public TypePathological saveInitial(TypePathological typePathological){
        return typePathologicalRepository.save(typePathological);
    }
}
