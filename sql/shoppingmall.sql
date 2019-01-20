/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50621
 Source Host           : localhost:3306
 Source Schema         : shoppingmall

 Target Server Type    : MySQL
 Target Server Version : 50621
 File Encoding         : 65001

 Date: 20/01/2019 18:59:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for shopping_mall_address
-- ----------------------------
DROP TABLE IF EXISTS `shopping_mall_address`;
CREATE TABLE `shopping_mall_address`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id 可能是买家 可能是卖家(商家)',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货/发货人名称',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '座机号码',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `role` int(4) NULL DEFAULT NULL COMMENT '地址所属角色 1买家 2商家',
  `province` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
  `district` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区/县',
  `street` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '街道',
  `postcode` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of shopping_mall_address
-- ----------------------------
INSERT INTO `shopping_mall_address` VALUES (1, 1, 'czn', '1775', '7389565', 0, 'jiangsu', 'nanjing', 'jiangning', NULL, '211106', '2019-01-13 02:29:42', '2019-01-13 02:29:42');
INSERT INTO `shopping_mall_address` VALUES (3, 1, 'chenzengnian', '152', '9094234', 0, 'jiangsu', 'nanjing', 'jiangning', NULL, '211106', '2019-01-13 02:33:52', '2019-01-13 02:33:52');
INSERT INTO `shopping_mall_address` VALUES (4, 2, 'seller_chenzengnian', '152781', '9094234', 1, 'jiangsu', 'nanjing', 'jiangning', NULL, '211106', '2019-01-13 02:35:44', '2019-01-13 02:35:44');

-- ----------------------------
-- Table structure for shopping_mall_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_mall_cart`;
CREATE TABLE `shopping_mall_cart`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL COMMENT '商品id',
  `buyer_id` int(11) NULL DEFAULT NULL COMMENT '买家id',
  `store_id` int(11) NULL DEFAULT NULL COMMENT '店铺id',
  `price` decimal(20, 2) NULL DEFAULT NULL COMMENT '价格',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `check_status` int(11) NULL DEFAULT NULL COMMENT '是否勾选',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of shopping_mall_cart
-- ----------------------------
INSERT INTO `shopping_mall_cart` VALUES (6, 2, 1, 1, 3999.00, 8, 1, '2019-01-13 20:05:27', '2019-01-20 15:55:20');
INSERT INTO `shopping_mall_cart` VALUES (7, 3, 1, 2, 1888.00, 2, 1, '2019-01-20 14:44:27', '2019-01-20 15:55:20');

-- ----------------------------
-- Table structure for shopping_mall_category
-- ----------------------------
DROP TABLE IF EXISTS `shopping_mall_category`;
CREATE TABLE `shopping_mall_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父类别id 为0是说明是根类别',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别名称',
  `detail` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别详情',
  `status` int(4) NULL DEFAULT 1 COMMENT '0已废弃类别  1当前可用类别',
  `order_by` int(4) NULL DEFAULT NULL COMMENT '排序编号,同类排序规则,数值相等按自然序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100034 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of shopping_mall_category
