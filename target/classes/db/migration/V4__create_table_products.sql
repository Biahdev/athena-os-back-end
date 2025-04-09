CREATE TABLE products
(
    product_id  BIGINT            NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT            NOT NULL,
    name        VARCHAR(50)    NOT NULL,
    description VARCHAR(250),
    status      VARCHAR(100)   NOT NULL,
    cost_value  NUMERIC(10, 2) NOT NULL,
    sales_value NUMERIC(10, 2) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);