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

 Date: 04/21/2018 09:48:03 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_class`
-- ----------------------------
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE `t_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `className` varchar(30) DEFAULT NULL COMMENT '选课名',
  `classDate` varchar(20) DEFAULT NULL COMMENT '课程开始时间',
  `teacherId` int(11) DEFAULT NULL,
  `allowed` int(11) DEFAULT NULL COMMENT '名额',
  `rest` int(11) DEFAULT NULL COMMENT '剩余名额',
  `duration` varchar(20) DEFAULT NULL COMMENT '时段',
  `selectDate` varchar(20) DEFAULT NULL COMMENT '选课时间',
  `experName` varchar(50) DEFAULT NULL COMMENT '实验名称',
  `experInfo` varchar(200) DEFAULT NULL COMMENT '试验资料',
  `experData` varchar(200) DEFAULT NULL COMMENT '实验数据',
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_class_parent`
-- ----------------------------
DROP TABLE IF EXISTS `t_class_parent`;
CREATE TABLE `t_class_parent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `className` varchar(50) DEFAULT NULL COMMENT '课程名称',
  `startTime` varchar(20) DEFAULT NULL,
  `endTime` varchar(20) DEFAULT NULL,
  `allowed` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_exper`
-- ----------------------------
DROP TABLE IF EXISTS `t_exper`;
CREATE TABLE `t_exper` (
  `id` int(11) NOT NULL,
  `classId` int(11) DEFAULT NULL COMMENT '课程ID',
  `experName` varchar(30) DEFAULT NULL COMMENT '实验名称',
  `experData` varchar(500) DEFAULT NULL COMMENT '实验数据',
  `experInfo` varchar(500) DEFAULT NULL COMMENT '实验资料',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_optional`
-- ----------------------------
DROP TABLE IF EXISTS `t_optional`;
CREATE TABLE `t_optional` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `classId` int(11) DEFAULT NULL,
  `states` varchar(2) DEFAULT NULL COMMENT '选课1，取消0，完成2，评价3，教师评价4',
  `judge` varchar(200) DEFAULT NULL COMMENT '教师评价',
  `situation` varchar(200) DEFAULT NULL COMMENT '学生试验情况',
  `imageName` varchar(50) DEFAULT NULL COMMENT '提交图片名',
  `imageUrl` varchar(200) DEFAULT NULL COMMENT '提交地址',
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(30) DEFAULT NULL COMMENT '1 管理员  2  教师  3 学生',
  `userName` varchar(30) DEFAULT NULL,
  `pwd` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `position` varchar(100) DEFAULT NULL,
  `sex` varchar(1) DEFAULT NULL,
  `telphone` varchar(15) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
