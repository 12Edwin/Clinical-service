package utez.edu.mx.backend.access.user.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.access.user.model.UserDto;
import utez.edu.mx.backend.access.user.model.UserRepository;
import utez.edu.mx.backend.security.service.CryptService;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Transactional(readOnly = true)
    public boolean existsUsername (String username) {
        return repository.existsByCode(username);
    }

    public Optional<User> findFirstByCode(String code) {
        return repository.findFirstByCode(code);
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<?> updatePassword (UserDto user){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<?> verifyCode (UserDto user){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<?> saveUserDoctor (User user) throws IllegalArgumentException, JsonProcessingException, UnsupportedEncodingException {
        if (user.getCode() == null || user.getPassword() == null
        ) throw new IllegalArgumentException("missing fields");

        if (repository.existsByCode(user.getCode())){
            return new ResponseEntity<>(new Message("code already exists", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (!repository.existsByRole(user.getRole())){
            return new ResponseEntity<>(new Message("invalid role", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (repository.existsByPerson(user.getPerson())){
            return new ResponseEntity<>(new Message("user with this person already exists", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        user.setPassword(encoder.encode(user.getPassword()));
        User newUser = repository.saveAndFlush(user);
        if (newUser.getCode() == null){
            return new ResponseEntity<>(new Message("Unregistered user", TypeResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Message(newUser, "Registered user", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(value = "transactionManager",rollbackFor = {SQLException.class})
    public ResponseEntity<?> updateUserDoctor (User user) throws IllegalArgumentException {
        if (user.getId() <= 0
        ) throw new IllegalArgumentException("missing fields");

        if (!repository.existsById(user.getId())){
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        boolean updatedUser = repository.updateSpeciality(user.getId(), user.getSpeciality().getId()) >= 1;
        if (!updatedUser){
            return new ResponseEntity<>(new Message("User not updated", TypeResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Message("Updated user", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public User saveInitial (User user){
        return repository.saveAndFlush(user);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<?> lockUser (Long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException("missing fields");
        Optional<User> user = findById(id);
        if (user.isEmpty()){
            return new ResponseEntity<>(new Message("User not found", TypeResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        boolean result = repository.lockUser(id, !user.get().isAvailable()) == 1;
        if (!result){
            return new ResponseEntity<>(new Message("Cannot change user status", TypeResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Message("User status changed", TypeResponse.SUCCESS), HttpStatus.OK);
    }

}
