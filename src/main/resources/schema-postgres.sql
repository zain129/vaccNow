--DROP TABLE IF EXISTS vaccination;
--DROP TABLE IF EXISTS dose;
--DROP TABLE IF EXISTS vaccine;
--DROP TABLE IF EXISTS payment;
--DROP TABLE IF EXISTS schedule;
--DROP TABLE IF EXISTS patient;
--DROP TABLE IF EXISTS branch;

CREATE TABLE IF NOT EXISTS branch (
    branch_id integer NOT NULL,
    name character varying NOT NULL,
    open_at time without time zone NOT NULL,
    close_at time without time zone NOT NULL,
    created timestamp without time zone,
    updated timestamp without time zone,
    CONSTRAINT branch_pk PRIMARY KEY (branch_id)
);

CREATE TABLE IF NOT EXISTS vaccine (
	vaccine_id integer NOT NULL,
	name character varying NOT NULL,
	description character varying NOT NULL,
	manufacturer character varying,
	created timestamp without time zone,
    updated timestamp without time zone,
    CONSTRAINT vaccine_pk PRIMARY KEY (vaccine_id)
);

CREATE TABLE IF NOT EXISTS dose (
	dose_id integer NOT NULL,
	branch_id integer NOT NULL,
	vaccine_id integer NOT NULL,
	dosage_quantity integer NOT NULL,
	created timestamp without time zone,
    updated timestamp without time zone,
    CONSTRAINT dose_pk PRIMARY KEY (dose_id),
	CONSTRAINT dose_fk FOREIGN KEY (branch_id) REFERENCES branch (branch_id),
	CONSTRAINT dose_fk1 FOREIGN KEY (vaccine_id) REFERENCES vaccine (vaccine_id)
);

CREATE TABLE IF NOT EXISTS patient (
	patient_id integer NOT NULL,
	name character varying NOT NULL,
	national_id character varying NOT NULL,
	country character varying,
	created timestamp without time zone,
    updated timestamp without time zone,
    CONSTRAINT patient_pk PRIMARY KEY (patient_id)
);

CREATE TABLE IF NOT EXISTS schedule (
	schedule_id integer NOT NULL,
	branch_id integer NOT NULL,
	patient_id integer NOT NULL,
	status boolean NOT NULL,
	start_time time without time zone NOT NULL,
	end_time time without time zone NOT NULL,
	date timestamp without time zone NOT NULL,
	created timestamp without time zone,
    updated timestamp without time zone,
    CONSTRAINT schedule_pk PRIMARY KEY (schedule_id),
	CONSTRAINT schedule_fk FOREIGN KEY (branch_id) REFERENCES branch (branch_id),
	CONSTRAINT schedule_fk1 FOREIGN KEY (patient_id) REFERENCES patient (patient_id)
);

CREATE TABLE IF NOT EXISTS vaccination (
	vaccination_id integer NOT NULL,
	dose_id integer NOT NULL,
	patient_id integer NOT NULL,
	schedule_id integer NOT NULL,
	date timestamp without time zone NOT NULL,
	created timestamp without time zone,
    updated timestamp without time zone,
    CONSTRAINT vaccination_pk PRIMARY KEY (vaccination_id),
	CONSTRAINT vaccination_fk FOREIGN KEY (dose_id) REFERENCES dose (dose_id),
	CONSTRAINT vaccination_fk1 FOREIGN KEY (patient_id) REFERENCES patient (patient_id),
	CONSTRAINT vaccination_fk2 FOREIGN KEY (schedule_id) REFERENCES schedule (schedule_id)
);

CREATE TABLE IF NOT EXISTS payment (
	payment_id integer NOT NULL,
	schedule_id integer NOT NULL,
	mode character varying NOT NULL,
	account_number  character varying NULL,
	date timestamp without time zone NOT NULL,
	created timestamp without time zone,
    updated timestamp without time zone,
    CONSTRAINT payment_pk PRIMARY KEY (payment_id),
	CONSTRAINT payment_fk FOREIGN KEY (schedule_id) REFERENCES schedule (schedule_id)
);
