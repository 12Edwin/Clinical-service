package utez.edu.mx.backend.access.user.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import utez.edu.mx.backend.access.role.model.RoleRepository;
import utez.edu.mx.backend.access.role.model.RoleTypes;
import utez.edu.mx.backend.access.sms.control.SmsService;
import utez.edu.mx.backend.access.user.model.DtoSession;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.access.user.model.UserDto;
import utez.edu.mx.backend.access.user.model.UserRepository;
import utez.edu.mx.backend.base_catalog.person.model.Person;
import utez.edu.mx.backend.base_catalog.person.model.PersonRepository;
import utez.edu.mx.backend.base_catalog.person.model.SexType;
import utez.edu.mx.backend.security.service.CryptService;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final SmsService smsService;
    private final PersonRepository personRepository;
    private final PasswordEncoder encoder;
    private final CryptService cryptService;

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
    public ResponseEntity<Object> updatePassword (UserDto user){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> verifyCode (UserDto user){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> saveUserDoctor (User user) throws IllegalArgumentException, JsonProcessingException, UnsupportedEncodingException {
        if (user.getCode() == null || user.getPassword() == null
        ) throw new IllegalArgumentException("missing fields");

        if (repository.existsByCode(user.getCode())){
            return new ResponseEntity<>(new Message("code already exists", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (!roleRepository.existsById(user.getRole().getId())){
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
    public ResponseEntity<Object> updateUserDoctor (User user) throws IllegalArgumentException {
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

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findProfile(Long id, Long id_user) throws UnsupportedEncodingException, JsonProcessingException {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        if (!id.equals(id_user)){
            return new ResponseEntity<>(new Message("Unauthorized user", TypeResponse.WARNING), HttpStatus.FORBIDDEN);
        }
        User user = userOptional.get();
        user.setPassword("");
        user.setExpiration(null);
        user.setToken("");
        user.setRole(null);
        user.setImg("/user/image/" + cryptService.encrypt(user.getId().toString()));
        return new ResponseEntity<>(new Message(user, "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Object> updateProfile (UserDto user, Long id_user) throws IllegalArgumentException {
        Optional<User> userOptional = repository.findById(user.getId());
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(user.getId(), id_user)){
            return new ResponseEntity<>(new Message("Unauthorized user", TypeResponse.WARNING), HttpStatus.FORBIDDEN);
        }
        Optional<Person> optionalPerson = personRepository.findById(userOptional.get().getPerson().getId());
        if (optionalPerson.isEmpty()){
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }


        User updatedUser = userOptional.get();
        updatedUser.setAvailable(true);

        Person person = optionalPerson.get();
        person.setName(user.getPersonProfile().getName());
        person.setSurname(user.getPersonProfile().getSurname());
        person.setLastname(user.getPersonProfile().getLastname() == null ? "" : user.getPersonProfile().getLastname());
        person.setSex(SexType.valueOf(user.getPersonProfile().getSex()));
        person.setPhone(user.getPersonProfile().getPhone());
        person.setBirthday(user.getPersonProfile().getBirthday());

        Calendar cal = Calendar.getInstance();
        cal.setTime(person.getBirthday());
        cal.set((cal.get(Calendar.YEAR) + 18), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        if (cal.getTime().after(Calendar.getInstance().getTime())){
            return new ResponseEntity<>(new Message("invalid birthday", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (personRepository.existsByPhoneAndIdNot(person.getPhone(), userOptional.get().getPerson().getId())){
            return new ResponseEntity<>(new Message("Phone already registered", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        personRepository.saveAndFlush(person);
        repository.saveAndFlush(updatedUser);

        return new ResponseEntity<>(new Message("User updated", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public User saveInitial (User user){
        return repository.saveAndFlush(user);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> lockUser (Long id) throws IllegalArgumentException {
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

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> recover(DtoSession dto) throws UnsupportedEncodingException, JsonProcessingException {
        Optional<Person> person = personRepository.findFirstByPhone(dto.getPhone());
        if (person.isEmpty()) {
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<User> user = repository.findByPerson(person.get());
        if (user.isEmpty()) {
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        User updatedUser = user.get();
        if (!updatedUser.isAvailable() && user.get().getRole().getName() != RoleTypes.ADMIN) {
            return new ResponseEntity<>(new Message("User locked", TypeResponse.WARNING), HttpStatus.LOCKED);
        }
        RandomString tickets = new RandomString(5);
        updatedUser.setToken(tickets.nextString());
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 5);
        date = calendar.getTime();
        updatedUser.setExpiration(date);
        User result = repository.saveAndFlush(updatedUser);
        if (result.getToken() == null) {
            return new ResponseEntity<>(new Message("Code not sent", TypeResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        return smsService.sendSMS(person.get().getPhone(), result.getToken());
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> verifyCode(DtoSession dto) throws UnsupportedEncodingException, JsonProcessingException {
        Optional<Person> person = personRepository.findFirstByPhone(dto.getPhone());
        if (person.isEmpty()) {
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<User> user = repository.findByPerson(person.get());
        if (user.isEmpty()) {
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        if (!user.get().isAvailable() && user.get().getRole().getName() != RoleTypes.ADMIN) {
            return new ResponseEntity<>(new Message("User locked", TypeResponse.WARNING), HttpStatus.LOCKED);
        }
        if (!dto.getToken().equals(user.get().getToken())) {
            return new ResponseEntity<>(new Message("The code has not matched", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        User updatedUser = user.get();
        if (updatedUser.getExpiration().before(new Date())) {
            updatedUser.setToken("");
            updatedUser.setExpiration(null);
            repository.saveAndFlush(updatedUser);
            return new ResponseEntity<>(new Message("Expired code", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        updatedUser.setToken("");
        updatedUser.setExpiration(null);
        updatedUser.setAvailable(true);
        updatedUser.setPassword(encoder.encode(dto.getPassword()));
        repository.saveAndFlush(updatedUser);
        return new ResponseEntity<>(new Message(user, "Password changed", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Object> uploadProfilePicture(Long userId, MultipartFile file) {
        Optional<User> userOptional = repository.findById(userId);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        long maxSize = 2097152; // 2MB in bytes
        if (file.getSize() > maxSize) {
            return new ResponseEntity<>(new Message("File size exceeds the maximum limit", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = "user_" + userId + extension;

        String folderPath = "resources/profiles/";
        Path path = Paths.get(folderPath + newFileName);

        try {
            if (user.getImg() != null && !user.getImg().isEmpty()) {
                Files.deleteIfExists(Path.of(user.getImg()));
            }
            Files.write(path, file.getBytes());
            user.setImg(path.toString());
            repository.saveAndFlush(user);

            return new ResponseEntity<>(new Message("Profile picture uploaded successfully", TypeResponse.SUCCESS), HttpStatus.OK);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new Message("Error while uploading the profile picture", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getProfilePicture(Long userId) {
        Optional<User> userOptional = repository.findById(userId);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();
        String imagePath = user.getImg();
        if (imagePath == null || imagePath.isEmpty()) {
            return new ResponseEntity<>(new Message("Profile picture not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        try {
            Path path = Paths.get(imagePath);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                String contentType = Files.probeContentType(path);
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
            } else {
                return new ResponseEntity<>(new Message("Could not read the file", TypeResponse.ERROR), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Error occurred while accessing the file", TypeResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

}
