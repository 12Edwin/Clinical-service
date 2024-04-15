package utez.edu.mx.backend.access.privilege.control;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.access.privilege.model.Privilege;
import utez.edu.mx.backend.access.privilege.model.PrivilegeNames;
import utez.edu.mx.backend.access.privilege.model.PrivilegeRepository;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PrivilegeService {

    private final PrivilegeRepository repository;
    @Transactional(readOnly = true)
    public Optional<Privilege> findByName(PrivilegeNames name) {
        return repository.findFirstByName(name);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Privilege saveInitial(Privilege privilege) {
        return repository.saveAndFlush(privilege);
    }
}
