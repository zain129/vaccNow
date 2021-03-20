-- branch data
INSERT INTO branch (branch_id, name, open_at, close_at, created, updated) VALUES (1, 'First Branch', '07:00:00', '21:00:00', current_timestamp, null);
INSERT INTO branch (branch_id, name, open_at, close_at, created, updated) VALUES (2, 'Second Branch', '08:00:00', '22:00:00', current_timestamp, null);
INSERT INTO branch (branch_id, name, open_at, close_at, created, updated) VALUES (3, 'Third Branch', '09:00:00', '20:00:00', current_timestamp, null);
INSERT INTO branch (branch_id, name, open_at, close_at, created, updated) VALUES (4, 'Fourth Branch', '11:00:00', '18:00:00', current_timestamp, null);
INSERT INTO branch (branch_id, name, open_at, close_at, created, updated) VALUES (5, 'Fifith Branch', '10:00:00', '19:00:00', current_timestamp, null);

-- patient data
INSERT INTO patient (patient_id, name, national_id, country, created, updated) VALUES (1, 'Abdullah', '123', 'Egypt', current_timestamp, null);
INSERT INTO patient (patient_id, name, national_id, country, created, updated) VALUES (2, 'Abdur Rehman', '234', 'KSA', current_timestamp, null);
INSERT INTO patient (patient_id, name, national_id, country, created, updated) VALUES (3, 'Qasim', '345', 'UAE', current_timestamp, null);
INSERT INTO patient (patient_id, name, national_id, country, created, updated) VALUES (4, 'Joe', '456', 'RSA', current_timestamp, null);
INSERT INTO patient (patient_id, name, national_id, country, created, updated) VALUES (5, 'Mike', '567', 'USA', current_timestamp, null);
INSERT INTO patient (patient_id, name, national_id, country, created, updated) VALUES (6, 'Murray', '678', 'UK', current_timestamp, null);
INSERT INTO patient (patient_id, name, national_id, country, created, updated) VALUES (7, 'Sujit', '789', 'India', current_timestamp, null);

-- vaccine data
INSERT INTO vaccine (vaccine_id, name, description, manufacturer, created, updated) VALUES (1, 'covid-19 vacc', 'vaccince for covid-19', 'Pfizer', current_timestamp, null);
INSERT INTO vaccine (vaccine_id, name, description, manufacturer, created, updated) VALUES (2, 'anti-polio', 'vaccine for polio', 'GSK', current_timestamp, null);
INSERT INTO vaccine (vaccine_id, name, description, manufacturer, created, updated) VALUES (3, 'Atv', 'anti-tetanus vaccine', 'Amson', current_timestamp, null);

-- dose data
INSERT INTO dose (dose_id, branch_id, vaccine_id, dosage_quantity, created, updated) VALUES (1, 1, 1, 100, current_timestamp, null);
INSERT INTO dose (dose_id, branch_id, vaccine_id, dosage_quantity, created, updated) VALUES (2, 1, 2, 50, current_timestamp, null);
INSERT INTO dose (dose_id, branch_id, vaccine_id, dosage_quantity, created, updated) VALUES (3, 1, 3, 10, current_timestamp, null);
INSERT INTO dose (dose_id, branch_id, vaccine_id, dosage_quantity, created, updated) VALUES (4, 2, 1, 100, current_timestamp, null);
INSERT INTO dose (dose_id, branch_id, vaccine_id, dosage_quantity, created, updated) VALUES (5, 3, 2, 50, current_timestamp, null);
INSERT INTO dose (dose_id, branch_id, vaccine_id, dosage_quantity, created, updated) VALUES (6, 4, 3, 10, current_timestamp, null);
INSERT INTO dose (dose_id, branch_id, vaccine_id, dosage_quantity, created, updated) VALUES (7, 5, 1, 1000, current_timestamp, null);

-- schedule data
INSERT INTO schedule(schedule_id, branch_id, patient_id, status, start_time, end_time, date, created, updated) VALUES (1, 1, 1, true, '0715', '0730', current_date+2, current_timestamp, null);
INSERT INTO schedule(schedule_id, branch_id, patient_id, status, start_time, end_time, date, created, updated) VALUES (2, 1, 2, true, '0815', '0830', current_date+2, current_timestamp, null);
INSERT INTO schedule(schedule_id, branch_id, patient_id, status, start_time, end_time, date, created, updated) VALUES (3, 1, 3, true, '1430', '1445', current_date+2, current_timestamp, null);
INSERT INTO schedule(schedule_id, branch_id, patient_id, status, start_time, end_time, date, created, updated) VALUES (4, 2, 4, true, '1000', '1015', current_date+2, current_timestamp, null);
