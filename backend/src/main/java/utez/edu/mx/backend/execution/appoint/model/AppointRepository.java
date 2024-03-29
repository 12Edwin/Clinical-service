package utez.edu.mx.backend.execution.appoint.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.base_catalog.schedule.model.Space;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointRepository extends JpaRepository<Appoint, Long> {

    List<Appoint> findAllByStartHourBetween(Date startDate, Date endDate);
    @Query(value = "SELECT id FROM appoints  WHERE space_id = :space AND ((start_hour BETWEEN :date1 AND :date2) OR (end_hour BETWEEN :date1 AND :date2)) AND id NOT IN (:id)")
    List<Long> findBusySpaces(@Param("space") Long space, @Param("date1") Date date1, @Param("date2") Date date2, @Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE appoints SET start_hour = :date1, end_hour = :date2, status = :status, user_id = :user WHERE id = :id")
    int reschedule(@Param("date1") Date date1, @Param("date2") Date date2, @Param("status") StatusAppoint status, @Param("user") Long user, @Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE appoints SET status = :status, user_id = :user WHERE id = :id")
    int changeStatus(@Param("status") StatusAppoint status, @Param("user") Long user, @Param("id") Long id);
}
