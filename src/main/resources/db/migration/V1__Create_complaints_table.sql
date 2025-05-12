CREATE TABLE `complaints`
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id VARCHAR(255) NOT NULL,
    content VARCHAR(1000),
    created_at DATETIME,
    reporter VARCHAR(255) NOT NULL,
    country VARCHAR(255),
    report_count INT DEFAULT 1,

    UNIQUE KEY unique_product_reporter (product_id, reporter)
);