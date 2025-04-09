CREATE TABLE orders
(
    order_id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id               BIGINT         NOT NULL,
    employee_id             BIGINT         NOT NULL,
    status                  VARCHAR(50)    NOT NULL,
    delivery_date           DATE           NOT NULL,
    delivery_type           VARCHAR(50)    NOT NULL,
    quantity                INT         NOT NULL,
    discount_order          NUMERIC(10, 2),
    discount_order_products NUMERIC(10, 2),
    discount_total          NUMERIC(10, 2),
    initial_total           NUMERIC(10, 2) NOT NULL,
    final_total             NUMERIC(10, 2) NOT NULL,
    note                    TEXT,
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients (client_id),
    FOREIGN KEY (employee_id) REFERENCES employees (employee_id)
);

CREATE TABLE order_products
(
    order_product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id         BIGINT         NOT NULL,
    product_id       BIGINT         NOT NULL,
    quantity         INT         NOT NULL,
    discount         DECIMAL(10, 2) NOT NULL,
    extra_price      DECIMAL(10, 2) NOT NULL,
    initial_unit     DECIMAL(10, 2) NOT NULL,
    final_unit       DECIMAL(10, 2) NOT NULL,
    initial_total    DECIMAL(10, 2) NOT NULL,
    final_total      DECIMAL(10, 2) NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products (product_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

CREATE TABLE order_product_options
(
    order_product_option_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_product_id        BIGINT      NOT NULL,
    option_id               BIGINT      NOT NULL,
    title                   VARCHAR(50) NOT NULL,
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_product_id) REFERENCES order_products (order_product_id),
    FOREIGN KEY (option_id) REFERENCES options (option_id)
);

CREATE TABLE order_product_option_values
(
    order_product_option_value_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_product_option_id       BIGINT      NOT NULL,
    option_value_id               BIGINT      NOT NULL,
    name                          VARCHAR(50) NOT NULL,
    price                         DECIMAL(10, 2),
    created_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at                    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_product_option_id) REFERENCES order_product_options (order_product_option_id),
    FOREIGN KEY (option_value_id) REFERENCES options_values (option_value_id)
);



