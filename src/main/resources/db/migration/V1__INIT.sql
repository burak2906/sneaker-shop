CREATE SCHEMA IF NOT EXISTS project;
SET search_path = project, pg_catalog;

-- SEQUENCES
CREATE SEQUENCE roles_seq START WITH 3 INCREMENT BY 1;

-- USERS + ROLES
CREATE TABLE users (
                       id uuid PRIMARY KEY,
                       username TEXT,
                       email TEXT UNIQUE,
                       password TEXT
);

CREATE TABLE roles (
                       id INTEGER PRIMARY KEY,
                       name TEXT
);

CREATE TABLE user_role (
                           user_id UUID NOT NULL,
                           role_id INTEGER NOT NULL,
                           PRIMARY KEY (user_id, role_id),
                           FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                           FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- INSERT ROLES
INSERT INTO roles (id, name)
VALUES
    (1, 'ADMIN'),
    (2, 'USER');

-- SNEAKERS
CREATE TABLE sneakers (
                          id SERIAL PRIMARY KEY,
                          name TEXT,
                          size TEXT,
                          price DOUBLE PRECISION
);

-- CART
CREATE TABLE carts (
                       id SERIAL PRIMARY KEY,
                       user_id UUID UNIQUE,
                       FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE cart_sneakers (
                               cart_id INTEGER NOT NULL,
                               sneaker_id INTEGER NOT NULL,
                               PRIMARY KEY (cart_id, sneaker_id),
                               FOREIGN KEY (cart_id) REFERENCES carts (id) ON DELETE CASCADE,
                               FOREIGN KEY (sneaker_id) REFERENCES sneakers (id) ON DELETE CASCADE
);


-- REVIEW
CREATE TABLE reviews (
                         id SERIAL PRIMARY KEY,
                         comment TEXT,
                         rating INTEGER,
                         reviewer_id UUID,
                         sneaker_id INTEGER,
                         FOREIGN KEY (reviewer_id) REFERENCES users (id) ON DELETE SET NULL,
                         FOREIGN KEY (sneaker_id) REFERENCES sneakers (id) ON DELETE CASCADE
);