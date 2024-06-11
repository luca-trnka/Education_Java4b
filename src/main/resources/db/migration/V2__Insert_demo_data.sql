-- V2__Insert_demo_data.sql
INSERT INTO customer (name, email) VALUES ('Customer A', 'customerA@example.com');
INSERT INTO supplier (name, email) VALUES ('Supplier A', 'supplierA@example.com');
INSERT INTO worker (name, email, supplier_id) VALUES ('Worker A', 'workerA@example.com', 1);
INSERT INTO offer (description, status, customer_id, supplier_id) VALUES ('First offer', 'NEW', 1, 1);
