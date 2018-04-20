/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost
 Source Database       : exper

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : utf-8

 Date: 04/16/2018 23:02:12 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_ optional`
-- ----------------------------
DROP TABLE IF EXISTS `t_ optional`;
CREATE TABLE `t_ optional` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `states` varchar(2) DEFAULT NULL COMMENT '选课1，取消0，完成2，提交3，教师评价4',
  `judge` varchar(200) DEFAULT NULL COMMENT '教师评价',
  `situation` varchar(200) DEFAULT NULL COMMENT '学生试验情况',
  `imageName` varchar(50) DEFAULT NULL COMMENT '提交图片名',
  `inageUrl` varchar(200) DEFAULT NULL COMMENT '提交地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_class`
-- ----------------------------
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE `t_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `className` varchar(30) DEFAULT NULL COMMENT '选课名',
  `classDate` varchar(20) DEFAULT NULL COMMENT '课程时间',
  `teacherId` int(11) DEFAULT NULL,
  `allowed` int(11) DEFAULT NULL COMMENT '名额',
  `duration` varchar(20) DEFAULT NULL,
  `selectDate` varchar(20) DEFAULT NULL COMMENT '选课时间',
  `experName` varchar(50) DEFAULT NULL COMMENT '实验名称',
  `experInfo` varchar(200) DEFAULT NULL COMMENT '试验资料',
  `experData` varchar(200) DEFAULT NULL COMMENT '实验数据',
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userno` varchar(30) DEFAULT NULL COMMENT '存roleId',
  `userName` varchar(30) DEFAULT NULL,
  `pwd` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `position` varchar(100) DEFAULT NULL,
  `sex` varchar(1) DEFAULT NULL,
  `telphone` varchar(15) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_user`
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES ('1', 'admin', 'admin', '123456', null, null, null, null, null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
