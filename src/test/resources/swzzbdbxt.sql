/*
Navicat MySQL Data Transfer

Source Server         : swzzbdbxt
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : swzzbdbxt

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-04-18 10:12:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for attach
-- ----------------------------
DROP TABLE IF EXISTS `attach`;
CREATE TABLE `attach` (
  `id` varchar(255) NOT NULL COMMENT '表id',
  `target_id` varchar(255) DEFAULT NULL COMMENT '目标id msg_id 或sub_id',
  `target_type` int(11) DEFAULT NULL COMMENT '目标类型 0--msg 1--submission',
  `attach_file_name` varchar(255) DEFAULT NULL COMMENT '附件名',
  `create_time` datetime DEFAULT NULL COMMENT '生成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';

-- ----------------------------
-- Records of attach
-- ----------------------------

-- ----------------------------
-- Table structure for msg
-- ----------------------------
DROP TABLE IF EXISTS `msg`;
CREATE TABLE `msg` (
  `id` varchar(255) NOT NULL COMMENT '表id',
  `sequence` int(255) DEFAULT NULL COMMENT '立项号 ex：20180001',
  `create_time` datetime DEFAULT NULL COMMENT '发布时间',
  `name` varchar(255) DEFAULT NULL COMMENT '督办事项',
  `basis` varchar(255) DEFAULT NULL COMMENT '立项依据',
  `content` mediumtext COMMENT '办理情况',
  `limit_time` datetime DEFAULT NULL COMMENT '办理期限',
  `end_time` datetime DEFAULT NULL COMMENT '办结日期',
  `status` int(11) DEFAULT NULL COMMENT '办理状态 0--草稿 1--在办 2--办结 3--逾期 4--中止 5--阶段性办结',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of msg
-- ----------------------------

-- ----------------------------
-- Table structure for msg_co-sponsor
-- ----------------------------
DROP TABLE IF EXISTS `msg_co-sponsor`;
CREATE TABLE `msg_co-sponsor` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '表id',
  `msg_id` varchar(255) DEFAULT NULL COMMENT '关联督办事项id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '关联处室id',
  `is_signed` int(11) DEFAULT NULL COMMENT '是否签收 0--否 1--是',
  `is_assigned` int(11) DEFAULT NULL COMMENT '是否分派 0--否 1--是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='督办事项协办处室表';

-- ----------------------------
-- Records of msg_co-sponsor
-- ----------------------------

-- ----------------------------
-- Table structure for msg_contractor
-- ----------------------------
DROP TABLE IF EXISTS `msg_contractor`;
CREATE TABLE `msg_contractor` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '表id',
  `msg_id` varchar(255) DEFAULT NULL COMMENT '关联督办事项id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '关联user_id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='督办事项承办人表';

-- ----------------------------
-- Records of msg_contractor
-- ----------------------------

-- ----------------------------
-- Table structure for msg_sponsor
-- ----------------------------
DROP TABLE IF EXISTS `msg_sponsor`;
CREATE TABLE `msg_sponsor` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '表id',
  `msg_id` varchar(255) DEFAULT NULL COMMENT '关联督办事项id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '关联处室id',
  `is_signed` int(11) DEFAULT NULL COMMENT '是否签收 0--否 1--是',
  `is_assigned` int(11) DEFAULT NULL COMMENT '是否分派 0--否 1--是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='督办事项主办处室表';

-- ----------------------------
-- Records of msg_sponsor
-- ----------------------------

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '提醒关联用户id',
  `type` int(11) DEFAULT NULL COMMENT '提醒类型 -1--动态更新 0--逾期 1--待审核 2--待签收 3--待指派 4--草稿箱',
  `target_id` varchar(255) DEFAULT NULL COMMENT '关联目标id msg_id 或 sub_id',
  `target_type` int(11) DEFAULT NULL COMMENT '目标类型 0--msg 1--submission',
  `create_time` datetime DEFAULT NULL COMMENT '生成时间',
  `is_read` int(11) DEFAULT NULL COMMENT '是否已读 0--已读 1--未读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提醒表';

-- ----------------------------
-- Records of notice
-- ----------------------------

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `permission_name` varchar(32) DEFAULT NULL COMMENT '权限名',
  `permission_sign` varchar(128) DEFAULT NULL COMMENT '权限标识,程序中判断使用,如"user:create"',
  `description` varchar(256) DEFAULT NULL COMMENT '权限描述,UI界面显示使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '管理员', 'admin', '管理员');
INSERT INTO `permission` VALUES ('2', '部领导', '1-1', '部领导');
INSERT INTO `permission` VALUES ('3', '办公室管理', '1-2', '办公室管理');
INSERT INTO `permission` VALUES ('4', '处室负责人', '2-1', '处室负责人');
INSERT INTO `permission` VALUES ('5', '处室内勤', '2-2', '处室内勤');
INSERT INTO `permission` VALUES ('6', '承办人', '3', '承办人');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '处室id',
  `role_name` varchar(32) DEFAULT NULL COMMENT '处室名',
  `role_sign` varchar(128) DEFAULT NULL COMMENT '处室标识,程序中判断使用,如"admin"',
  `description` varchar(256) DEFAULT NULL COMMENT '处室描述,UI界面显示使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='处室表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员', 'admin', '管理员');
INSERT INTO `role` VALUES ('2', '部领导', '1', '部领导');
INSERT INTO `role` VALUES ('3', '办公室', '2', '办公室');
INSERT INTO `role` VALUES ('4', '研究室', '3', '研究室');
INSERT INTO `role` VALUES ('5', '组织一处', '4', '组织一处');
INSERT INTO `role` VALUES ('6', '组织二处', '5', '组织二处');
INSERT INTO `role` VALUES ('7', '干部一处', '6', '干部一处');
INSERT INTO `role` VALUES ('8', '市县干部一处', '7', '市县干部一处');
INSERT INTO `role` VALUES ('9', '市县干部二处', '8', '市县干部二处');
INSERT INTO `role` VALUES ('10', '干部三处', '9', '干部三处');
INSERT INTO `role` VALUES ('11', '干部四处', '10', '干部四处');
INSERT INTO `role` VALUES ('12', '干部五处', '11', '干部五处');
INSERT INTO `role` VALUES ('13', '干部六处', '12', '干部六处');
INSERT INTO `role` VALUES ('14', '干部培训处', '13', '干部培训处');
INSERT INTO `role` VALUES ('15', '干部监督室', '14', '干部监督室');
INSERT INTO `role` VALUES ('16', '人才工作处', '15', '人才工作处');
INSERT INTO `role` VALUES ('17', '信息处', '16', '信息处');
INSERT INTO `role` VALUES ('18', '驻部纪检组', '17', '驻部纪检组');

-- ----------------------------
-- Table structure for sequence_number
-- ----------------------------
DROP TABLE IF EXISTS `sequence_number`;
CREATE TABLE `sequence_number` (
  `id` int(11) unsigned NOT NULL COMMENT '表id --年 ex：2018',
  `count` int(11) DEFAULT NULL COMMENT '立项号计数 每次+1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sequence_number
-- ----------------------------

-- ----------------------------
-- Table structure for submission
-- ----------------------------
DROP TABLE IF EXISTS `submission`;
CREATE TABLE `submission` (
  `id` varchar(255) NOT NULL COMMENT '表id',
  `msg_id` varchar(255) DEFAULT NULL COMMENT '关联督办事项id',
  `type` int(11) DEFAULT NULL COMMENT '提请类型 1--办结 2--延期 3--中止 4--阶段性办结',
  `situation` text COMMENT '情况反馈 for：4种提请类型',
  `reason` text COMMENT '理由反馈 for:延期、中止、阶段性办结',
  `measure` text COMMENT '措施反馈 for：延期',
  `owner_id` bigint(20) DEFAULT NULL COMMENT '提请发起人userid',
  `superior_verify_passed` int(11) DEFAULT NULL COMMENT '办公室审核是否通过 0--否 1--是',
  `superior_verifi_user_id` bigint(20) DEFAULT NULL COMMENT '办公室审核人userid',
  `status` int(11) DEFAULT NULL COMMENT '状态 0--草稿 1--提请 2--处室已审核 3--办公室已审核',
  `send_time` datetime DEFAULT NULL COMMENT '发出时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提请表';

-- ----------------------------
-- Records of submission
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名 英文 用于大组工网关联',
  `password` char(64) DEFAULT NULL COMMENT '密码',
  `userdesc` varchar(50) DEFAULT NULL COMMENT '用户姓名 用于中文名显示',
  `state` int(11) DEFAULT NULL COMMENT '状态 1--启用 0--停用',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '职务id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间 自动生成',
  `role_id` bigint(20) DEFAULT NULL COMMENT '处室id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '信息处技术员', '1', null, '2014-07-17 12:59:08', null);
