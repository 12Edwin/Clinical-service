
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
SELECT * FROM view_doctors;


CREATE OR REPLACE VIEW  view_expedients as select
                ex.id, ex.folio,
                pa.id as patient_id, pa.created_by, pa.marital_status, pa.occupation, pa.email, pa.place_of_birth,
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

drop view view_expedients;

CREATE OR REPLACE TRIGGER generate_noExpedient
BEFORE INSERT ON expedients
FOR EACH ROW
EXECUTE PROCEDURE generate_noExpedient();


CREATE USER clinical_user WITH PASSWORD 'admin';
drop user clincal_user;
GRANT ALL PRIVILEGES ON DATABASE clinic TO clincal_user;

CREATE OR REPLACE FUNCTION grant_permissions_to_clinical_user() RETURNS VOID AS $$
DECLARE
    tbl_name text;
BEGIN
    FOR tbl_name IN (SELECT table_schema || '.' || table_name FROM information_schema.tables WHERE table_schema NOT IN ('pg_catalog', 'information_schema'))
    LOOP
        EXECUTE format('GRANT SELECT, UPDATE, INSERT, DELETE ON %s TO clinical_user', tbl_name);
    END LOOP;
END;
$$ LANGUAGE plpgsql;

SELECT grant_permissions_to_clinical_user();

ALTER USER clinical_user CONNECTION LIMIT 2;

CREATE TABLE bitacora (
    id SERIAL PRIMARY KEY,
    tabla TEXT NOT NULL,
    operacion TEXT NOT NULL,
    usuario TEXT NOT NULL,
    fecha_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    datos_antiguos TEXT,
    datos_nuevos TEXT
);

CREATE OR REPLACE FUNCTION audit() RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'DELETE') THEN
        INSERT INTO bitacora (tabla, operacion, usuario, datos_antiguos)
        VALUES (TG_TABLE_NAME, 'DELETE', current_user, OLD::text);
        RETURN OLD;
    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO bitacora (tabla, operacion, usuario, datos_antiguos, datos_nuevos)
        VALUES (TG_TABLE_NAME, 'UPDATE', current_user, OLD::text, NEW::text);
        RETURN NEW;
    ELSIF (TG_OP = 'INSERT') THEN
        INSERT INTO bitacora (tabla, operacion, usuario, datos_nuevos)
        VALUES (TG_TABLE_NAME, 'INSERT', current_user, NEW::text);
        RETURN NEW;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER roles_audit
AFTER INSERT OR UPDATE OR DELETE ON roles
FOR EACH ROW EXECUTE PROCEDURE audit();

GRANT USAGE, SELECT ON SEQUENCE bitacora_id_seq TO clinical_user;

create database clinic;