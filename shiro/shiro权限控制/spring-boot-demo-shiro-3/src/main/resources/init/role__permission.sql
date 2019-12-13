/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 50540
 Source Host           : localhost:3306
 Source Schema         : shiro

 Target Server Type    : MySQL
 Target Server Version : 50540
 File Encoding         : 65001

 Date: 13/12/2019 17:32:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role__permission
-- ----------------------------
DROP TABLE IF EXISTS `role__permission`;
CREATE TABLE `role__permission`  (
  `rid` int(10) NULL DEFAULT NULL COMMENT '角色id',
  `pid` int(10) NULL DEFAULT NULL COMMENT '权限id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role__permission
-- ----------------------------
INSERT INTO `role__permission` VALUES (1, 2);
INSERT INTO `role__permission` VALUES (1, 3);
INSERT INTO `role__permission` VALUES (2, 1);
INSERT INTO `role__permission` VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;
