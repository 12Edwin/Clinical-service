package utez.edu.mx.backend.base_catalog.person.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.base_catalog.person.model.Person;
import utez.edu.mx.backend.base_catalog.person.model.PersonRepository;
import utez.edu.mx.backend.utils.entity.BadRequests;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    @Transactional(readOnly = true)
    public Optional<Person> findByName (String name){
        return repository.findByName(name);
    }
    @Transactional(readOnly = true)
    public Optional<Person> findById (Long id){
        return repository.findById(id);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> save (Person person) throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {
        if (person.getName() == null || person.getSurname() == null
                || Objects.equals(person.getName(), "")
                || Objects.equals(person.getSurname(), "")
                || person.getPhone() == null
                || person.getSex() == null || person.getBirthday() == null
        ) throw new IllegalArgumentException(BadRequests.MISSING_FIELDS.getText());

        Calendar cal = Calendar.getInstance();
        cal.setTime(person.getBirthday());
        cal.set((cal.get(Calendar.YEAR) + 18), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        if (cal.getTime().after(Calendar.getInstance().getTime())){
            return new ResponseEntity<>(new Message("invalid birthday", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (repository.existsByPhone(person.getPhone())){
            return new ResponseEntity<>(new Message("phone already registered", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Person newPerson = repository.save(person);
        if (newPerson.getName() == null){
            return new ResponseEntity<>(new Message("Unregistered person", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Message(newPerson, "Registered person", TypeResponse.SUCCESS), HttpStatus.OK);
    }


    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> update (Person person) throws IllegalArgumentException {
        if (person.getName() == null || person.getSurname() == null || person.getId() <= 0
                || Objects.equals(person.getName(), "")
                || Objects.equals(person.getSurname(), "")
                || person.getSex() == null || person.getBirthday() == null
        ) throw new IllegalArgumentException(BadRequests.MISSING_FIELDS.getText());

        Calendar cal = Calendar.getInstance();
        cal.setTime(person.getBirthday());
        cal.set((cal.get(Calendar.YEAR) + 18), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        if (cal.getTime().after(Calendar.getInstance().getTime())){
            return new ResponseEntity<>(new Message("invalid birthday", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (!repository.existsById(person.getId())){
            return new ResponseEntity<>(new Message("Person not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (repository.existsByPhoneAndIdNot(person.getPhone(), person.getId())){
            return new ResponseEntity<>(new Message("Phone already registered", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        boolean updatedDoctor = repository.updateDoctor(
                person.getId(), person.getName(), person.getSurname(), person.getLastname(),
                person.getBirthday(), person.getSex()) >= 1;

        if (!updatedDoctor){
            return new ResponseEntity<>(new Message("Person not updated", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Message( "Updated person", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Person saveInitial (Person person){
        return repository.save(person);
    }
}
