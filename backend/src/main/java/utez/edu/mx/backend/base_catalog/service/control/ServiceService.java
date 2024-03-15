package utez.edu.mx.backend.base_catalog.service.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.base_catalog.service.model.Service;
import utez.edu.mx.backend.base_catalog.service.model.ServiceRepository;

import java.sql.SQLException;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
public class ServiceService {

    @Autowired
    private ServiceRepository repository;

    @Transactional(readOnly = true)
    public Optional<Service> findFirstByName(String name){
        return repository.findByName(name);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Service saveInitial(Service service){
        return repository.saveAndFlush(service);
    }
}
