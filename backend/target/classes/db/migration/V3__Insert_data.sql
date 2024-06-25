-- src/main/resources/db/migration/V3__Insert_data.sql

-- Insert users
INSERT INTO app_user (name, password, email, role_id) VALUES
                                                          ('newUser', 'Password1+', 'newUser@example.com', (SELECT id FROM roles WHERE name = 'NEW_USER')),
                                                          ('admin', 'Password2+', 'admin@example.com', (SELECT id FROM roles WHERE name = 'ADMIN')),
                                                          ('customer', 'Password3+', 'customer@example.com', (SELECT id FROM roles WHERE name = 'CUSTOMER')),
                                                          ('supplier', 'Password4+', 'supplier@example.com', (SELECT id FROM roles WHERE name = 'SUPPLIER')),
                                                          ('worker', 'Password5+', 'worker@example.com', (SELECT id FROM roles WHERE name = 'WORKER'));

-- Insert offers
INSERT INTO offers (title, description, status, supplier_id, customer_id) VALUES
                                                                                    ('Offer 1', 'Text123', 'NEW', (SELECT id FROM app_user WHERE email = 'supplier@example.com'), (SELECT id FROM app_user WHERE email = 'customer@example.com'));

-- Insert workers for the offers
INSERT INTO offer_workers (offer_id, worker_id) VALUES
                                                    ((SELECT id FROM offers WHERE title = 'Offer 1'), (SELECT id FROM app_user WHERE email = 'worker@example.com'));