package utez.edu.mx.backend.execution.appoint.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.access.user.model.UserRepository;
import utez.edu.mx.backend.base_catalog.space.model.Space;
import utez.edu.mx.backend.base_catalog.space.model.SpaceRepository;
import utez.edu.mx.backend.execution.appoint.model.Appoint;
import utez.edu.mx.backend.execution.appoint.model.AppointRepository;
import utez.edu.mx.backend.execution.appoint.model.StatusAppoint;
import utez.edu.mx.backend.execution.treatment.model.Treatment;
import utez.edu.mx.backend.execution.treatment.model.TreatmentRepository;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AppointService {

    @Autowired
    private final AppointRepository repository;
    @Autowired
    private final SpaceRepository spaceRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final TreatmentRepository treatmentRepository;

    @Transactional
    public ResponseEntity<?> findByDate(Appoint appoint) throws UnsupportedEncodingException, JsonProcessingException {
        if (appoint.getStartHour() == null || appoint.getEndHour() == null
        ) throw new IllegalArgumentException();

        return new ResponseEntity<>(new Message(repository.findAllByStartHourBetween(appoint.getStartHour(), appoint.getEndHour()), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> save(Appoint appoint) throws UnsupportedEncodingException, JsonProcessingException, IllegalArgumentException {
        if (appoint.getSpace().getId() <= 0 || appoint.getStartHour() == null
            || appoint.getEndHour() == null || appoint.getTreatment().getId() <= 0
            ) throw new IllegalArgumentException();

        Optional<Treatment> treatment = treatmentRepository.findById(appoint.getTreatment().getId());
        if (treatment.isEmpty()){
            return new ResponseEntity<>(new Message("Treatment not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<Space> space = spaceRepository.findById(appoint.getSpace().getId());
        if (space.isEmpty()){
            return new ResponseEntity<>(new Message("Space not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        long millis = Math.abs(appoint.getEndHour().getTime() - appoint.getStartHour().getTime());
        long hours = millis / (60 * 60 * 1000);
        if (hours > 5){
            return new ResponseEntity<>(new Message("Maximum 5 hours", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (hours < 1){
            return new ResponseEntity<>(new Message("Minimum 1 hour", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (appoint.getStartHour().before(new Date()) || appoint.getEndHour().before(new Date())){
            return new ResponseEntity<>(new Message("Invalid schedule", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (appoint.getStartHour().after(appoint.getEndHour())){
            return new ResponseEntity<>(new Message("Invalid schedule", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }


        List<Long> busySpaces = repository.findBusySpaces(space.get().getId(), appoint.getStartHour(), appoint.getEndHour(), 0L);
        if (!busySpaces.isEmpty()){
            return new ResponseEntity<>(new Message("The space is busy in this schedule", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        appoint.setTreatment(treatment.get());
        appoint.setSpace(space.get());
        appoint.setStatus(StatusAppoint.Pendiente);
        appoint.setUser(null);
        Appoint result = repository.save(appoint);

        if (!repository.existsById(result.getId())){
            return new ResponseEntity<>(new Message("Unregistered appoint", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message(result, "Appoint registered", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> update(Appoint appoint) throws UnsupportedEncodingException, JsonProcessingException {
        if (appoint.getSpace().getId() <= 0 || appoint.getStartHour() == null || appoint.getUser().getId() <= 0
                || appoint.getEndHour() == null || appoint.getTreatment().getId() <= 0
                || appoint.getId() <= 0 || appoint.getStatus() == null) throw new IllegalArgumentException();

        if (!repository.existsById(appoint.getId())){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Optional<Treatment> treatment = treatmentRepository.findById(appoint.getTreatment().getId());
        if (treatment.isEmpty()){
            return new ResponseEntity<>(new Message("Treatment not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<Space> space = spaceRepository.findById(appoint.getSpace().getId());
        if (space.isEmpty()){
            return new ResponseEntity<>(new Message("Space not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<User> user = userRepository.findById(appoint.getUser().getId());
        if (user.isEmpty()){
            return new ResponseEntity<>(new Message("Doctor not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        long millis = Math.abs(appoint.getEndHour().getTime() - appoint.getStartHour().getTime());
        long hours = millis / (60 * 60 * 1000);
        if (hours > 5){
            return new ResponseEntity<>(new Message("Maximum 5 hours", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (hours < 1){
            return new ResponseEntity<>(new Message("Minimum 1 hour", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (appoint.getStartHour().before(new Date()) || appoint.getEndHour().before(new Date())){
            return new ResponseEntity<>(new Message("Invalid schedule", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (appoint.getStartHour().after(appoint.getEndHour())){
            return new ResponseEntity<>(new Message("Invalid schedule", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }


        List<Long> busySpaces = repository.findBusySpaces(space.get().getId(), appoint.getStartHour(), appoint.getEndHour(), appoint.getId());
        if (!busySpaces.isEmpty()){
            return new ResponseEntity<>(new Message("The space is busy in this schedule", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        appoint.setTreatment(treatment.get());
        appoint.setSpace(space.get());
        appoint.setUser(user.get());
        Appoint result = repository.saveAndFlush(appoint);

        if (!repository.existsById(result.getId())){
            return new ResponseEntity<>(new Message("Appoint not updated", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message(result, "Appoint updated", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> reschedule(Appoint appoint) throws UnsupportedEncodingException, JsonProcessingException {
        if (appoint.getStartHour() == null
                || appoint.getEndHour() == null
                || appoint.getId() <= 0) throw new IllegalArgumentException();

        Optional<Appoint> appoint1 = repository.findById(appoint.getId());
        if (appoint1.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<User> user = userRepository.findById(appoint.getUser().getId());
        if (user.isEmpty()){
            return new ResponseEntity<>(new Message("Doctor not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        long millis = Math.abs(appoint.getEndHour().getTime() - appoint.getStartHour().getTime());
        long hours = millis / (60 * 60 * 1000);
        if (hours > 5){
            return new ResponseEntity<>(new Message("Maximum 5 hours", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (hours < 1){
            return new ResponseEntity<>(new Message("Minimum 1 hour", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (appoint.getStartHour().before(new Date()) || appoint.getEndHour().before(new Date())){
            return new ResponseEntity<>(new Message("Invalid schedule", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (appoint.getStartHour().after(appoint.getEndHour())){
            return new ResponseEntity<>(new Message("Invalid schedule", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        List<Long> busySpaces = repository.findBusySpaces(appoint1.get().getSpace().getId(), appoint.getStartHour(), appoint.getEndHour(), appoint.getId());
        if (!busySpaces.isEmpty()){
            return new ResponseEntity<>(new Message("The space is busy in this schedule", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        appoint.setStatus(StatusAppoint.Reprogramada);
        appoint.setUser(user.get());
        boolean result = repository.reschedule(appoint.getStartHour(), appoint.getEndHour(), appoint.getStatus(), appoint.getUser().getId(), appoint.getId()) == 1;

        if (!result){
            return new ResponseEntity<>(new Message("Appoint not rescheduled", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message( "Appoint rescheduled", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> complete(Appoint appoint) throws UnsupportedEncodingException, JsonProcessingException {
        if (appoint.getId() <= 0 || appoint.getUser().getId() <= 0) throw new IllegalArgumentException();

        Optional<Appoint> optionalAppoint = repository.findById(appoint.getId());
        if (optionalAppoint.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<User> user = userRepository.findById(appoint.getUser().getId());
        if (user.isEmpty()){
            return new ResponseEntity<>(new Message("Doctor not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }


        optionalAppoint.get().setStatus(StatusAppoint.Completada);
        optionalAppoint.get().setUser(user.get());
        boolean result = repository.changeStatus(optionalAppoint.get().getStatus(), optionalAppoint.get().getUser().getId(), optionalAppoint.get().getId()) == 1;

        if (!result){
            return new ResponseEntity<>(new Message("Appoint not completed", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message( "Appoint completed", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> cancel(Appoint appoint) throws UnsupportedEncodingException, JsonProcessingException {
        if (appoint.getId() <= 0 || appoint.getUser().getId() <= 0) throw new IllegalArgumentException();

        Optional<Appoint> optionalAppoint = repository.findById(appoint.getId());
        if (optionalAppoint.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<User> user = userRepository.findById(appoint.getUser().getId());
        if (user.isEmpty()){
            return new ResponseEntity<>(new Message("Doctor not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }


        optionalAppoint.get().setStatus(StatusAppoint.Cancelada);
        optionalAppoint.get().setUser(user.get());
        boolean result = repository.changeStatus(optionalAppoint.get().getStatus(), optionalAppoint.get().getUser().getId(), optionalAppoint.get().getId()) == 1;

        if (!result){
            return new ResponseEntity<>(new Message("Appoint not canceled", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message( "Appoint canceled", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> findBySpace(Long id) throws UnsupportedEncodingException, JsonProcessingException {
        if (id <= 0) return new ResponseEntity<>(new Message("missing fields", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);

        Optional<Space> space = spaceRepository.findById(id);
        if (space.isEmpty()){
            return new ResponseEntity<>(new Message("Space not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Message(repository.findAllBySpace(space.get()), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }
}
