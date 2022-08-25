INSERT INTO skill (industry, skill_level) VALUES
('Java',  'Junior'),
('Java',  'Middle'),
('Java',  'Senior'),
('C++',  'Junior'),
('C++',  'Middle'),
('C++',  'Senior'),
('C#',  'Junior'),
('C#',  'Middle'),
('C#',  'Senior'),
('JS',  'Junior'),
('JS',  'Middle'),
('JS',  'Senior');

INSERT INTO company (name, description) VALUES
('RASK In-AGRO', 'The company implements and maintains IT management and accounting systems for industrial and agricultural enterprises'),
('Quartz', 'Software for agriculture and utilities');

INSERT INTO customer (name, edrpou, product) VALUES
('Oryla United Elevator, LLC', '30921733', 'Accounting of the elevator, mill and feed mill'),
('Oryla United Elevator, LLC', '30921733','BAS AGRO. Elevator accounting'),
('Korsun AF, STOV', '32012939', 'BAS AGRO. Accounting (Agriculture)'),
('Agrosoyuz-RP, LLC, with. Mliiv', '35013940', 'Accounting of an agricultural enterprise'),
('BI Group - Engineering', '160540301', 'ERP');

INSERT INTO developer (full_name, birth_date, sex, email, skype, salary) VALUES
('Neroda Dmitry', '1982-05-09', 'male', 'Neroda.D@inagro.com.ua', 'd.ramoss', 5000),
('Kravets Andrey', '1975-01-14', 'male', 'Kravets.A@inagro.com.ua', 'acrom_sc', 1500),
('Spivak Galina', '1972-07-12', 'female', 'Spivak.G@inagro.com.ua', 'gala_spivak', 2500),
('Demchenko Bogdan', '1992-11-13', 'male', 'demchenko.b@ukr.net', 'bogdan_d6', 2000),
('Huzii Anna', '1984-07-08', 'female', 'anna_guzii@ukr.net', 'anna_guzii', 2500),
('Onishchenko Elena', '1990-01-02', 'female', 'onishchenko.l@inagro.com.ua', 'lena.stadnik32', 1000);

INSERT INTO project (start_date, name, description, company_id, customer_id) VALUES
('2017-01-04','Kazakhstan BI-Group', 'ERP with enhancements for business management of a construction company', 1, 5),
('2016-08-01','UkrAgroCom', 'Program for the UkrAgroCom company', 1, 2),
('2021-09-01','KUP Agro', 'Program for integrated management of an agricultural enterprise', 1, 3),
('2010-04-20','Quarter', 'Program for utility companies', 2, 4),
('2014-05-10','Agro-business management for Ukraine', 'A program for managing an agricultural enterprise', 2, 4);

INSERT INTO developer_skill (developer_id, skill_id) VALUES
(1,12),
(1,2),
(1,8),
(2,11),
(3,2),
(3,11),
(4,1),
(4,11),
(5,2),
(5,11),
(6,11);

INSERT INTO project_developer (project_id, developer_id) VALUES
(1,6),
(1,2),
(1,4),
(2,5),
(3,2),
(3,3),
(4,2),
(4,1),
(5,2),
(5,6);

UPDATE project
SET project.cost = (
    SELECT SUM(salary)
    FROM developer
    WHERE developer.id IN (
        SELECT PS.developer_id
        FROM project_developer AS PS
        WHERE PS.project_id=project.id
    )
);