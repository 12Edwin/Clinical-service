package utez.edu.mx.backend.execution.doctor.control;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import utez.edu.mx.backend.execution.doctor.model.ViewDoctors;
import utez.edu.mx.backend.execution.doctor.model.DoctorRepository;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class DoctorService {

    @Autowired
    private final DoctorRepository viewRepository;
    @Autowired
    private final PersonService personService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleService roleService;
    @Autowired
    private final SpecialityService specialityService;
    @Autowired
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public ResponseEntity<?> findDoctor(Long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException("missing fields");
        Optional<ViewDoctors> doctor = viewRepository.findById(id);
        if (doctor.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(doctor.get(), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findAllDoctors(Pageable pageable){
        return new ResponseEntity<> ( new Message(viewRepository.findAll(pageable), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> saveDoctor(ViewDoctors doctor) throws IllegalArgumentException {

        if (doctor.getSpeciality_id() <= 0  || doctor.getCode() == null || doctor.getPassword() == null)
            throw new IllegalArgumentException("missing fields");

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
        assert message != null;
        if (!message.getType().equals(TypeResponse.SUCCESS)){
            return resp;
        }
        return userService.saveUserDoctor(new User(doctor.getCode(), doctor.getPassword(), "", ((Person) message.getResult()).getId(), role.get(), speciality.get()));
    }

    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {SQLException.class})
    public ResponseEntity<?> updateDoctor(ViewDoctors doctor) throws IllegalArgumentException {

        if (doctor.getSpeciality_id() <= 0) throw new IllegalArgumentException("missing fields");
        Optional<Role> role = roleService.findByName(RoleTypes.DOCTOR);
        if (role.isEmpty()) return new ResponseEntity<>(new Message("invalid role", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        Optional<Speciality> speciality = specialityService.findById((long) doctor.getSpeciality_id());
        if (speciality.isEmpty()) return new ResponseEntity<>(new Message("invalid speciality", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        Optional<User> user = userService.findById(doctor.getId());
        if (user.isEmpty()){
            return new ResponseEntity<>(new Message("user not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Optional<Person> person = personService.findById(user.get().getPerson().getId());
        if (person.isEmpty()){
            return new ResponseEntity<>(new Message("person not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        ResponseEntity resp = personService.update(new Person(person.get().getId(), doctor.getName(), doctor.getSurname(), doctor.getLastname(),
                doctor.getBirthday(), SexType.valueOf(doctor.getSex()), null, null, null));
        Message message = (Message) resp.getBody();
        assert message != null;
        if (!message.getType().equals(TypeResponse.SUCCESS)){
            return resp;
        }
        return userService.updateUserDoctor(new User(user.get().getId(), null, null, "", true, null, null, null, speciality.get()));
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<?> lockDoctor(Long id) throws IllegalArgumentException {
        return userService.lockUser(id);
    }
}
