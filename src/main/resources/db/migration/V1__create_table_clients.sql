CREATE TABLE clients
(
    client_id  BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    status     VARCHAR(50) NOT NULL DEFAULT 'REGULAR',
    address    VARCHAR(100),
    phone      VARCHAR(50),
    whatsapp   TINYINT              DEFAULT 0,
    instagram  VARCHAR(50),
    created_at TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);