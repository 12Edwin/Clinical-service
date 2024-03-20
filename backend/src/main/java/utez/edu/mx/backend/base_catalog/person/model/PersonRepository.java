package utez.edu.mx.backend.base_catalog.person.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utez.edu.mx.backend.access.role.model.Role;

import java.util.Date;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByName(String name);
    boolean existsByPhone(String phone);
    @Modifying
    @Query(value = "UPDATE people SET name = :name, surname = :surname," +
                    "  lastname = :lastname, birthday = :birthday," +
                    "  sex = :sex WHERE id = :id")
    int updateDoctor(
            @Param("id") Long id, @Param("name") String name,
            @Param("surname") String surname, @Param("lastname") String lastname,
            @Param("birthday") Date birthday, @Param("sex") SexType sex
            );
}
