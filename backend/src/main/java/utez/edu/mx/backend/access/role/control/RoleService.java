package utez.edu.mx.backend.access.role.control;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.access.role.model.Role;
import utez.edu.mx.backend.access.role.model.RoleRepository;
import utez.edu.mx.backend.access.role.model.RoleTypes;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    @Transactional(readOnly = true)
    public Optional<Role> findById(Long id){
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Role> findByName(RoleTypes name) {
        return repository.findFirstByName(name);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Role saveInitial(Role role) {
        return repository.saveAndFlush(role);
    }
    @Transactional
    public Role updatePrivileges(Role role, String privileges) {
        role.setPrivileges(privileges);
        return repository.saveAndFlush(role);
    }
}
