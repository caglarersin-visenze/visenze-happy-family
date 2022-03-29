DROP TABLE IF EXISTS Product;
CREATE TABLE Product
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_name char(10) NOT NULL,
    product_stock INT NOT NULL
);