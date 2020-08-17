

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


CREATE TABLE `brand_income_daily_statistics` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `biz_code` varchar(32) NOT NULL,
  `seller_id` bigint NOT NULL DEFAULT '0' COMMENT '卖家id',
  `order_sale_amount` bigint DEFAULT '0' COMMENT '订单支付金额',
  `order_sale_count` bigint DEFAULT '0' COMMENT '订单支付数量',
  `order_refund_amount` bigint DEFAULT '0' COMMENT '订单退款金额',
  `order_refund_count` bigint DEFAULT '0' COMMENT '订单退款数量',
  `sale_income` bigint DEFAULT NULL COMMENT '销售收入',
  `refund_income` bigint DEFAULT NULL COMMENT '销售退款',
  `date` int NOT NULL COMMENT '发生日期',
  `delete_mark` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位',
  `delete_timestamp` bigint NOT NULL DEFAULT '0' COMMENT '删除时间戳',
  `gmt_created` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_date` (`date`),
  KEY `idx_seller_id` (`seller_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1422 DEFAULT CHARSET=utf8 COMMENT='品牌商收益日统计';


INSERT INTO `brand_income_daily_statistics`(`id`, `biz_code`, `seller_id`, `order_sale_amount`, `order_sale_count`, `order_refund_amount`, `order_refund_count`, `sale_income`, `refund_income`, `date`, `delete_mark`, `delete_timestamp`, `gmt_created`, `gmt_modified`) VALUES (19, 'mokuaitv', 3051161, 1597474599, 1, 0, 0, 20000, 0, 29990103, 0, 0, '2019-12-19 10:49:59.000000', '2020-08-15 14:56:39');
INSERT INTO `brand_income_daily_statistics`(`id`, `biz_code`, `seller_id`, `order_sale_amount`, `order_sale_count`, `order_refund_amount`, `order_refund_count`, `sale_income`, `refund_income`, `date`, `delete_mark`, `delete_timestamp`, `gmt_created`, `gmt_modified`) VALUES (20, 'mokuaitv', 3051161, 1597474599, 1, 0, 0, 20000, 0, 29990104, 0, 0, '2019-12-19 16:39:29.000000', '2020-08-15 14:56:39');
INSERT INTO `brand_income_daily_statistics`(`id`, `biz_code`, `seller_id`, `order_sale_amount`, `order_sale_count`, `order_refund_amount`, `order_refund_count`, `sale_income`, `refund_income`, `date`, `delete_mark`, `delete_timestamp`, `gmt_created`, `gmt_modified`) VALUES (21, 'mokuaitv', 3050758, 1597474599, 1, 1000000, 1, 1, 1, 29990104, 0, 0, '2019-12-19 16:49:26.000000', '2020-08-15 14:56:39');
INSERT INTO `brand_income_daily_statistics`(`id`, `biz_code`, `seller_id`, `order_sale_amount`, `order_sale_count`, `order_refund_amount`, `order_refund_count`, `sale_income`, `refund_income`, `date`, `delete_mark`, `delete_timestamp`, `gmt_created`, `gmt_modified`) VALUES (22, 'mokuaitv', 3051313, 1597474599, 1, 0, 0, 3, 0, 20191219, 0, 0, '2019-12-19 21:15:42.000000', '2020-08-15 14:56:39');
INSERT INTO `brand_income_daily_statistics`(`id`, `biz_code`, `seller_id`, `order_sale_amount`, `order_sale_count`, `order_refund_amount`, `order_refund_count`, `sale_income`, `refund_income`, `date`, `delete_mark`, `delete_timestamp`, `gmt_created`, `gmt_modified`) VALUES (23, 'mokuaitv', 33996879, 1597474599, 1, 0, 0, 30000, 0, 20191219, 0, 0, '2019-12-19 22:19:50.000000', '2020-08-15 14:56:39');
INSERT INTO `brand_income_daily_statistics`(`id`, `biz_code`, `seller_id`, `order_sale_amount`, `order_sale_count`, `order_refund_amount`, `order_refund_count`, `sale_income`, `refund_income`, `date`, `delete_mark`, `delete_timestamp`, `gmt_created`, `gmt_modified`) VALUES (24, 'mokuaitv', 3051313, 1597474599, 3, 1, 1, 3, 1, 20191220, 0, 0, '2019-12-20 11:39:09.000000', '2020-08-15 14:56:39');
