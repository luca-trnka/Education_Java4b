-- src/main/resources/db/migration/V4__Insert_data.sql

-- Insert users
INSERT INTO app_user (name, password, email) VALUES
                                              ('newUser', 'Password1+', 'newUser@example.com'),
                                              ('admin', 'Password2+', 'admin@example.com'),
                                              ('customer', 'Password3+', 'customer@example.com'),
                                              ('supplier', 'Password4+', 'supplier@example.com'),
                                              ('worker', 'Password5+', 'worker@example.com');

-- Insert users and their roles
INSERT INTO user_roles (user_id, role_id) VALUES
                                              ((SELECT id FROM app_user WHERE email = 'newUser@example.com'), (SELECT id FROM roles WHERE name = 'NEW_USER')),
                                              ((SELECT id FROM app_user WHERE email = 'admin@example.com'), (SELECT id FROM roles WHERE name = 'ADMIN')),
                                              ((SELECT id FROM app_user WHERE email = 'customer@example.com'), (SELECT id FROM roles WHERE name = 'CUSTOMER')),
                                              ((SELECT id FROM app_user WHERE email = 'supplier@example.com'), (SELECT id FROM roles WHERE name = 'SUPPLIER')),
                                              ((SELECT id FROM app_user WHERE email = 'worker@example.com'), (SELECT id FROM roles WHERE name = 'WORKER'));