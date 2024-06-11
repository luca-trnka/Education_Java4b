-- V1__initial_setup.sql
CREATE TABLE customer (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE supplier (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE worker (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        supplier_id BIGINT,
                        CONSTRAINT fk_supplier
                            FOREIGN KEY(supplier_id)
                                REFERENCES supplier(id)
);

CREATE TABLE offer (
                       id BIGSERIAL PRIMARY KEY,
                       description TEXT NOT NULL,
                       status VARCHAR(50) NOT NULL,
                       customer_id BIGINT,
                       supplier_id BIGINT,
                       CONSTRAINT fk_customer
                           FOREIGN KEY(customer_id)
                               REFERENCES customer(id),
                       CONSTRAINT fk_supplier
                           FOREIGN KEY(supplier_id)
                               REFERENCES supplier(id)
);

CREATE TABLE offer_workers (
                               offer_id BIGINT NOT NULL,
                               worker_id BIGINT NOT NULL,
                               PRIMARY KEY (offer_id, worker_id),
                               CONSTRAINT fk_offer
                                   FOREIGN KEY(offer_id)
                                       REFERENCES offer(id),
                               CONSTRAINT fk_worker
                                   FOREIGN KEY(worker_id)
                                       REFERENCES worker(id)
);
