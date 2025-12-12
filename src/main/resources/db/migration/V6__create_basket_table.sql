CREATE TABLE IF NOT EXISTS basket (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_basket_customer_id ON basket(customer_id);
CREATE INDEX IF NOT EXISTS idx_basket_product_id ON basket(product_id);

ALTER TABLE basket
ADD CONSTRAINT fk_basket_customer 
FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE;

ALTER TABLE basket 
ADD CONSTRAINT fk_basket_product 
FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE;

ALTER TABLE basket
ADD CONSTRAINT uk_basket_customer_product 
UNIQUE (customer_id, product_id);

