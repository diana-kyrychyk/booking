DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS bookings;

CREATE TABLE employees
(
    id         SERIAL NOT NULL,
    first_name VARCHAR(100),
    last_name  VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE rooms
(
    id   SERIAL NOT NULL,
    name VARCHAR,
    PRIMARY KEY (id)
);

CREATE TABLE bookings
(
    id          SERIAL NOT NULL,
    date_start  TIMESTAMP,
    date_end    TIMESTAMP,
    room_id     INT,
    employee_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (room_id) references rooms (id),
    FOREIGN KEY (employee_id) references employees (id)
);

