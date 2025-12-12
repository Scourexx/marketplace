CREATE TABLE IF NOT EXISTS role (
    role_id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL,
    user_account_id BIGINT
);

CREATE TABLE IF NOT EXISTS authority (
    authority_id BIGSERIAL PRIMARY KEY,
    authority_name VARCHAR(255) NOT NULL,
    role_id BIGINT,
    CONSTRAINT fk_authority_role 
        FOREIGN KEY (role_id) REFERENCES role(role_id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_authority_role_id ON authority(role_id);

