CREATE TABLE users
(
    id    SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name  VARCHAR(100) NOT NULL
);

CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_roles
(
    user_id INTEGER REFERENCES users (id),
    role_id INTEGER REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE offers
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    description TEXT,
    offer_status VARCHAR(50) NOT NULL,
    id_supplier  INTEGER REFERENCES users (id),
    id_customer  INTEGER REFERENCES users (id)
);

CREATE TABLE offer_workers
(
    offer_id  INTEGER REFERENCES offers (id),
    worker_id INTEGER REFERENCES users (id),
    PRIMARY KEY (offer_id, worker_id)
);

CREATE TABLE offer_status_permission
(
    id           SERIAL PRIMARY KEY,
    offer_status VARCHAR(50) NOT NULL,
    role_id      INTEGER REFERENCES roles (id),
    UNIQUE (offer_status, role_id)
);