-- ----------------------------
INSERT INTO `shopping_mall_category` VALUES (100001, 0, '家用电器', NULL, 1, NULL, '2017-03-25 16:46:00', '2017-03-25 16:46:00');
INSERT INTO `shopping_mall_category` VALUES (100002, 0, '数码3C', NULL, 1, NULL, '2017-03-25 16:46:21', '2017-03-25 16:46:21');
INSERT INTO `shopping_mall_category` VALUES (100003, 0, '服装箱包', NULL, 1, NULL, '2017-03-25 16:49:53', '2017-03-25 16:49:53');
INSERT INTO `shopping_mall_category` VALUES (100004, 0, '食品生鲜', NULL, 1, NULL, '2017-03-25 16:50:19', '2017-03-25 16:50:19');
INSERT INTO `shopping_mall_category` VALUES (100005, 0, '酒水饮料', NULL, 1, NULL, '2017-03-25 16:50:29', '2017-03-25 16:50:29');
INSERT INTO `shopping_mall_category` VALUES (100006, 100001, '冰箱', NULL, 1, NULL, '2017-03-25 16:52:15', '2017-03-25 16:52:15');
INSERT INTO `shopping_mall_category` VALUES (100007, 100001, '液晶电视机', NULL, 1, NULL, '2017-03-25 16:52:26', '2017-03-25 16:52:26');
INSERT INTO `shopping_mall_category` VALUES (100008, 100001, '洗衣机', NULL, 1, NULL, '2017-03-25 16:52:39', '2017-03-25 16:52:39');
INSERT INTO `shopping_mall_category` VALUES (100009, 100001, '空调', NULL, 1, NULL, '2017-03-25 16:52:45', '2017-03-25 16:52:45');
INSERT INTO `shopping_mall_category` VALUES (100010, 100001, '电热水器', NULL, 1, NULL, '2017-03-25 16:52:54', '2017-03-25 16:52:54');
INSERT INTO `shopping_mall_category` VALUES (100011, 100002, '电脑', NULL, 1, NULL, '2017-03-25 16:53:18', '2017-03-25 16:53:18');
INSERT INTO `shopping_mall_category` VALUES (100012, 100002, '手机', NULL, 1, NULL, '2017-03-25 16:53:27', '2017-03-25 16:53:27');
INSERT INTO `shopping_mall_category` VALUES (100013, 100002, '平板电脑', NULL, 1, NULL, '2017-03-25 16:53:35', '2017-03-25 16:53:35');
INSERT INTO `shopping_mall_category` VALUES (100014, 100002, '数码相机', NULL, 1, NULL, '2017-03-25 16:53:56', '2017-03-25 16:53:56');
INSERT INTO `shopping_mall_category` VALUES (100015, 100002, '3C配件', NULL, 1, NULL, '2017-03-25 16:54:07', '2017-03-25 16:54:07');
INSERT INTO `shopping_mall_category` VALUES (100016, 100003, '女装', NULL, 1, NULL, '2017-03-25 16:54:44', '2017-03-25 16:54:44');
INSERT INTO `shopping_mall_category` VALUES (100017, 100003, '帽子', NULL, 1, NULL, '2017-03-25 16:54:51', '2017-03-25 16:54:51');
INSERT INTO `shopping_mall_category` VALUES (100018, 100003, '旅行箱', NULL, 1, NULL, '2017-03-25 16:55:02', '2017-03-25 16:55:02');
INSERT INTO `shopping_mall_category` VALUES (100019, 100003, '手提包', NULL, 1, NULL, '2017-03-25 16:55:09', '2017-03-25 16:55:09');
INSERT INTO `shopping_mall_category` VALUES (100020, 100003, '保暖内衣', NULL, 1, NULL, '2017-03-25 16:55:18', '2017-03-25 16:55:18');
INSERT INTO `shopping_mall_category` VALUES (100021, 100004, '零食', NULL, 1, NULL, '2017-03-25 16:55:30', '2017-03-25 16:55:30');
INSERT INTO `shopping_mall_category` VALUES (100022, 100004, '生鲜', NULL, 1, NULL, '2017-03-25 16:55:37', '2017-03-25 16:55:37');
INSERT INTO `shopping_mall_category` VALUES (100023, 100004, '半成品菜', NULL, 1, NULL, '2017-03-25 16:55:47', '2017-03-25 16:55:47');
INSERT INTO `shopping_mall_category` VALUES (100024, 100004, '速冻食品', NULL, 1, NULL, '2017-03-25 16:55:56', '2017-03-25 16:55:56');
INSERT INTO `shopping_mall_category` VALUES (100025, 100004, '进口食品', NULL, 1, NULL, '2017-03-25 16:56:06', '2017-03-25 16:56:06');
INSERT INTO `shopping_mall_category` VALUES (100026, 100005, '白酒', NULL, 1, NULL, '2017-03-25 16:56:22', '2017-03-25 16:56:22');
INSERT INTO `shopping_mall_category` VALUES (100027, 100005, '红酒', NULL, 1, NULL, '2017-03-25 16:56:30', '2017-03-25 16:56:30');
INSERT INTO `shopping_mall_category` VALUES (100028, 100005, '饮料', NULL, 1, NULL, '2017-03-25 16:56:37', '2017-03-25 16:56:37');
INSERT INTO `shopping_mall_category` VALUES (100029, 100005, '调制鸡尾酒', NULL, 1, NULL, '2017-03-25 16:56:45', '2017-03-25 16:56:45');
INSERT INTO `shopping_mall_category` VALUES (100030, 100005, '进口洋酒', NULL, 1, NULL, '2017-03-25 16:57:05', '2017-03-25 16:57:05');
INSERT INTO `shopping_mall_category` VALUES (100031, 100002, 'mac', '苹果笔记本大促销5折起', 1, NULL, '2019-01-07 01:31:38', '2019-01-07 01:31:38');
INSERT INTO `shopping_mall_category` VALUES (100032, 0, '一切球类', '篮球、足球、羽毛球等各种球类', 1, NULL, '2019-01-20 12:07:16', '2019-01-20 12:07:16');
INSERT INTO `shopping_mall_category` VALUES (100033, 0, '球类', '篮球、足球、羽毛球等各种球类', 1, NULL, '2019-01-20 12:13:30', '2019-01-20 12:13:30');

