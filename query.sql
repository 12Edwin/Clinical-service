
create or replace view view_doctors as select
                us.id, concat(pe.name, ' ', pe.surname, ' ', pe.lastname) as fullname,
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
                         password, code, us.speciality_id;


CREATE OR REPLACE VIEW  view_expedients as select
                ex.id, ex.folio,
                pa.id as patient_id, pa.created_by, pa.marital_status, pa.occupation, pa.place_of_birth,
                pe.id as person_id, pe.name, pe.surname, pe.lastname, pe.sex, pe.phone, pe.birthday,
                phy.id as physic_id, phy.allergies, phy.gender, phy.height, phy.weight
                from expedients ex
                LEFT JOIN patients pa on ex.patient_id = pa.id
                LEFT JOIN people pe on pa.person_id = pe.id
                LEFT JOIN physical_records phy on ex.physical_id = phy.id;



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

CREATE OR REPLACE TRIGGER generate_noExpedient
BEFORE INSERT ON expedients
FOR EACH ROW
EXECUTE PROCEDURE generate_noExpedient();