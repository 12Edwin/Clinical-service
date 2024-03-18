package utez.edu.mx.backend.base_catalog.person.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.base_catalog.person.model.Person;
import utez.edu.mx.backend.base_catalog.person.model.PersonRepository;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Transactional(readOnly = true)
    public Optional<Person> findByName (String name){
        return repository.findByName(name);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Person saveInitial (Person person){
        return repository.save(person);
    }
}
