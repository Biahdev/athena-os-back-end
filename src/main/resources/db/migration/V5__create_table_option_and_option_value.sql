CREATE TABLE options
(
    option_id  BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT          NOT NULL,
    title      VARCHAR(50)  NOT NULL,
    type       VARCHAR(250) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products (product_id)
);


CREATE TABLE options_values
(
    option_value_id BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    option_id       BIGINT         NOT NULL,
    name            VARCHAR(50) NOT NULL,
    price           NUMERIC(10, 2),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (option_id) REFERENCES options (option_id)
);