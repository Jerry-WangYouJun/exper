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

 Date: 04/20/2018 22:57:46 PM
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
  `duration` varchar(20) DEFAULT NULL COMMENT '持续时间',
  `selectDate` varchar(20) DEFAULT NULL COMMENT '选课时间',
  `experName` varchar(50) DEFAULT NULL COMMENT '实验名称',
  `experInfo` varchar(200) DEFAULT NULL COMMENT '试验资料',
  `experData` varchar(200) DEFAULT NULL COMMENT '实验数据',
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_class`
-- ----------------------------
BEGIN;
INSERT INTO `t_class` VALUES ('1', '英语', '2018-04-22 16:48:48', '4', '40', '30', '2018-04-30 22:43:34', null, '11111', '111', '111', ';123/rnull'), ('3', '数学', '2018-04-19 22:28:53', '3', '40', '40', '2018-04-30 22:28:58', '2018-04-17 22:47:47', null, null, null, '123');
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_optional`
-- ----------------------------
BEGIN;
INSERT INTO `t_optional` VALUES ('1', '1', '1', '1', '1', '1', '1', '1', '1111;1'), ('3', '2', '1', '0', '123123', '12323', 'DSC00601.JPG', null, 'null;1233;null;1233'), ('4', '1', '1', null, null, '123123', 'DSC00617.JPG', null, '1233'), ('5', '2', '1', '2', null, '11111', 'DSC00612.JPG', null, '123;null;123;null'), ('6', '2', '1', '0', null, null, null, null, null);
COMMIT;

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

-- ----------------------------
--  Records of `t_user`
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES ('1', '1', 'admin', '123456', null, null, null, null, null), ('2', '3', 'stu1', '123456', null, null, null, null, null), ('3', '2', 'teacher', '123456', null, null, null, null, null), ('4', '2', 'teacher2', '123456', null, null, null, null, null), ('5', '3', 'stu3', '123', null, null, null, null, null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
