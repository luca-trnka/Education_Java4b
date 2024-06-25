CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE app_user
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name     VARCHAR(100) NOT NULL,
    role_id  INTEGER REFERENCES roles (id)

);

CREATE TABLE offers
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    description TEXT,
    status      VARCHAR(50)  NOT NULL,
    supplier_id INTEGER REFERENCES app_user (id),
    customer_id INTEGER REFERENCES app_user (id)
);

CREATE TABLE offer_workers
(
    offer_id  INTEGER REFERENCES offers (id),
    worker_id INTEGER REFERENCES app_user (id),
    PRIMARY KEY (offer_id, worker_id)
);

CREATE TABLE offer_status_permissions
(
    id      SERIAL PRIMARY KEY,
    status  VARCHAR(50) NOT NULL,
    role_id INTEGER REFERENCES roles (id),
    UNIQUE (status, role_id)
);