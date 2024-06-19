CREATE TABLE app_user
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

CREATE TABLE offers
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    description TEXT,
    offer_status VARCHAR(50) NOT NULL,
    id_supplier  INTEGER REFERENCES app_user (id),
    id_customer  INTEGER REFERENCES app_user (id)
);

CREATE TABLE offer_workers
(
    offer_id  INTEGER REFERENCES offers (id),
    worker_id INTEGER REFERENCES app_user (id),
    PRIMARY KEY (offer_id, worker_id)
);

CREATE TABLE offer_status_permissions
(
    id           SERIAL PRIMARY KEY,
    offer_status VARCHAR(50) NOT NULL,
    role_id      INTEGER REFERENCES roles (id),
    UNIQUE (offer_status, role_id)
);