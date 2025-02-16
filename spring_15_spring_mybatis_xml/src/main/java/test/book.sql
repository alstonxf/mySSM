CREATE DATABASE mydb;
USE mydb;

CREATE TABLE book (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(100) NOT NULL,
                      author VARCHAR(100),
                      price DECIMAL(10,2)
);