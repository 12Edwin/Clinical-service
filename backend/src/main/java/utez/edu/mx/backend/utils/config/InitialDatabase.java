package utez.edu.mx.backend.utils.config;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import utez.edu.mx.backend.access.privilege.control.PrivilegeService;
import utez.edu.mx.backend.access.privilege.model.Privilege;
import utez.edu.mx.backend.access.privilege.model.PrivilegeNames;
import utez.edu.mx.backend.access.role.control.RoleService;
import utez.edu.mx.backend.access.role.model.Role;
import utez.edu.mx.backend.access.role.model.RoleTypes;
import utez.edu.mx.backend.access.user.control.UserService;
import utez.edu.mx.backend.access.user.model.User;
import utez.edu.mx.backend.base_catalog.pathology.control.PathologicalService;
import utez.edu.mx.backend.base_catalog.pathology.model.TypePathological;
import utez.edu.mx.backend.base_catalog.person.control.PersonService;
import utez.edu.mx.backend.base_catalog.person.model.Person;
import utez.edu.mx.backend.base_catalog.person.model.SexType;
import utez.edu.mx.backend.base_catalog.service.control.ServiceService;
import utez.edu.mx.backend.base_catalog.service.model.Service;
import utez.edu.mx.backend.base_catalog.space.control.SpaceService;
import utez.edu.mx.backend.base_catalog.space.model.Space;
import utez.edu.mx.backend.base_catalog.speciality.control.SpecialityService;
import utez.edu.mx.backend.base_catalog.speciality.model.Speciality;

import java.text.SimpleDateFormat;
import java.util.Optional;