-- ----------------------------
-- Table structure for shopping_mall_order
-- ----------------------------
DROP TABLE IF EXISTS `shopping_mall_order`;
CREATE TABLE `shopping_mall_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` bigint(20) NOT NULL COMMENT '订单号',
  `buyer_id` int(11) NOT NULL COMMENT '买家id',
  `address_id` int(11) NOT NULL COMMENT '收获地址id',
  `payment` decimal(20, 2) NOT NULL COMMENT '订单金额',
  `pay_type` int(4) NULL DEFAULT NULL COMMENT '支付类型',
  `postage` int(10) NULL DEFAULT NULL COMMENT '邮费',
  `status` int(4) NULL DEFAULT NULL COMMENT '订单状态 0取消 10未付款 20已付款 30已发货但未收货  40收货成功 50交易关闭',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '收货时间',
  `close_time` datetime(0) NULL DEFAULT NULL COMMENT '交易关闭时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_no_index`(`order_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of shopping_mall_order
-- ----------------------------
INSERT INTO `shopping_mall_order` VALUES (2, 1547381364107, 1, 1, 67986.00, 1, 0, 0, '2019-01-14 21:47:47', NULL, NULL, NULL, '2019-01-13 20:09:18', NULL);
INSERT INTO `shopping_mall_order` VALUES (3, 1547381626381, 1, 3, 67986.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2019-01-13 20:13:37', NULL);
INSERT INTO `shopping_mall_order` VALUES (4, 1547480549485, 1, 1, 67986.00, 1, 0, 20, '2019-01-14 23:44:02', NULL, NULL, NULL, '2019-01-14 23:42:22', NULL);
INSERT INTO `shopping_mall_order` VALUES (5, 1547480798821, 1, 1, 67986.00, 1, 0, 20, '2019-01-14 23:47:35', NULL, NULL, NULL, '2019-01-14 23:46:38', NULL);
INSERT INTO `shopping_mall_order` VALUES (6, 1547970185412, 1, 1, 35768.00, 1, 0, 10, NULL, NULL, NULL, NULL, '2019-01-20 15:42:55', NULL);
INSERT INTO `shopping_mall_order` VALUES (7, 1547970927879, 1, 1, 35768.00, 1, 0, 30, NULL, NULL, NULL, NULL, '2019-01-20 15:55:22', NULL);

-- ----------------------------
-- Table structure for shopping_mall_order_entity
-- ----------------------------
DROP TABLE IF EXISTS `shopping_mall_order_entity`;
CREATE TABLE `shopping_mall_order_entity`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` bigint(20) NOT NULL COMMENT '订单号',
  `buyer_id` int(11) NOT NULL COMMENT '买家id',
  `seller_id` int(11) NULL DEFAULT NULL COMMENT '卖家id',
  `store_id` int(11) NULL DEFAULT NULL COMMENT '店铺id',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `product_image_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品主图url',
  `price` decimal(20, 2) NULL DEFAULT NULL COMMENT '商品价格',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '商品数量',
  `total_price` decimal(10, 0) NULL DEFAULT NULL COMMENT '商品总价,保留两位小数,单位是元',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of shopping_mall_order_entity
-- ----------------------------
INSERT INTO `shopping_mall_order_entity` VALUES (1, 1547381364107, 1, 2, 1, 1, 'iphone7 128G', NULL, 5999.00, 6, 35994, '2019-01-13 20:09:18', '2019-01-13 20:09:18');
INSERT INTO `shopping_mall_order_entity` VALUES (2, 1547381364107, 1, 2, 1, 2, 'sansung', NULL, 3999.00, 8, 31992, '2019-01-13 20:09:18', '2019-01-13 20:09:18');
INSERT INTO `shopping_mall_order_entity` VALUES (3, 1547381626381, 1, 2, 1, 1, 'iphone7 128G', NULL, 5999.00, 6, 35994, '2019-01-13 20:13:37', '2019-01-13 20:13:37');
INSERT INTO `shopping_mall_order_entity` VALUES (4, 1547381626381, 1, 2, 1, 2, 'sansung', NULL, 3999.00, 8, 31992, '2019-01-13 20:13:37', '2019-01-13 20:13:37');
INSERT INTO `shopping_mall_order_entity` VALUES (5, 1547480549485, 1, 2, 1, 1, 'iphone7 128G', NULL, 5999.00, 6, 35994, '2019-01-14 23:42:22', '2019-01-14 23:42:22');
INSERT INTO `shopping_mall_order_entity` VALUES (6, 1547480549485, 1, 2, 1, 2, 'sansung', NULL, 3999.00, 8, 31992, '2019-01-14 23:42:22', '2019-01-14 23:42:22');
INSERT INTO `shopping_mall_order_entity` VALUES (7, 1547480798821, 1, 2, 1, 1, 'iphone7 128G', NULL, 5999.00, 6, 35994, '2019-01-14 23:46:38', '2019-01-14 23:46:38');
INSERT INTO `shopping_mall_order_entity` VALUES (8, 1547480798821, 1, 2, 1, 2, 'sansung', NULL, 3999.00, 8, 31992, '2019-01-14 23:46:38', '2019-01-14 23:46:38');
INSERT INTO `shopping_mall_order_entity` VALUES (9, 1547970185412, 1, 2, 1, 2, 'sansung', NULL, 3999.00, 8, 31992, '2019-01-20 15:42:55', '2019-01-20 15:42:55');
INSERT INTO `shopping_mall_order_entity` VALUES (10, 1547970185412, 1, 2, 2, 3, 'iphoneSE 64G', NULL, 1888.00, 2, 3776, '2019-01-20 15:42:55', '2019-01-20 15:42:55');
INSERT INTO `shopping_mall_order_entity` VALUES (11, 1547970927879, 1, 2, 1, 2, 'sansung', NULL, 3999.00, 8, 31992, '2019-01-20 15:55:22', '2019-01-20 15:55:22');
INSERT INTO `shopping_mall_order_entity` VALUES (12, 1547970927879, 1, 2, 2, 3, 'iphoneSE 64G', NULL, 1888.00, 2, 3776, '2019-01-20 15:55:22', '2019-01-20 15:55:22');

-- ----------------------------
-- Table structure for shopping_mall_product
-- ----------------------------
DROP TABLE IF EXISTS `shopping_mall_product`;
CREATE TABLE `shopping_mall_product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL COMMENT '类别id,对应类别表的主键',
  `store_id` int(11) NOT NULL COMMENT '店铺id,对应店铺id,该商品属于某个店铺的',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `subtitle` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品副标题',
  `detail` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品的详情',
  `main_image_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品主图的url地址',
  `sub_images_url` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品子图url地址',
  `price` decimal(20, 2) NOT NULL COMMENT '商品价格',
  `stock` int(11) NOT NULL COMMENT '商品库存',
  `status` int(4) NOT NULL COMMENT '商品状态 0 下架 1 在售',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of shopping_mall_product
-- ----------------------------
INSERT INTO `shopping_mall_product` VALUES (1, 100001, 1, 'iphone7 128G', '新品上市，手慢无', 'ipone7 超大级别128G存储，靓丽外观，时尚大气，潮流上档次', NULL, NULL, 5999.00, 104, 0, '2019-01-12 19:49:44', '2019-01-20 13:59:48');
INSERT INTO `shopping_mall_product` VALUES (2, 100001, 1, 'sansung', '新品上市，手慢无', '三星爆炸机', NULL, NULL, 3999.00, 360, 1, '2019-01-12 20:09:02', '2019-01-20 15:55:22');
INSERT INTO `shopping_mall_product` VALUES (3, 100001, 2, 'iphoneSE 64G', '副标题', 'iphoneSE的详细描述', NULL, NULL, 1888.00, 996, 1, '2019-01-20 13:51:58', '2019-01-20 15:55:22');

-- ----------------------------
-- Table structure for shopping_mall_store
-- ----------------------------
DROP TABLE IF EXISTS `shopping_mall_store`;
CREATE TABLE `shopping_mall_store`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seller_id` int(11) NOT NULL COMMENT '用户id',
  `category_id` int(11) NOT NULL COMMENT '店铺所属商品类别id',
  `store_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '店铺名',
  `status` int(4) NULL DEFAULT NULL COMMENT '店铺状态 0:店铺在售 1店铺关闭',
  `detail` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '店铺详情',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of shopping_mall_store
-- ----------------------------
INSERT INTO `shopping_mall_store` VALUES (1, 2, 100001, 'mac店', 0, '苹果笔记本店', '2019-01-20 12:54:29', '2019-01-20 12:54:29');
INSERT INTO `shopping_mall_store` VALUES (2, 2, 100012, 'iphone手机官方店', 1, '新款出品，价格美丽', '2019-01-20 12:44:11', '2019-01-20 12:44:11');

-- ----------------------------
-- Table structure for shopping_mall_trade_info
-- ----------------------------
DROP TABLE IF EXISTS `shopping_mall_trade_info`;
CREATE TABLE `shopping_mall_trade_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` bigint(20) NOT NULL COMMENT '订单号',
  `buyer_id` int(11) NOT NULL COMMENT '买家id',
  `seller_id` int(11) NOT NULL COMMENT '卖家id',
  `trade_platform` int(4) NULL DEFAULT NULL COMMENT '支付平台 1支付宝 2微信',
  `trade_number` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易流水号',
  `trade_status` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易状态 success failed',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of shopping_mall_trade_info
-- ----------------------------
INSERT INTO `shopping_mall_trade_info` VALUES (1, 1547480798821, 1, 2, 1, '2019011422001438650504972633', 'TRADE_SUCCESS', '2019-01-14 23:48:25', '2019-01-14 23:48:25');

-- ----------------------------
-- Table structure for shopping_mall_user
-- ----------------------------
DROP TABLE IF EXISTS `shopping_mall_user`;
CREATE TABLE `shopping_mall_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `realname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '真是姓名',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话',
  `question` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码找回问题',
  `answer` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码找回答案',
  `role` int(4) NOT NULL COMMENT '角色 0:买家 1:卖家 2:管理员',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of shopping_mall_user
-- ----------------------------
INSERT INTO `shopping_mall_user` VALUES (1, 'buyer', 'A1A%CE1E@91!021B@@!H020&A5AC!H18', '小陈', '11290@qq.com', '17751784', '1加1的结果', '2', 0, '2019-01-06 16:58:22', '2019-01-06 17:29:11');
INSERT INTO `shopping_mall_user` VALUES (2, 'seller', 'A1A%CE1E@91!021B@@!H020&A5AC!H18', '商家', '163@163.com', '1775', '商家问题', '答案', 1, '2019-01-06 21:16:52', '2019-01-06 21:16:52');
INSERT INTO `shopping_mall_user` VALUES (4, 'admin', 'A1A%CE1E@91!021B@@!H020&A5AC!H18', '管理员', 'seller@163.com', '17751784', '管理员问题', '答案', 2, '2019-01-06 21:20:08', '2019-01-06 21:20:08');
INSERT INTO `shopping_mall_user` VALUES (5, 'czn', 'A1A%CE1E@91!021B@@!H020&A5AC!H18', '老陈头', 'czn1223@163.com', '1772345', '最喜欢的电影', '功夫', 0, '2019-01-19 22:31:59', '2019-01-20 01:55:36');
INSERT INTO `shopping_mall_user` VALUES (6, 'seller-czn', 'A1A%CE1E@91!021B@@!H020&A5AC!H18', '老陈皮子头', 'seller12345@163.com', '1775178432', '最喜欢的演员', '周星驰', 1, '2019-01-20 01:59:55', '2019-01-20 01:59:55');
INSERT INTO `shopping_mall_user` VALUES (7, 'admin-czn', 'A1A%CE1E@91!021B@@!H020&A5AC!H18', '管理员头子', 'admin12345@163.com', '1775178432', '最喜欢的水果', '橘子', 2, '2019-01-20 02:08:22', '2019-01-20 02:08:22');

SET FOREIGN_KEY_CHECKS = 1;
