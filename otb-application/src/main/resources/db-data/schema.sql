DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    user_role  VARCHAR(255) NOT NULL CHECK (user_role IN ('ADMIN', 'USER')),
    PRIMARY KEY (id)
);