package utez.edu.mx.backend.base_catalog.space.control;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.base_catalog.space.model.Space;
import utez.edu.mx.backend.base_catalog.space.model.SpaceRepository;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class SpaceService {

    @Autowired
    private final SpaceRepository repository;
    @Transactional
    public Space saveInitial(Space space){
        return repository.save(space);
    }

    @Transactional(readOnly = true)
    public Optional<Space> findFirstByName(String name){
        return repository.findFirstByName(name);
    }
}
