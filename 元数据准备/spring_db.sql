create database spring_db character set utf8;
use spring_db;
create table tbl_account(
                            id int primary key auto_increment,
                            name varchar(35),
                            money double
);
INSERT INTO tbl_account (name, money) VALUES
                                          ('Alice', 1000.00),
                                          ('Bob', 2500.50),
                                          ('Charlie', 500.25),
                                          ('David', 3000.00);


