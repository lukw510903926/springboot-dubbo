/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50704
Source Host           : 127.0.0.1:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50704
File Encoding         : 65001

Date: 2018-10-25 15:31:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `test_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) DEFAULT NULL COMMENT '名称',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `test_type` int(11) DEFAULT NULL COMMENT '测试下划线字段命名类型',
  `test_date` datetime DEFAULT NULL COMMENT '日期',
  `role` bigint(20) DEFAULT NULL COMMENT '测试',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  PRIMARY KEY (`test_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1055360990119002114 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `price` varchar(64) DEFAULT NULL,
  `description` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1052835080358666243 DEFAULT CHARSET=utf8;
