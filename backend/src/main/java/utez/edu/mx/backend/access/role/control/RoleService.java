package utez.edu.mx.backend.access.role.control;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.access.role.model.Role;
import utez.edu.mx.backend.access.role.model.RoleRepository;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class RoleService {

    @Autowired
    private final RoleRepository repository;

    @Transactional(readOnly = true)
    public Optional<Role> findById(Long id){
        return repository.findById(id);
    }
}
