package utez.edu.mx.backend.access.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.backend.access.role.model.Role;
import utez.edu.mx.backend.base_catalog.person.model.DtoPerson;
import utez.edu.mx.backend.base_catalog.person.model.Person;
import utez.edu.mx.backend.base_catalog.speciality.model.Speciality;
import utez.edu.mx.backend.execution.appoint.model.Appoint;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotNull(groups = { Modify.class, Restore.class, ChangeStatus.class, Profile.class})
    private Long id;
    @NotBlank(groups = {Modify.class, Register.class, ChangePassword.class, VerifyCode.class, UpdatePassword.class})
    private String code;
    @NotBlank(groups = {Modify.class, Register.class, ChangePassword.class, VerifyCode.class, UpdatePassword.class, Profile.class})
    private String password;
    @NotBlank(groups = {VerifyCode.class, ChangePassword.class})
    private String token;
    private boolean available;
    @NotNull(groups = {Modify.class, Register.class})
    private Person person;
    @NotNull(groups = {Modify.class, Register.class})
    private Appoint appoint;
    @NotNull(groups = {Modify.class, Register.class})
    private Role role;
    @NotNull(groups = {Modify.class, Register.class})
    private Speciality speciality;

    @NotNull(groups = {Profile.class})
    private DtoPerson personProfile;

    public interface Register {}
    public interface Modify{}
    public interface Restore{}
    public interface ChangeStatus{}
    public interface Profile{}

    public interface ChangePassword {}

    public interface VerifyCode {}

    public interface UpdatePassword {}

    public interface Find{}
}
