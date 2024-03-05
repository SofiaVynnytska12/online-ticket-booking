DROP TABLE IF EXISTS train_tickets;
DROP TABLE IF EXISTS bus_tickets;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       user_role VARCHAR(255) NOT NULL CHECK (user_role IN ('ADMIN', 'USER'))
);

CREATE TABLE train_tickets (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               name VARCHAR(255) NOT NULL,
                               from_city VARCHAR(255) NOT NULL CHECK (from_city IN ('KYIV','DNIPRO','WARSAW','KHARKIV')),
                               to_city VARCHAR(255) NOT NULL CHECK (to_city IN ('KYIV','DNIPRO','WARSAW','KHARKIV')),
                               day_of_departure DATE NOT NULL,
                               arrival_day DATE NOT NULL,
                               time_of_departure TIME NOT NULL,
                               arrival_time TIME NOT NULL,
                               price DECIMAL(10, 2) NOT NULL CHECK (price > 0),
                               seat_number INT NOT NULL CHECK (seat_number > 0),
                               type_of_train_class VARCHAR(255) DEFAULT NULL CHECK (type_of_train_class IN ('FIRST_CLASS', 'SECOND_CLASS', 'LUXURY')),
                               car_number INT NOT NULL CHECK (car_number > 0),
                               owner_id BIGINT,
                               CONSTRAINT owner_id_train_tickets_fk FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE bus_tickets (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             from_city VARCHAR(255) NOT NULL CHECK (from_city IN ('KYIV','DNIPRO','WARSAW','KHARKIV')),
                             to_city VARCHAR(255) NOT NULL CHECK (to_city IN ('KYIV','DNIPRO','WARSAW','KHARKIV')),
                             ticket_status VARCHAR(255) NOT NULL CHECK (ticket_status IN ('AVAILABLE', 'BOOKED')),
                             day_of_departure DATE NOT NULL,
                             arrival_day DATE NOT NULL,
                             time_of_departure TIME NOT NULL,
                             arrival_time TIME NOT NULL,
                             price DECIMAL(10, 2) NOT NULL CHECK (price > 0),
                             seat_number INT NOT NULL CHECK (seat_number > 0),
                             owner_id BIGINT,
                             CONSTRAINT owner_id_bus_tickets_fk FOREIGN KEY (owner_id) REFERENCES users(id)
);