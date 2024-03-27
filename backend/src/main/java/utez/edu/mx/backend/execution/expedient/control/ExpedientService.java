package utez.edu.mx.backend.execution.expedient.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.backend.base_catalog.pathology.model.DtoPathological_record;
import utez.edu.mx.backend.base_catalog.pathology.model.PathologicalRepository;
import utez.edu.mx.backend.base_catalog.person.model.Person;
import utez.edu.mx.backend.base_catalog.person.model.PersonRepository;
import utez.edu.mx.backend.base_catalog.person.model.SexType;
import utez.edu.mx.backend.base_catalog.physicalRecord.model.Physical_record;
import utez.edu.mx.backend.base_catalog.physicalRecord.model.Physical_recordRepository;
import utez.edu.mx.backend.base_catalog.physicalRecord.model.TypeGender;
import utez.edu.mx.backend.execution.expedient.model.DtoExpedient;
import utez.edu.mx.backend.execution.expedient.model.Expedient;
import utez.edu.mx.backend.execution.expedient.model.ExpedientRepository;
import utez.edu.mx.backend.execution.patient.model.Patient;
import utez.edu.mx.backend.execution.patient.model.PatientRepository;
import utez.edu.mx.backend.execution.patient.model.TypeMaritalStatus;
import utez.edu.mx.backend.utils.entity.Message;
import utez.edu.mx.backend.utils.entity.TypeResponse;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
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

    @Transactional
    public ResponseEntity<?> save(DtoExpedient expedient) throws UnsupportedEncodingException, JsonProcessingException {
        if(expedient.getBirthday() == null || expedient.getAllergies() == null || expedient.getName() == null
            || expedient.getGender() == null || expedient.getCreated_by() == null || expedient.getHeight() <= 0
            || expedient.getLastname() == null || expedient.getOccupation() == null || expedient.getMarital_status() == null
            || expedient.getPhone() == null || expedient.getSex() == null || expedient.getPlace_of_birth() == null
            || expedient.getSurname() == null || expedient.getWeight() <= 0
            || expedient.getPathologicalRecords() == null) throw new IllegalArgumentException();

        Physical_record record = recordRepository.save(new Physical_record(expedient.getWeight(), expedient.getHeight(), TypeGender.valueOf(expedient.getGender()), expedient.getAllergies()));
        Person person = personRepository.save(new Person(expedient.getName(), expedient.getSurname(), expedient.getLastname(), expedient.getBirthday(), SexType.valueOf(expedient.getSex()), expedient.getPhone()));
        Patient patient = patientRepository.save(new Patient(expedient.getPlace_of_birth(), TypeMaritalStatus.valueOf(expedient.getMarital_status()), expedient.getCreated_by(), expedient.getOccupation(), person));
        Expedient exp = repository.save(new Expedient(record, patient));
        if (!expedient.getPathologicalRecords().isEmpty()){
            pathologicalRepository.saveAll(expedient.getPathologicalRecords().stream().map(DtoPathological_record::cast).collect(Collectors.toList()));
        }
        if (repository.existsById(exp.getId())){
            return new ResponseEntity<>(new Message(exp,"Request successful", TypeResponse.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("Unregistered expedient", TypeResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
