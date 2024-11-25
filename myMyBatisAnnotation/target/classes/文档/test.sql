
/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : localhost:3306
 Source Schema         : mybatis-simple

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 16/04/2022 23:11:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
CREATE DATABASE IF NOT EXISTS mybatisdb;
USE mybatisdb;
DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user (
    id int(11) NOT NULL AUTO_INCREMENT,
    username varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
    birthday datetime DEFAULT NULL,
    sex varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
    address varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
    PRIMARY KEY (id) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO user VALUES (1, 'admin1', date_format('20220901', '%Y%m%d'),'m','address1');
INSERT INTO user VALUES (2, 'admin2', date_format('20220902', '%Y%m%d'),'m','address2');
INSERT INTO user VALUES (3, 'admin3', date_format('20220904', '%Y%m%d'),'f','address3');
INSERT INTO user VALUES (4, 'admin4', date_format('20220905', '%Y%m%d'),'f','address4');

SET FOREIGN_KEY_CHECKS = 1;