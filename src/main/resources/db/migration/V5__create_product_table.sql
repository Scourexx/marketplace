CREATE TABLE IF NOT EXISTS product (
    product_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    category VARCHAR(255),
    stock INTEGER DEFAULT 0,
    description TEXT,
    status BOOLEAN DEFAULT true
);

CREATE INDEX IF NOT EXISTS idx_product_name ON product(name);
CREATE INDEX IF NOT EXISTS idx_product_category ON product(category);
CREATE INDEX IF NOT EXISTS idx_product_status ON product(status);

