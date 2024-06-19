-- src/main/resources/db/migration/V3__Add_roles_to_users.sql

CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_id BIGINT NOT NULL,
                            PRIMARY KEY (user_id, role_id)
);