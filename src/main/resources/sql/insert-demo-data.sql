INSERT INTO employees(id, first_name, last_name)
VALUES (1, 'John', 'Leyva'),
       (2, 'Joe', 'Jackson'),
       (3, 'Joanna', 'Klark'),
       (4, 'Mercy', 'Mii'),
       (5, 'Mark', 'Fedler');

SELECT nextval('employees_id_seq');
SELECT nextval('employees_id_seq');
SELECT nextval('employees_id_seq');
SELECT nextval('employees_id_seq');

INSERT INTO rooms(id, name)
VALUES (1, '101'),
       (1, '102'),
       (3, '103');

INSERT INTO bookings (id, date_start, date_end, room_id, employee_id)
VALUES (1, '2022-06-19 10:30:00', '2022-06-19 11:30:00', 2, 1),
       (2, '2022-06-19 10:30:00', '2022-06-19 11:30:00', 1, 1),
       (3, '2022-06-20 10:30:00', '2022-06-20 11:30:00', 1, 1),
       (4, '2022-06-20 12:30:00', '2022-06-20 14:30:00', 1, 1);
