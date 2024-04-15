package utez.edu.mx.backend.access.user.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import utez.edu.mx.backend.access.role.model.Role;
import utez.edu.mx.backend.base_catalog.person.model.Person;
import utez.edu.mx.backend.base_catalog.speciality.model.Speciality;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByCode(String code);
    boolean existsByCode(String code);
    boolean existsByRole(Role role);
    boolean existsByPerson(Person person);

    @Modifying
    @Query(value = "UPDATE users SET speciality_id = :speciality WHERE id = :id")
    int updateSpeciality(@Param("id") Long id, @Param("speciality") Long speciality);

    @Modifying
    @Query(value = "UPDATE users SET available = :status WHERE id = :id")
    int lockUser(@Param("id") Long id, @Param("status") boolean status);

    List<User> findAllBySpeciality(Speciality speciality);

    Optional<User> findByPerson(Person person);
}
