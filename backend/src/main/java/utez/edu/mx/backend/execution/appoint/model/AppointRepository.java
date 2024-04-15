package utez.edu.mx.backend.execution.appoint.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utez.edu.mx.backend.base_catalog.space.model.Space;
import utez.edu.mx.backend.execution.treatment.model.Treatment;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointRepository extends JpaRepository<Appoint, Long> {

    List<Appoint> findAllByStartHourBetween(Date startDate, Date endDate);
    @Query(value = "SELECT id FROM appoints  WHERE space_id = :space AND (((:date1 > start_hour  AND :date1 < end_hour) OR (:date2 > start_hour AND :date2 < end_hour)) OR\n" +
            "                                ((start_hour > :date1 AND start_hour < :date2) OR (end_hour > :date1 AND end_hour < :date2))  OR\n" +
            "                                (:date1 = start_hour AND :date2 = end_hour)) AND id NOT IN (:id) AND status not like 'Cancelada'")
    List<Long> findBusySpaces(@Param("space") Long space, @Param("date1") Date date1, @Param("date2") Date date2, @Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE appoints SET start_hour = :date1, end_hour = :date2, status = :status, user_id = :user WHERE id = :id")
    int reschedule(@Param("date1") Date date1, @Param("date2") Date date2, @Param("status") StatusAppoint status, @Param("user") Long user, @Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE appoints SET status = :status, user_id = :user WHERE id = :id")
    int changeStatus(@Param("status") StatusAppoint status, @Param("user") Long user, @Param("id") Long id);

    List<Appoint> findAllBySpaceAndTreatmentIn(Space space, List<Treatment> treatments);

    @Query(value = "SELECT concat(p.name, ' ', p.surname,'~', pa.email) as name  FROM appoints\n" +
            "LEFT JOIN treatments on appoints.treatment_id = treatments.id\n" +
            "LEFT JOIN expedients on treatments.expedient_id = expedients.id\n" +
            "LEFT JOIN patients pa on expedients.patient_id = pa.id\n" +
            "LEFT JOIN people p on pa.person_id = p.id\n" +
            "where appoints.id = :id LIMIT 1", nativeQuery = true)
    String findPatientName(@Param("id") Long id);

    @Query(value = "SELECT concat(pe.name, ' ', pe.surname, ' ', COALESCE(pe.lastname, '')) as fullName,\n" +
            "        pe.id as person_id, pe.birthday, pe.phone, pe.sex, pa.email, sp.id as space_id,\n" +
            "        sp.name as space_name, ap.id, ap.start_hour,\n" +
            "        ap.end_hour, ap.status, t.id as treatment_id, t.studies_description,\n" +
            "        t.recommendation, t.support_home\n" +
            "                FROM appoints ap\n" +
            "                LEFT JOIN spaces sp on ap.space_id = sp.id\n" +
            "                LEFT JOIN treatments t on ap.treatment_id = t.id\n" +
            "                LEFT JOIN expedients ex on t.expedient_id = ex.id\n" +
            "                LEFT JOIN patients pa on ex.patient_id = pa.id\n" +
            "                LEFT JOIN people pe on pa.person_id = pe.id\n" +
            "                WHERE ap.id = :id", nativeQuery = true)
    Optional<ViewAppointPatient> findFullAppointById(@Param("id") Long id);
}
