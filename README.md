**Clinical-Service**

1.  Primeramente crear la base de datos con nombre clinic
2.  Asegurarse de las credenciales en el propperties del back, que sean correspondientes a la de su administrador de postrgres versión 15
3.  Ejecutar el proyecto de spring rectificar el JDK en la versión 17 **NOTA: La versión de spring especificada en el poom.xml es la 2.7.10**
4.  Ejecutar el siguiente extracto sql:

DROP table if exists view_doctors;
Drop table if exists view_expedients;
Drop table if exists view_appoint_patient;

create or replace view view_doctors as select
                us.id, concat(pe.name, ' ', pe.surname, ' ', pe.lastname) as fullname,
                us.available,
                pe.name, pe.surname, pe.lastname, null as password, null as code,
                pe.birthday, pe.phone, pe.sex, s.name as speciality, s.description,
                us.speciality_id
                from users us
                LEFT JOIN roles r on us.role_id = r.id
                LEFT JOIN people pe on us.person_id = pe.id
                LEFT JOIN specialities s on us.speciality_id = s.id
                WHERE us.speciality_id IS NOT NULL AND r.name like 'DOCTOR'
                GROUP BY us.id, fullname, pe.name, pe.surname, pe.lastname,
                         pe.birthday, pe.phone, pe.sex, speciality, s.description,
                         password, code, us.speciality_id, us.available::boolean;


CREATE OR REPLACE VIEW  view_expedients as select
                ex.id, ex.folio,
                pa.id as patient_id, pa.created_by, pa.marital_status, pa.occupation, pa.email, pa.place_of_birth,
                pe.id as person_id, pe.name, pe.surname, COALESCE(pe.lastname, '') as lastname, pe.sex, pe.phone, pe.birthday,
                phy.id as physic_id, phy.allergies, phy.gender, phy.height, phy.weight
                from expedients ex
                LEFT JOIN patients pa on ex.patient_id = pa.id
                LEFT JOIN people pe on pa.person_id = pe.id
                LEFT JOIN physical_records phy on ex.physical_id = phy.id;


CREATE OR REPLACE VIEW view_appoint_patient AS SELECT ap.id, ap.start_hour, ap.end_hour, ap.status,
        pe.id as person_id, concat(pe.name, ' ', pe.surname, ' ', COALESCE(pe.lastname, '')) as fullName,
        pe.birthday, pe.phone, pe.sex, pa.email,
        sp.id as space_id, sp.name as space_name,
        t.id as treatment_id, t.studies_description,
        t.recommendation, t.support_home
                FROM appoints ap
                LEFT JOIN spaces sp on ap.space_id = sp.id
                LEFT JOIN treatments t on ap.treatment_id = t.id
                LEFT JOIN expedients ex on t.expedient_id = ex.id
                LEFT JOIN patients pa on ex.patient_id = pa.id
                LEFT JOIN people pe on pa.person_id = pe.id;

CREATE OR REPLACE FUNCTION generate_noExpedient()
RETURNS TRIGGER AS $$
DECLARE
    next_expedient_code INT;
BEGIN
    SELECT COALESCE(MAX(CAST(SUBSTRING(folio, 4) AS INTEGER)), 0) + 1
    INTO next_expedient_code
    FROM expedients;

    NEW.folio := 'EXP' || LPAD(next_expedient_code::TEXT, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

drop view view_expedients;

CREATE OR REPLACE TRIGGER generate_noExpedient
BEFORE INSERT ON expedients
FOR EACH ROW
EXECUTE PROCEDURE generate_noExpedient();


5.  Ejecutar nuevamente la instancia de backend
