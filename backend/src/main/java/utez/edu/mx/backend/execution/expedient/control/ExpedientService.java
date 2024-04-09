package utez.edu.mx.backend.execution.expedient.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.base_catalog.disease.model.Disease;
import utez.edu.mx.backend.base_catalog.disease.model.DiseaseRepository;
import utez.edu.mx.backend.base_catalog.disease.model.DtoDisease;
import utez.edu.mx.backend.base_catalog.pathology.model.DtoPathological_record;
import utez.edu.mx.backend.base_catalog.pathology.model.PathologicalRepository;
import utez.edu.mx.backend.base_catalog.pathology.model.Pathological_record;
import utez.edu.mx.backend.base_catalog.person.model.Person;
import utez.edu.mx.backend.base_catalog.person.model.PersonRepository;
import utez.edu.mx.backend.base_catalog.person.model.SexType;
import utez.edu.mx.backend.base_catalog.physicalRecord.model.Physical_record;
import utez.edu.mx.backend.base_catalog.physicalRecord.model.Physical_recordRepository;
import utez.edu.mx.backend.base_catalog.physicalRecord.model.TypeGender;
import utez.edu.mx.backend.execution.expedient.model.*;
import utez.edu.mx.backend.execution.patient.model.Patient;
import utez.edu.mx.backend.execution.patient.model.PatientRepository;
import utez.edu.mx.backend.execution.patient.model.TypeMaritalStatus;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpedientService {

    @Autowired
    private ExpedientRepository repository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private Physical_recordRepository recordRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PathologicalRepository pathologicalRepository;
    @Autowired
    private DiseaseRepository diseaseRepository;
    @Autowired
    private ViewExpedientRepository viewRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll(Pageable pageable) throws JsonProcessingException, UnsupportedEncodingException {
        Page<ViewExpedients> expedients = viewRepository.findAll(pageable);
        Page<DtoExpedient> dtoExpedients = expedients.map(ViewExpedients::cast);
        dtoExpedients.getContent().forEach((expedient) -> {
            Optional<Expedient> exp = repository.findById(expedient.getId());
            expedient.setPathologicalRecords(pathologicalRepository.findAllByExpedient(exp.get()).stream().map(Pathological_record::cast).toList());
            expedient.setDiseases(diseaseRepository.findAllByExpedient(exp.get()).stream().map(Disease::cast).toList());
        });
        return new ResponseEntity<> ( new Message(dtoExpedients, "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<?> findAllByFolio(Pageable pageable, DtoExpedient data) throws JsonProcessingException, UnsupportedEncodingException {
        Page<ViewExpedients> expedients = viewRepository.findAllByFolioIsContainingIgnoreCase(pageable, data.getFolio());
        Page<DtoExpedient> dtoExpedients = expedients.map(ViewExpedients::cast);
        dtoExpedients.getContent().forEach((expedient) -> {
            Optional<Expedient> exp = repository.findById(expedient.getId());
            expedient.setPathologicalRecords(pathologicalRepository.findAllByExpedient(exp.get()).stream().map(Pathological_record::cast).toList());
            expedient.setDiseases(diseaseRepository.findAllByExpedient(exp.get()).stream().map(Disease::cast).toList());
        });
        return new ResponseEntity<> ( new Message(dtoExpedients, "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<?> findAllByEmail(Pageable pageable, DtoExpedient data) throws JsonProcessingException, UnsupportedEncodingException {
        Page<ViewExpedients> expedients = viewRepository.findAllByEmailIsContainingIgnoreCase(pageable, data.getEmail());
        Page<DtoExpedient> dtoExpedients = expedients.map(ViewExpedients::cast);
        dtoExpedients.getContent().forEach((expedient) -> {
            Optional<Expedient> exp = repository.findById(expedient.getId());
            expedient.setPathologicalRecords(pathologicalRepository.findAllByExpedient(exp.get()).stream().map(Pathological_record::cast).toList());
            expedient.setDiseases(diseaseRepository.findAllByExpedient(exp.get()).stream().map(Disease::cast).toList());
        });
        return new ResponseEntity<> ( new Message(dtoExpedients, "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(Long id) throws IllegalArgumentException, JsonProcessingException, UnsupportedEncodingException {
        if (id <= 0) throw new IllegalArgumentException("missing fields");
        Optional<ViewExpedients> optional = viewRepository.findById(id);
        if (optional.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        DtoExpedient expedient = optional.get().cast();
        Optional<Expedient> exp = repository.findById(expedient.getId());
        expedient.setPathologicalRecords(pathologicalRepository.findAllByExpedient(exp.get()).stream().map(Pathological_record::cast).toList());
        expedient.setDiseases(diseaseRepository.findAllByExpedient(exp.get()).stream().map(Disease::cast).toList());
        return new ResponseEntity<>(new Message(expedient, "Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> save(DtoExpedient expedient) throws UnsupportedEncodingException, JsonProcessingException {
        if(expedient.getBirthday() == null || expedient.getAllergies() == null || expedient.getName() == null
            || expedient.getGender() == null || expedient.getCreated_by() == null || expedient.getHeight() <= 0
            || expedient.getOccupation() == null || expedient.getMarital_status() == null
            || expedient.getPhone() == null || expedient.getSex() == null || expedient.getPlace_of_birth() == null
            || expedient.getSurname() == null || expedient.getWeight() <= 0
            || expedient.getPathologicalRecords() == null) throw new IllegalArgumentException();

        Calendar cal = Calendar.getInstance();
        cal.setTime(expedient.getBirthday());
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        if (cal.getTime().after(Calendar.getInstance().getTime())){
            return new ResponseEntity<>(new Message("invalid birthday", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (patientRepository.existsByEmail(expedient.getEmail())){
            return new ResponseEntity<>(new Message("email already registered", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (personRepository.existsByPhone(expedient.getPhone())){
            return new ResponseEntity<>(new Message("phone already registered", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Physical_record record = recordRepository.save(new Physical_record(expedient.getWeight(), expedient.getHeight(), TypeGender.valueOf(expedient.getGender()), expedient.getAllergies()));
        Person person = personRepository.save(new Person(expedient.getName(), expedient.getSurname(), expedient.getLastname(), expedient.getBirthday(), SexType.valueOf(expedient.getSex()), expedient.getPhone()));
        Patient patient = patientRepository.save(new Patient(expedient.getPlace_of_birth(), TypeMaritalStatus.valueOf(expedient.getMarital_status()), expedient.getCreated_by(), expedient.getOccupation(), expedient.getEmail(), person));
        Expedient exp = repository.save(new Expedient(record, patient));
        if (!expedient.getPathologicalRecords().isEmpty()){
            List<Pathological_record> pathologies = expedient.getPathologicalRecords().stream().map(DtoPathological_record::cast).toList();
            pathologicalRepository.saveAll(pathologies.stream().peek(p -> p.setExpedient(exp)).collect(Collectors.toList()));
        }
        if (!expedient.getDiseases().isEmpty()){
            List<Disease> diseases = expedient.getDiseases().stream().map(DtoDisease::cast).toList();
            diseaseRepository.saveAll(diseases.stream().peek(d -> d.setExpedient(exp)).collect(Collectors.toList()));
        }
        if (repository.existsById(exp.getId())){
            return new ResponseEntity<>(new Message(exp,"Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("Unregistered expedient", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional
    public ResponseEntity<?> update(DtoExpedient expedient) throws UnsupportedEncodingException, JsonProcessingException {
        if(expedient.getBirthday() == null || expedient.getAllergies() == null || expedient.getName() == null
                || expedient.getGender() == null || expedient.getCreated_by() == null || expedient.getHeight() <= 0
                || expedient.getOccupation() == null || expedient.getMarital_status() == null || expedient.getDiseases() == null
                || expedient.getPhone() == null || expedient.getSex() == null || expedient.getPlace_of_birth() == null
                || expedient.getSurname() == null || expedient.getWeight() <= 0 || expedient.getPathologicalRecords() == null
                || expedient.getId() <= 0 || expedient.getPhysic_id() <= 0 || expedient.getPatient_id() <= 0
                || expedient.getPerson_id() <= 0) throw new IllegalArgumentException();

        Optional<Expedient> optionalExpedient = repository.findById(expedient.getId());
        if (optionalExpedient.isEmpty()){
            return new ResponseEntity<>(new Message("Not found", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(expedient.getBirthday());
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        if (cal.getTime().after(Calendar.getInstance().getTime())){
            return new ResponseEntity<>(new Message("invalid birthday", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (personRepository.existsByPhoneAndPhoneNot(expedient.getPhone(), optionalExpedient.get().getPatient().getPerson().getPhone())){
            return new ResponseEntity<>(new Message("phone already registered", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (patientRepository.existsByEmailAndEmailNot(expedient.getEmail(), optionalExpedient.get().getPatient().getEmail())){
            return new ResponseEntity<>(new Message("email already registered", TypeResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Physical_record record = recordRepository.saveAndFlush(new Physical_record(expedient.getPhysic_id(), expedient.getWeight(), expedient.getHeight(), TypeGender.valueOf(expedient.getGender()), expedient.getAllergies()));
        Person person = personRepository.saveAndFlush(new Person(expedient.getPerson_id(), expedient.getName(), expedient.getSurname(), expedient.getLastname(), expedient.getBirthday(), SexType.valueOf(expedient.getSex()), expedient.getPhone()));
        Patient patient = patientRepository.saveAndFlush(new Patient(expedient.getPatient_id(), expedient.getPlace_of_birth(), TypeMaritalStatus.valueOf(expedient.getMarital_status()), expedient.getCreated_by(), expedient.getOccupation(), expedient.getEmail(), person));
        if (!expedient.getPathologicalRecords().isEmpty()){
            pathologicalRepository.deletePathological_recordByExpedient(optionalExpedient.get());
            List<Pathological_record> pathologies = expedient.getPathologicalRecords().stream().map(DtoPathological_record::cast).toList();
            pathologicalRepository.saveAllAndFlush(pathologies.stream().peek(p -> p.setExpedient(optionalExpedient.get())).collect(Collectors.toList()));
        }
        if (!expedient.getDiseases().isEmpty()){
            diseaseRepository.deleteDiseasesByExpedient(optionalExpedient.get().getId());
            List<Disease> diseases = expedient.getDiseases().stream().map(DtoDisease::cast).toList();
            diseaseRepository.saveAllAndFlush(diseases.stream().peek(d -> d.setExpedient(optionalExpedient.get())).collect(Collectors.toList()));
        }
        Optional<Expedient> exp = repository.findById(optionalExpedient.get().getId());
        if (exp.isPresent()){
            return new ResponseEntity<>(new Message(exp.get(),"Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("expedient not updated", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