@Component
@AllArgsConstructor
public class InitialDatabase implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(InitialDatabase.class);

    private final PrivilegeService privilegeService;
    private final RoleService roleService;
    private final UserService userService;
    private final PersonService personService;
    private final ServiceService serviceService;
    private final SpecialityService specialityService;
    private final PathologicalService pathologicalService;
    private final SpaceService spaceService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        Privilege privilege;
        String privilegeAdmin = "[";
        String privilegeDoctor = "[";


        // PRIVILEGES   -- PATHOLOGIES --
        Optional<Privilege> optionalPrivilege = privilegeService.findByName(PrivilegeNames.PATHOLOGIES);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.PATHOLOGIES,
                    "Catálogo para el control de patologías en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }

        privilegeAdmin += new Gson().toJson(privilege) +",";

        // PRIVILEGES   -- DISEASES --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.DISEASES);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.DISEASES,
                    "Catálogo para el control de enfermedades en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeAdmin += new Gson().toJson(privilege) +",";

        // PRIVILEGES   -- DOCTORS --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.DOCTORS);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.DOCTORS,
                    "Catálogo para el control de doctores en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeAdmin += new Gson().toJson(privilege) +",";

        // PRIVILEGES   -- SPECIALITIES --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.SPECIALITIES);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.SPECIALITIES,
                    "Catálogo para el control de especialidades en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeAdmin += new Gson().toJson(privilege) +",";

        // PRIVILEGES   -- SERVICES --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.SERVICES);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.SERVICES,
                    "Catálogo para el control de servicios en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeAdmin += new Gson().toJson(privilege) +",";

        // PRIVILEGES   -- PHYSICAL_RECORDS --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.PHYSICAL_RECORDS);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.PHYSICAL_RECORDS,
                    "Catálogo para el control de composición física en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeAdmin += new Gson().toJson(privilege) +",";

        // PRIVILEGES   -- SPACES --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.SPACES);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.SPACES,
                    "Catálogo para el control de espacios de atención a pacientes en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeAdmin += new Gson().toJson(privilege) +",";

        // PRIVILEGES   -- EXPEDIENT --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.EXPEDIENTS);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.EXPEDIENTS,
                    "Catálogo para el control de expedientes en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeDoctor += new Gson().toJson(privilege) +",";

        // PRIVILEGES   -- TREATMENT --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.TREATMENTS);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.TREATMENTS,
                    "Catálogo para el control de tratamientos en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeDoctor += new Gson().toJson(privilege) +",";

        // PRIVILEGES   -- PATIENTS --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.PATIENTS);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.PATIENTS,
                    "Catálogo para el control de pacientes en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeDoctor += new Gson().toJson(privilege) +",";

        // PRIVILEGES   -- APPOINTS --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.APPOINTS);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.APPOINTS,
                    "Catálogo para el control de tratamientos en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeDoctor += new Gson().toJson(privilege) +"]";

        // PRIVILEGES   -- USERS --
        optionalPrivilege = privilegeService.findByName(PrivilegeNames.USERS);
        if (optionalPrivilege.isEmpty()) {
            privilege = new Privilege(PrivilegeNames.USERS,
                    "Catálogo para el control de cuentas en el sistema");
            privilege = privilegeService.saveInitial(privilege);
        } else {
            privilege = optionalPrivilege.get();
        }
        privilegeAdmin += new Gson().toJson(privilege) +"]";

        // ROLES    -- ADMIN --
        Optional<Role> optionalRoleAdmin = roleService.findByName(RoleTypes.ADMIN);
        String finalPrivilegeAdmin = privilegeAdmin;
        Role roleAdmin;
        if (optionalRoleAdmin.isEmpty()){
            roleAdmin = roleService.saveInitial(new Role(RoleTypes.ADMIN, "Administrador", finalPrivilegeAdmin));
        }else {
            roleAdmin = roleService.updatePrivileges(optionalRoleAdmin.get(), finalPrivilegeAdmin);
        }

        // ROLES    -- DOCTOR --
        Optional<Role> optionalRoleDoctor = roleService.findByName(RoleTypes.DOCTOR);
        String finalPrivilegeDoctor = privilegeDoctor;
        if (optionalRoleDoctor.isEmpty()) {
            roleService.saveInitial(new Role(RoleTypes.DOCTOR, "Doctor", finalPrivilegeDoctor));
        } else {
            roleService.updatePrivileges(optionalRoleDoctor.get(), finalPrivilegeDoctor);
        }

        // USERS    -- ADMIN --
        if (!userService.existsUsername("6666666666")){
            Person person;
            Optional<Person> optionalPerson = personService.findByName("root");
            if (optionalPerson.isPresent()){
                person = optionalPerson.get();
            }else {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                person = new Person("Administrador", "root", "root", format.parse("2000-01-01") , SexType.Masculino, "7773760183");
                person = personService.saveInitial(person);
            }
            User user = new User("6666666666", passwordEncoder.encode("En123456."), "", person.getId(), roleAdmin, null);
            userService.saveInitial(user);
        }

        // PATHOLOGY    -- CÁNCER --
        Optional<TypePathological> optionalTypePathological = pathologicalService.findFirstByName("Cáncer");
        if (optionalTypePathological.isEmpty()){
            pathologicalService.saveInitial(new TypePathological("Cáncer", "Tienes cáncer Andy"));
        }

        // PATHOLOGY    -- DIABETES --
        optionalTypePathological = pathologicalService.findFirstByName("Diabetes");
        if (optionalTypePathological.isEmpty()){
            pathologicalService.saveInitial(new TypePathological("Diabetes", "Enfermedad metabólica caracterizada por eliminación excesiva de orina, adelgazamiento, sed intensa y otros trastornos generales."));
        }

        // PATHOLOGY    -- Asma --
        optionalTypePathological = pathologicalService.findFirstByName("Asma");
        if (optionalTypePathological.isEmpty()){
            pathologicalService.saveInitial(new TypePathological("Asma", "Enfermedad de los bronquios, caracterizada por accesos ordinariamente nocturnos e infebriles, con respiración difícil y anhelante, tos, expectoración escasa y espumosa, y silbidos respiratorios."));
        }

        // PATHOLOGY    -- Artritis --
        optionalTypePathological = pathologicalService.findFirstByName("Artritis");
        if (optionalTypePathological.isEmpty()){
            pathologicalService.saveInitial(new TypePathological("Artritis", "Inflamación de las articulaciones."));
        }

        // SPECIALITY    -- Cirugía Ortopédica --
        Optional<Speciality> optionalSpeciality = specialityService.findFirsByName("Cirugía Ortopédica");
        Speciality speciality1 = optionalSpeciality.orElseGet(() -> specialityService.saveInitial(new Speciality("Cirugía Ortopédica", "Cirugía que se refiere a desórdenes del aparato locomotor")));

        // SPECIALITY    -- Cardiología --
        optionalSpeciality = specialityService.findFirsByName("Cardiología");
        Speciality speciality2 = optionalSpeciality.orElseGet(() -> specialityService.saveInitial(new Speciality("Cardiología", "Estudio del corazón y de sus funciones y enfermedades.")));

        // SPECIALITY    -- Ginecología y Obstetricia --
        optionalSpeciality = specialityService.findFirsByName("Ginecología y Obstetricia");
        Speciality speciality3 = optionalSpeciality.orElseGet(() -> specialityService.saveInitial(new Speciality("Ginecología y Obstetricia", "Se especializa en la atención de las mujeres durante el embarazo y el parto.")));

        // SERVICE  -- Tratamiento de fracturas y lesiones óseas --
        Optional<Service> optionalService = serviceService.findFirstByName("Tratamiento de fracturas y lesiones óseas");
        if (optionalService.isEmpty()){
            serviceService.saveInitial(new Service("Tratamiento de fracturas y lesiones óseas", "", 200, speciality1));
        }

        // SERVICE  -- Cirugía de reemplazo articular --
        optionalService = serviceService.findFirstByName("Cirugía de reemplazo articular");
        if (optionalService.isEmpty()){
            serviceService.saveInitial(new Service("Cirugía de reemplazo articular", "", 2000, speciality1));
        }

        // SERVICE  -- Tratamiento de lesiones deportivas --
        optionalService = serviceService.findFirstByName("Tratamiento de lesiones deportivas");
        if (optionalService.isEmpty()){
            serviceService.saveInitial(new Service("Tratamiento de lesiones deportivas", "", 500, speciality1));
        }

        // SERVICE  -- Evaluación y tratamiento de enfermedades cardíacas --
        optionalService = serviceService.findFirstByName("Evaluación y tratamiento de enfermedades cardíacas");
        if (optionalService.isEmpty()){
            serviceService.saveInitial(new Service("Evaluación y tratamiento de enfermedades cardíacas", "", 700, speciality2));
        }

        // SERVICE  -- Manejo de factores de riesgo cardiovascular --
        optionalService = serviceService.findFirstByName("Manejo de factores de riesgo cardiovascular");
        if (optionalService.isEmpty()){
            serviceService.saveInitial(new Service("Manejo de factores de riesgo cardiovascular", "", 1000, speciality2));
        }

        // SERVICE  -- Intervenciones invasivas, como cateterismos cardíacos y angioplastias. --
        optionalService = serviceService.findFirstByName("Intervenciones invasivas, como cateterismos cardíacos y angioplastias.");
        if (optionalService.isEmpty()){
            serviceService.saveInitial(new Service("Intervenciones invasivas, como cateterismos cardíacos y angioplastias.", "", 1000, speciality2));
        }

        // SERVICE  -- Atención prenatal --
        optionalService = serviceService.findFirstByName("Atención prenatal");
        if (optionalService.isEmpty()){
            serviceService.saveInitial(new Service("Atención prenatal", "", 600, speciality3));
        }

        // SERVICE  -- Atención durante el parto y el postparto --
        optionalService = serviceService.findFirstByName("Atención durante el parto y el postparto");
        if (optionalService.isEmpty()){
            serviceService.saveInitial(new Service("Atención durante el parto y el postparto", "", 900, speciality3));
        }

        // SERVICE  -- Evaluación y tratamiento de problemas ginecológicos --
        optionalService = serviceService.findFirstByName("Evaluación y tratamiento de problemas ginecológicos");
        if (optionalService.isEmpty()){
            serviceService.saveInitial(new Service("Evaluación y tratamiento de problemas ginecológicos", "", 100, speciality3));
        }

        // SPACE  -- Urgencias 1 --
        Optional<Space> optionalSpace = spaceService.findFirstByName("Urgencias 1");
        if (optionalSpace.isEmpty()){
            spaceService.saveInitial(new Space("Urgencias 1", "Segundo piso, puerta 3"));
        }

        // SPACE  -- Sala de cardiología --
        optionalSpace = spaceService.findFirstByName("Sala de cardiología");
        if (optionalSpace.isEmpty()){
            spaceService.saveInitial(new Space("Sala de cardiología", "Segundo piso, puerta 6"));
        }

        // SPACE  -- Consultorio 1 --
        optionalSpace = spaceService.findFirstByName("Consultorio 1");
        if (optionalSpace.isEmpty()){
            spaceService.saveInitial(new Space("Consultorio 1", "Primer piso, puerta 1"));
        }
        logger.info("Data pre-registered");
    }
}
