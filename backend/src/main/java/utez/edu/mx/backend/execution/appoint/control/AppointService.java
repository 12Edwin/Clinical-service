package utez.edu.mx.backend.execution.appoint.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.access.user.model.UserRepository;
import utez.edu.mx.backend.base_catalog.space.model.Space;
import utez.edu.mx.backend.base_catalog.space.model.SpaceRepository;
import utez.edu.mx.backend.execution.appoint.model.*;
import utez.edu.mx.backend.execution.expedient.model.Expedient;
import utez.edu.mx.backend.execution.expedient.model.ExpedientRepository;
import utez.edu.mx.backend.execution.patient.model.Patient;
import utez.edu.mx.backend.execution.patient.model.PatientRepository;
import utez.edu.mx.backend.execution.treatment.model.Treatment;
import utez.edu.mx.backend.execution.treatment.model.TreatmentRepository;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;
import utez.edu.mx.backend.utils.service.EmailService;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class AppointService {

    private final AppointRepository repository;
    private final ViewRepositoryAppoint viewRepositoryAppoint;
    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;
    private final TreatmentRepository treatmentRepository;
    private final ExpedientRepository expedientRepository;
    private final PatientRepository patientRepository;
    private final EmailService emailService;

    @Transactional
    public ResponseEntity<Object> findByDate(Appoint appoint) throws UnsupportedEncodingException, JsonProcessingException {
        if (appoint.getStartHour() == null || appoint.getEndHour() == null
        ) throw new IllegalArgumentException();

        return new ResponseEntity<>(new Message(repository.findAllByStartHourBetween(appoint.getStartHour(), appoint.getEndHour()), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Object> findById(Long id, Long id_user) throws UnsupportedEncodingException, JsonProcessingException {
        if (id <= 0) return new ResponseEntity<>(new Message("missing fields", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);

        Optional<User> user = userRepository.findById(id_user);
        if (user.isEmpty()){
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        Optional<Appoint> appoint = repository.findById(id);
        if (appoint.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Optional<Treatment> treatment = treatmentRepository.findById(appoint.get().getTreatment().getId());
        if (treatment.isEmpty()){
            return new ResponseEntity<>(new Message("Treatment not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<Expedient> expedient = expedientRepository.findById(treatment.get().getExpedient().getId());
        if (expedient.isEmpty()){
            return new ResponseEntity<>(new Message("Expedient not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        if (!expedient.get().getPatient().getCreatedBy().getId().equals(id_user)){
            return new ResponseEntity<>(new Message("Unauthorized user", TypeResponse.ERROR), HttpStatus.FORBIDDEN);
        }
        Optional<ViewAppointPatient> result = viewRepositoryAppoint.findById(id);
        if (result.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new Message( result.get(), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Object> save(Appoint appoint, Long id_user) throws UnsupportedEncodingException, JsonProcessingException, IllegalArgumentException {
        if (appoint.getSpace().getId() <= 0 || appoint.getStartHour() == null
            || appoint.getEndHour() == null || appoint.getTreatment().getId() <= 0
            ) throw new IllegalArgumentException();

        Optional<User> user = userRepository.findById(id_user);
        if (user.isEmpty()){
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<Treatment> treatment = treatmentRepository.findById(appoint.getTreatment().getId());
        if (treatment.isEmpty()){
            return new ResponseEntity<>(new Message("Treatment not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<Expedient> expedient = expedientRepository.findById(treatment.get().getExpedient().getId());
        if (expedient.isEmpty()){
            return new ResponseEntity<>(new Message("Expedient not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        if (!expedient.get().getPatient().getCreatedBy().getId().equals(id_user)){
            return new ResponseEntity<>(new Message("Unauthorized user", TypeResponse.ERROR), HttpStatus.FORBIDDEN);
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

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        Date oneMonthFromNow = calendar.getTime();

        if (appoint.getStartHour().after(oneMonthFromNow)) {
            return new ResponseEntity<>(new Message("Cannot be more than one month from now", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
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
        SimpleDateFormat horFormat = new SimpleDateFormat("HH:mm a");
        String start = horFormat.format(appoint.getStartHour());
        String end = horFormat.format(appoint.getEndHour());
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String date = formatter.format(appoint.getStartHour());
        date = date.substring(0, 1).toUpperCase() + date.substring(1).toLowerCase();
        String res = repository.findPatientName(appoint.getId());
        String name = res.split("~")[0];
        String email = res.split("~")[1];
        HtmlString html = new HtmlString(name, date, start, end, appoint.getSpace().getName(), appoint.getSpace().getDescription(), "777-376-0183", Integer.toString(java.time.Year.now().getValue()) );
        ResponseEntity<Object> response = emailService.sendSimpleMessage(email, "Cita médica", html.getHtml());
        if (response.getStatusCode() != HttpStatus.OK){
            return new ResponseEntity<>(new Message("Appoint not sent", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new Message(result, "Appoint registered", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Object> update(Appoint appoint, Long id_user) throws UnsupportedEncodingException, JsonProcessingException {
        if (appoint.getSpace().getId() <= 0 || appoint.getStartHour() == null
                || appoint.getEndHour() == null || appoint.getTreatment().getId() <= 0
                || appoint.getId() <= 0 || appoint.getStatus() == null) throw new IllegalArgumentException();

        Optional<User> user = userRepository.findById(id_user);
        if (user.isEmpty()){
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<Appoint> optionalAppoint = repository.findById(appoint.getId());
        if (optionalAppoint.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (optionalAppoint.get().getStatus() == StatusAppoint.Cancelada || optionalAppoint.get().getStatus() == StatusAppoint.Completada){
            return new ResponseEntity<>(new Message("Cannot update a canceled or completed appointment", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Optional<Treatment> treatment = treatmentRepository.findById(optionalAppoint.get().getTreatment().getId());
        if (treatment.isEmpty()){
            return new ResponseEntity<>(new Message("Treatment not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<Expedient> expedient = expedientRepository.findById(treatment.get().getExpedient().getId());
        if (expedient.isEmpty()){
            return new ResponseEntity<>(new Message("Expedient not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        if (!expedient.get().getPatient().getCreatedBy().getId().equals(id_user)){
            return new ResponseEntity<>(new Message("Unauthorized user", TypeResponse.ERROR), HttpStatus.FORBIDDEN);
        }
        treatment = treatmentRepository.findById(appoint.getTreatment().getId());
        if (treatment.isEmpty()){
            return new ResponseEntity<>(new Message("Treatment not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        expedient = expedientRepository.findById(treatment.get().getExpedient().getId());
        if (expedient.isEmpty()){
            return new ResponseEntity<>(new Message("Expedient not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        if (!expedient.get().getPatient().getCreatedBy().getId().equals(id_user)){
            return new ResponseEntity<>(new Message("Unauthorized user", TypeResponse.ERROR), HttpStatus.FORBIDDEN);
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

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        Date oneMonthFromNow = calendar.getTime();

        if (appoint.getStartHour().after(oneMonthFromNow)) {
            return new ResponseEntity<>(new Message("Cannot be more than one month from now", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<Object> reschedule(Appoint appoint, Long id_user) throws UnsupportedEncodingException, JsonProcessingException {
        if (appoint.getStartHour() == null
                || appoint.getEndHour() == null
                || appoint.getId() <= 0) throw new IllegalArgumentException();

        Optional<User> user = userRepository.findById(id_user);
        if (user.isEmpty()){
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<Appoint> appoint1 = repository.findById(appoint.getId());
        if (appoint1.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (appoint1.get().getStatus() == StatusAppoint.Cancelada || appoint1.get().getStatus() == StatusAppoint.Completada){
            return new ResponseEntity<>(new Message("Cannot update a canceled or completed appointment", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Optional<Treatment> treatment = treatmentRepository.findById(appoint1.get().getTreatment().getId());
        if (treatment.isEmpty()){
            return new ResponseEntity<>(new Message("Treatment not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<Expedient> expedient = expedientRepository.findById(treatment.get().getExpedient().getId());
        if (expedient.isEmpty()){
            return new ResponseEntity<>(new Message("Expedient not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        if (!expedient.get().getPatient().getCreatedBy().getId().equals(id_user)){
            return new ResponseEntity<>(new Message("Unauthorized user", TypeResponse.ERROR), HttpStatus.FORBIDDEN);
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

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        Date oneMonthFromNow = calendar.getTime();

        if (appoint.getStartHour().after(oneMonthFromNow)) {
            return new ResponseEntity<>(new Message("Cannot be more than one month from now", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<Object> complete(Appoint appoint, Long id_user) throws UnsupportedEncodingException, JsonProcessingException {
        if (appoint.getId() <= 0 ) throw new IllegalArgumentException();

        Optional<User> user = userRepository.findById(id_user);
        if (user.isEmpty()){
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<Appoint> optionalAppoint = repository.findById(appoint.getId());
        if (optionalAppoint.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (new Date().before(optionalAppoint.get().getStartHour())) {
            return new ResponseEntity<>(new Message("The appointment has not yet started", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (optionalAppoint.get().getStatus() == StatusAppoint.Cancelada || optionalAppoint.get().getStatus() == StatusAppoint.Completada){
            return new ResponseEntity<>(new Message("Cannot update a canceled or completed appointment", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Optional<Treatment> treatment = treatmentRepository.findById(optionalAppoint.get().getTreatment().getId());
        if (treatment.isEmpty()){
            return new ResponseEntity<>(new Message("Treatment not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<Expedient> expedient = expedientRepository.findById(treatment.get().getExpedient().getId());
        if (expedient.isEmpty()){
            return new ResponseEntity<>(new Message("Expedient not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        if (!expedient.get().getPatient().getCreatedBy().getId().equals(id_user)){
            return new ResponseEntity<>(new Message("Unauthorized user", TypeResponse.ERROR), HttpStatus.FORBIDDEN);
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
    public ResponseEntity<Object> cancel(Appoint appoint, Long id_user) throws UnsupportedEncodingException, JsonProcessingException {
        if (appoint.getId() <= 0) throw new IllegalArgumentException();

        Optional<User> user = userRepository.findById(id_user);
        if (user.isEmpty()){
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<Appoint> optionalAppoint = repository.findById(appoint.getId());
        if (optionalAppoint.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (optionalAppoint.get().getStatus() == StatusAppoint.Cancelada || optionalAppoint.get().getStatus() == StatusAppoint.Completada){
            return new ResponseEntity<>(new Message("Cannot update a canceled or completed appointment", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Optional<Treatment> treatment = treatmentRepository.findById(optionalAppoint.get().getTreatment().getId());
        if (treatment.isEmpty()){
            return new ResponseEntity<>(new Message("Treatment not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<Expedient> expedient = expedientRepository.findById(treatment.get().getExpedient().getId());
        if (expedient.isEmpty()){
            return new ResponseEntity<>(new Message("Expedient not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        if (!expedient.get().getPatient().getCreatedBy().getId().equals(id_user)){
            return new ResponseEntity<>(new Message("Unauthorized user", TypeResponse.ERROR), HttpStatus.FORBIDDEN);
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
    public ResponseEntity<Object> findBySpace(Long id, Long id_user) throws UnsupportedEncodingException, JsonProcessingException {
        if (id <= 0) return new ResponseEntity<>(new Message("missing fields", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);

        Optional<User> user = userRepository.findById(id_user);
        if (user.isEmpty()){
            return new ResponseEntity<>(new Message("User not found", TypeResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        Optional<Space> space = spaceRepository.findById(id);
        if (space.isEmpty()){
            return new ResponseEntity<>(new Message("Space not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        List<Patient> patients = patientRepository.findAllByCreatedBy(user.get());
        List<Treatment> treatments = treatmentRepository.findAllByExpedientIn(patients.stream().map(Patient::getExpedient).toList());
        return new ResponseEntity<>(new Message(repository.findAllBySpaceAndTreatmentIn(space.get(), treatments), "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }
}
