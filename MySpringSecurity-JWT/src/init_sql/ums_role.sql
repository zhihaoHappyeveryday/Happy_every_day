/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : mall

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 15/01/2020 23:27:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ums_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `admin_count` int(11) NULL DEFAULT NULL COMMENT '后台用户数量',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `status` int(1) NULL DEFAULT 1 COMMENT '启用状态：0->禁用；1->启用',
  `sort` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_role
-- ----------------------------
INSERT INTO `ums_role` VALUES (1, '商品管理员', '商品管理员', 0, '2018-09-30 15:46:11', 1, 0);
INSERT INTO `ums_role` VALUES (2, '商品分类管理员', '商品分类管理员', 0, '2018-09-30 15:53:45', 1, 0);
INSERT INTO `ums_role` VALUES (3, '商品类型管理员', '商品类型管理员', 0, '2018-09-30 15:53:56', 1, 0);
INSERT INTO `ums_role` VALUES (4, '品牌管理员', '品牌管理员', 0, '2018-09-30 15:54:12', 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
