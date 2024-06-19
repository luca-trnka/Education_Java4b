-- src/main/resources/db/migration/V2__Insert_roles_and_offer_status_permission.sql

INSERT INTO roles (name) VALUES  ('NEW_USER'), ('ADMIN'), ('CUSTOMER'), ('SUPPLIER'), ('WORKER');

INSERT INTO offer_status_permissions (offer_status, role_id)
SELECT offer_status, roles.id
FROM (VALUES
          ('NEW', 'SUPPLIER'),
          ('NEW', 'ADMIN'),
          ('ACCEPTED', 'CUSTOMER'),
          ('ACCEPTED', 'ADMIN'),
          ('REJECTED', 'CUSTOMER'),
          ('REJECTED', 'ADMIN'),
          ('IN_PROGRESS', 'SUPPLIER'),
          ('IN_PROGRESS', 'ADMIN'),
          ('READY_TO_BE_SHOWN', 'WORKER'),
          ('READY_TO_BE_SHOWN', 'SUPPLIER'),
          ('READY_TO_BE_SHOWN', 'ADMIN'),
          ('CUSTOMER_APPROVAL', 'CUSTOMER'),
          ('CUSTOMER_APPROVAL', 'ADMIN'),
          ('CUSTOMER_DISAPPROVAL', 'CUSTOMER'),
          ('CUSTOMER_DISAPPROVAL', 'ADMIN'),
          ('FIXING_BUGS', 'WORKER'),
          ('FIXING_BUGS', 'SUPPLIER'),
          ('FIXING_BUGS', 'ADMIN'),
          ('INVOICED', 'SUPPLIER'),
          ('INVOICED', 'WORKER'),
          ('INVOICED', 'ADMIN'),
          ('PAID', 'SUPPLIER'),
          ('PAID', 'WORKER'),
          ('PAID', 'ADMIN'),
          ('COMPLETED', 'SUPPLIER'),
          ('COMPLETED', 'WORKER'),
          ('COMPLETED', 'ADMIN')
     ) AS tmp (offer_status, role_name)
         JOIN roles ON roles.name = tmp.role_name;

