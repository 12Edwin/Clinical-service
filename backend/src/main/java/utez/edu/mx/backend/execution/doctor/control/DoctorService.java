package utez.edu.mx.backend.execution.doctor.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.access.role.control.RoleService;
import utez.edu.mx.backend.access.role.model.Role;
import utez.edu.mx.backend.access.role.model.RoleTypes;
import utez.edu.mx.backend.access.user.control.UserService;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.access.user.model.UserRepository;
import utez.edu.mx.backend.base_catalog.person.control.PersonService;
import utez.edu.mx.backend.base_catalog.person.model.Person;
import utez.edu.mx.backend.base_catalog.person.model.SexType;
import utez.edu.mx.backend.base_catalog.speciality.control.SpecialityService;
import utez.edu.mx.backend.base_catalog.speciality.model.Speciality;
import utez.edu.mx.backend.execution.doctor.model.DoctorRepository;
import utez.edu.mx.backend.execution.doctor.model.ViewDoctors;
import utez.edu.mx.backend.security.service.CryptService;
import utez.edu.mx.backend.utils.entity.BadRequests;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class DoctorService {

    private final DoctorRepository viewRepository;
    private final PersonService personService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final SpecialityService specialityService;

    private final ObjectMapper mapper;
    private static final CryptService cryptService = new CryptService();

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findDoctor(Long id) throws IllegalArgumentException, JsonProcessingException, UnsupportedEncodingException {
        if (id <= 0) throw new IllegalArgumentException(BadRequests.MISSING_FIELDS.getText());
        Optional<ViewDoctors> doctor = viewRepository.findById(id);
        if (doctor.isEmpty()){
            return new ResponseEntity<>(new Message(BadRequests.NOT_FOUND.getText(), TypeResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(doctor.get(), BadRequests.REQUESTS_SUCCESS.getText(), TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findAllDoctors(Pageable pageable) throws JsonProcessingException, UnsupportedEncodingException {
        return new ResponseEntity<> ( new Message(viewRepository.findAll(pageable), BadRequests.REQUESTS_SUCCESS.getText(), TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Object> saveDoctor(ViewDoctors doctor) throws IllegalArgumentException, UnsupportedEncodingException, JsonProcessingException {

        if (doctor.getSpeciality_id() <= 0  || doctor.getCode() == null || doctor.getPassword() == null)
            throw new IllegalArgumentException(BadRequests.MISSING_FIELDS.getText());

        if (userRepository.existsByCode(doctor.getCode())){
            return new ResponseEntity<>(new Message("code already exists", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<Role> role = roleService.findByName(RoleTypes.DOCTOR);
        if (role.isEmpty()) {
            return new ResponseEntity<>(new Message("invalid role", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<Speciality> speciality = specialityService.findById((long) doctor.getSpeciality_id());
        if (speciality.isEmpty()) {
            return new ResponseEntity<>(new Message("invalid speciality", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        ResponseEntity resp = personService.save(new Person(doctor.getName(), doctor.getSurname(), doctor.getLastname(),
                doctor.getBirthday(), SexType.valueOf(doctor.getSex()), doctor.getPhone()));
        Message message = (Message) resp.getBody();
        Person newPerson = mapper.readValue( cryptService.decrypt(String.valueOf(message.getResult())), Person.class);
        assert message != null;
        if (!message.getType().equals(TypeResponse.SUCCESS)){
            return resp;
        }
        return userService.saveUserDoctor(new User(doctor.getCode(), doctor.getPassword(), "", newPerson.getId(), role.get(), speciality.get()));
    }

    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {SQLException.class})
    public ResponseEntity<Object> updateDoctor(ViewDoctors doctor) throws IllegalArgumentException {

        if (doctor.getSpeciality_id() <= 0) throw new IllegalArgumentException(BadRequests.MISSING_FIELDS.getText());
        Optional<Role> role = roleService.findByName(RoleTypes.DOCTOR);
        if (role.isEmpty()) return new ResponseEntity<>(new Message("invalid role", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        Optional<Speciality> speciality = specialityService.findById((long) doctor.getSpeciality_id());
        if (speciality.isEmpty()) return new ResponseEntity<>(new Message("invalid speciality", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        Optional<User> user = userService.findById(doctor.getId());
        if (user.isEmpty()){
            return new ResponseEntity<>(new Message(BadRequests.USER_NOT_FOUND.getText(), TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Optional<Person> person = personService.findById(user.get().getPerson().getId());
        if (person.isEmpty()){
            return new ResponseEntity<>(new Message("person not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        ResponseEntity resp = personService.update(new Person(person.get().getId(), doctor.getName(), doctor.getSurname(), doctor.getLastname(),
                doctor.getBirthday(), SexType.valueOf(doctor.getSex()), person.get().getPhone(), null, null));
        Message message = (Message) resp.getBody();
        assert message != null;
        if (!message.getType().equals(TypeResponse.SUCCESS)){
            return resp;
        }
        return userService.updateUserDoctor(new User(user.get().getId(), null, null, null, "", true, null, null, null, null, speciality.get(), null));
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> lockDoctor(Long id) throws IllegalArgumentException {
        return userService.lockUser(id);
    }
}
