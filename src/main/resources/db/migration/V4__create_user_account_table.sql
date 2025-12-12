CREATE TABLE IF NOT EXISTS user_account (
    user_account_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    customer_id BIGINT,
    is_account_non_expired BOOLEAN DEFAULT true,
    is_account_non_locked BOOLEAN DEFAULT true,
    is_credentials_non_expired BOOLEAN DEFAULT true,
    is_enabled BOOLEAN DEFAULT true
);

CREATE INDEX IF NOT EXISTS idx_user_account_username ON user_account(username);
CREATE INDEX IF NOT EXISTS idx_user_account_customer_id ON user_account(customer_id);

ALTER TABLE user_account
ADD CONSTRAINT fk_user_account_customer 
FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE;

ALTER TABLE role
ADD CONSTRAINT fk_role_user_account 
FOREIGN KEY (user_account_id) REFERENCES user_account(user_account_id) ON DELETE CASCADE;

CREATE INDEX IF NOT EXISTS idx_role_user_account_id ON role(user_account_id);

