package utez.edu.mx.backend.utils.config;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import utez.edu.mx.backend.access.privilege.control.PrivilegeService;
import utez.edu.mx.backend.access.privilege.model.Privilege;
import utez.edu.mx.backend.access.privilege.model.PrivilegeNames;
import utez.edu.mx.backend.access.role.control.RoleService;
import utez.edu.mx.backend.access.role.model.Role;
import utez.edu.mx.backend.access.user.control.UserService;
import utez.edu.mx.backend.base_catalog.disease.control.DiseaseService;
import utez.edu.mx.backend.base_catalog.pathology.control.PathologyService;
import utez.edu.mx.backend.base_catalog.service.control.ServiceService;
import utez.edu.mx.backend.base_catalog.speciality.control.SpecialityService;

import java.util.Optional;

@Component
@AllArgsConstructor
public class InitialDatabase implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(InitialDatabase.class);

    private final PrivilegeService privilegeService;
    private final RoleService roleService;
    private final UserService userService;
    private final ServiceService serviceService;
    private final SpecialityService specialityService;
    private final DiseaseService diseaseService;
    private final PathologyService pathologyService;
    @Override
    public void run(String... args) throws Exception {
        Privilege privilege;
        StringBuilder privilegeAdmin = new StringBuilder("[");
        StringBuilder privilegeDoctor = new StringBuilder("[");

        // PRIVILEGES   -- PATHOLOGIES --
        Optional<Privilege> optionalPrivilege = privilegeService.findByName(PrivilegeNames.PATHOLOGIES);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.PATHOLOGIES,
                    "Catálogo para el control de patologías en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeAdmin.append(privilege.toString()).append(",");

        // PRIVILEGES   -- DISEASES --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.DISEASES);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.DISEASES,
                    "Catálogo para el control de enfermedades en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeAdmin.append(privilege.toString()).append(",");

        // PRIVILEGES   -- DOCTORS --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.DOCTORS);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.DOCTORS,
                    "Catálogo para el control de doctores en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeAdmin.append(privilege.toString()).append(",");

        // PRIVILEGES   -- SPECIALITIES --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.SPECIALITIES);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.SPECIALITIES,
                    "Catálogo para el control de especialidades en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeAdmin.append(privilege.toString()).append(",");

        // PRIVILEGES   -- SERVICES --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.SERVICES);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.SERVICES,
                    "Catálogo para el control de servicios en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeAdmin.append(privilege.toString()).append(",");

        // PRIVILEGES   -- SERVICES --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.SERVICES);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.SERVICES,
                    "Catálogo para el control de servicios en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeAdmin.append(privilege.toString()).append(",");

        // PRIVILEGES   -- EXPEDIENT --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.EXPEDIENT);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.EXPEDIENT,
                    "Catálogo para el control de expedientes en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeDoctor.append(privilege.toString()).append(",");

        // PRIVILEGES   -- TREATMENT --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.TREATMENT);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.TREATMENT,
                    "Catálogo para el control de tratamientos en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeDoctor.append(privilege.toString()).append(",");

        // PRIVILEGES   -- APPOINTS --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.APPOINTS);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.APPOINTS,
                    "Catálogo para el control de tratamientos en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeDoctor.append(privilege.toString()).append("]");

        // PRIVILEGES   -- ACCOUNT --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.USER);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.USER,
                    "Catálogo para el control de cuentas en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeAdmin.append(privilege.toString()).append("]");
    }
}
