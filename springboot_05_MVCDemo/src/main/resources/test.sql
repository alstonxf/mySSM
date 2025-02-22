-- 创建数据库
CREATE DATABASE IF NOT EXISTS test_db;
USE test_db;

-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
                                    id INT AUTO_INCREMENT PRIMARY KEY,  -- 自增ID
                                    name VARCHAR(100) NOT NULL,         -- 用户名
    age INT NOT NULL,                   -- 年龄
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 创建时间
    );


-- 插入一些测试数据
INSERT INTO user (name, age) VALUES ('Alice', 30);
INSERT INTO user (name, age) VALUES ('Bob', 25);
INSERT INTO user (name, age) VALUES ('Charlie', 35);
INSERT INTO user (name, age) VALUES ('David', 40);
INSERT INTO user (name, age) VALUES ('Eve', 29);
