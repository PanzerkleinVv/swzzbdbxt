/*
Navicat MySQL Data Transfer

Source Server         : swzzbdbxt
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : swzzbdbxt

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-08-10 18:07:35
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
INSERT INTO `attach` VALUES ('01c369a93a863e09b94b7d7cb5c767803c2347d2', 'cd64382336b9fa33031cd913a48406f08829b172', '1', 'ceregreset.exe', '2018-07-28 02:45:18');
INSERT INTO `attach` VALUES ('0e35c7c521dce97f17db76d932156762998128a2', '7effad8c721ceda9505be4ac83d1e9aa6327c50d', '0', '编辑1.TXT', '2018-07-28 01:01:49');
INSERT INTO `attach` VALUES ('15d9d4d84144e33f5bbe4358736c2e16dec8c24f', 'af09e40076a4398262c1afdfb9ae57d7ac1f8a6b', '1', 'allochook-x86_64.dll', '2018-07-28 02:42:10');
INSERT INTO `attach` VALUES ('2057b0517b67808b16288254c62795191ba1e728', '7effad8c721ceda9505be4ac83d1e9aa6327c50d', '0', '光大银行10元观影.png', '2018-07-28 01:01:49');
INSERT INTO `attach` VALUES ('245bd7a21fa4daeba54df8b32e9e572bfeb0d3c1', 'cd64382336b9fa33031cd913a48406f08829b172', '1', 'celua.txt', '2018-07-28 02:45:07');
INSERT INTO `attach` VALUES ('612f4e143403cca2adcea7a36ca866c216e7f2a0', 'af09e40076a4398262c1afdfb9ae57d7ac1f8a6b', '1', 'ced3d11hook.dll', '2018-07-28 02:42:28');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` varchar(255) NOT NULL COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '操作用户主键',
  `target_id` varchar(255) DEFAULT NULL COMMENT '主办或协办处室表单主键',
  `log_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '日志时间',
  `content` varchar(255) DEFAULT NULL COMMENT '日志内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('60df600431c113dc4f4508a948c63fa36a70025d', '2', 'cd64382336b9fa33031cd913a48406f08829b172', '2018-07-30 11:04:09', '研究室内勤');
INSERT INTO `log` VALUES ('c293ae6f1bf9b27c19f9042c1613616d44a39dee', '2', 'cd64382336b9fa33031cd913a48406f08829b172', '2018-07-30 10:53:33', '研究室内勤');
INSERT INTO `log` VALUES ('e2a3d3ebe2d286e421cc3af6db2f4aaab1f63e0a', '5', 'af09e40076a4398262c1afdfb9ae57d7ac1f8a6b', '2018-07-30 11:01:27', '办公室');
INSERT INTO `log` VALUES ('ed0704f76dbbd6c8290d8e33d5257f761bf81e39', '5', 'af09e40076a4398262c1afdfb9ae57d7ac1f8a6b', '2018-07-30 11:01:35', '办公室');
INSERT INTO `log` VALUES ('f9d9daf3e89553f72d73a9647859325364cb6f17', '5', 'af09e40076a4398262c1afdfb9ae57d7ac1f8a6b', '2018-07-30 11:02:10', '办公室');

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
  `content` mediumtext COMMENT '办理情况（作废）',
  `limit_time` datetime DEFAULT NULL COMMENT '办理期限',
  `end_time` datetime DEFAULT NULL COMMENT '办结日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of msg
-- ----------------------------
INSERT INTO `msg` VALUES ('0d553ed30e5728e968cc9635586d2e20fc70a708', '20180073', '2018-08-02 00:00:00', 'a', '立项依据3', null, null, null);
INSERT INTO `msg` VALUES ('7effad8c721ceda9505be4ac83d1e9aa6327c50d', '20180071', '2018-07-27 00:00:00', '测试1', '立项依据1', null, '2018-07-29 00:00:00', '2018-07-30 11:09:27');

-- ----------------------------
-- Table structure for msg_co-sponsor
-- ----------------------------
DROP TABLE IF EXISTS `msg_co-sponsor`;
CREATE TABLE `msg_co-sponsor` (
  `id` varchar(255) NOT NULL COMMENT '表id',
  `msg_id` varchar(255) DEFAULT NULL COMMENT '关联督办事项id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '关联处室id',
  `is_signed` int(11) DEFAULT NULL COMMENT '是否签收 0--否 1--是',
  `is_assigned` int(11) DEFAULT NULL COMMENT '是否分派 0--否 1--是',
  `content` mediumtext COMMENT '办理情况',
  `status` int(11) DEFAULT NULL,
  `limit_time` datetime DEFAULT NULL COMMENT '办理期限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='督办事项协办处室表';

-- ----------------------------
-- Records of msg_co-sponsor
-- ----------------------------
INSERT INTO `msg_co-sponsor` VALUES ('af09e40076a4398262c1afdfb9ae57d7ac1f8a6b', '7effad8c721ceda9505be4ac83d1e9aa6327c50d', '3', '1', '0', '<p>aaaaaaaaaaaaaaaaaaawwwwwwwwwwwwwwwwwwwwdaaaaaaadadaa</p>', '1', '2018-11-22 00:00:00');
INSERT INTO `msg_co-sponsor` VALUES ('bf98966bdc993437589df919784a0c0f40a2394f', '0d553ed30e5728e968cc9635586d2e20fc70a708', '6', '0', '0', '', '0', '2018-08-15 00:00:00');
INSERT INTO `msg_co-sponsor` VALUES ('f5aab1c56b46e6deb8db5f0d0826db67bb5c6f62', '7effad8c721ceda9505be4ac83d1e9aa6327c50d', '5', '0', '0', '', '1', '2018-07-29 00:00:00');

-- ----------------------------
-- Table structure for msg_contractor
-- ----------------------------
DROP TABLE IF EXISTS `msg_contractor`;
CREATE TABLE `msg_contractor` (
  `id` varchar(255) NOT NULL COMMENT '表id',
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
  `id` varchar(255) NOT NULL COMMENT '表id',
  `msg_id` varchar(255) DEFAULT NULL COMMENT '关联督办事项id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '关联处室id',
  `is_signed` int(11) DEFAULT NULL COMMENT '是否签收 0--否 1--是',
  `is_assigned` int(11) DEFAULT NULL COMMENT '是否分派 0--否 1--是',
  `content` mediumtext COMMENT '办理情况',
  `status` int(11) DEFAULT NULL,
  `limit_time` datetime DEFAULT NULL COMMENT '办理期限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='督办事项主办处室表';

-- ----------------------------
-- Records of msg_sponsor
-- ----------------------------
INSERT INTO `msg_sponsor` VALUES ('081781fb69c4d9ce45033dbd07e529705f2d2b33', '7effad8c721ceda9505be4ac83d1e9aa6327c50d', '2', '0', '0', '', '1', '2018-07-29 00:00:00');
INSERT INTO `msg_sponsor` VALUES ('8af16ad04fb49fff42224dfe92c2eefbc42a48e5', '0d553ed30e5728e968cc9635586d2e20fc70a708', '3', '0', '0', '', '0', '2018-08-15 00:00:00');
INSERT INTO `msg_sponsor` VALUES ('cd64382336b9fa33031cd913a48406f08829b172', '7effad8c721ceda9505be4ac83d1e9aa6327c50d', '4', '1', '0', '<p>啊发送到</p><p>发达撒adadsaafa</p>', '1', '2018-09-20 00:00:00');
INSERT INTO `msg_sponsor` VALUES ('f25a0a63103fcbe18c6547b531bc8aad17391d04', '7effad8c721ceda9505be4ac83d1e9aa6327c50d', '7', '0', '0', '', '1', '2018-07-29 00:00:00');

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
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8 COMMENT='提醒表';

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('129', '2', '0', '7effad8c721ceda9505be4ac83d1e9aa6327c50d', '1', '2018-07-30 11:04:09', '0');
INSERT INTO `notice` VALUES ('138', '1', '5', '0d553ed30e5728e968cc9635586d2e20fc70a708', '0', '2018-08-09 16:17:52', '0');
INSERT INTO `notice` VALUES ('139', '6', '5', '0d553ed30e5728e968cc9635586d2e20fc70a708', '0', '2018-08-09 16:17:52', '1');
INSERT INTO `notice` VALUES ('140', '5', '5', '0d553ed30e5728e968cc9635586d2e20fc70a708', '0', '2018-08-09 16:17:52', '1');

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
INSERT INTO `sequence_number` VALUES ('2018', '73');

-- ----------------------------
-- Table structure for submission
-- ----------------------------
DROP TABLE IF EXISTS `submission`;
CREATE TABLE `submission` (
  `id` varchar(255) NOT NULL COMMENT '表id',
  `msg_id` varchar(255) DEFAULT NULL COMMENT '关联主办、协办id',
  `type` int(11) DEFAULT NULL COMMENT '提请类型 1--办结 2--延期 3--中止 4--阶段性办结',
  `situation` text COMMENT '情况反馈 for：4种提请类型',
  `reason` text COMMENT '理由反馈 for:延期、中止、阶段性办结',
  `measure` text COMMENT '措施反馈 for：延期',
  `owner_id` bigint(20) DEFAULT NULL COMMENT '提请发起人userid',
  `superior_verify_passed` int(11) DEFAULT NULL COMMENT '办公室审核是否通过 0--否 1--是',
  `superior_verifi_user_id` bigint(20) DEFAULT NULL COMMENT '办公室审核人userid',
  `status` int(11) DEFAULT NULL COMMENT '状态 0--草稿 1--提请 2--办公室已审核',
  `send_time` datetime DEFAULT NULL COMMENT '发出时间',
  `limit_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '延期 期限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提请表';

-- ----------------------------
-- Records of submission
-- ----------------------------
INSERT INTO `submission` VALUES ('483a60e5566f68faf886f5f9432c401370b4b127', 'af09e40076a4398262c1afdfb9ae57d7ac1f8a6b', '2', '', '', '', '5', '1', '5', '2', '2018-07-30 11:09:27', '2018-11-22 00:00:00');
INSERT INTO `submission` VALUES ('95480ed5b73ea898954ad909a393419969745a50', 'cd64382336b9fa33031cd913a48406f08829b172', '2', '', '', '', '2', '1', '5', '2', '2018-07-30 09:42:02', '2018-07-30 09:42:20');
INSERT INTO `submission` VALUES ('f75c2f4a0bcefb8ab843eeb477d017b5159fb36d', 'af09e40076a4398262c1afdfb9ae57d7ac1f8a6b', '2', '', '', '', '5', '1', '1', '2', '2018-07-28 12:49:28', '2018-07-30 09:05:54');
INSERT INTO `submission` VALUES ('fcd6a1e659cac0665022514945f77ed78b2d9ee7', 'af09e40076a4398262c1afdfb9ae57d7ac1f8a6b', '2', '', '', '', '5', '1', '5', '2', '2018-07-30 09:42:48', '2018-07-30 09:42:55');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名 英文 用于大组工网关联',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `userdesc` varchar(50) DEFAULT NULL COMMENT '用户姓名 用于中文名显示',
  `state` int(11) DEFAULT NULL COMMENT '状态 1--启用 0--停用',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '职务id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间 自动生成',
  `role_id` bigint(20) DEFAULT NULL COMMENT '处室id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '信息处技术员', '1', '1', '2014-07-17 12:59:08', '1');
INSERT INTO `user` VALUES ('2', '11111', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '研究室内勤', '1', '5', '2018-05-03 12:45:42', '4');
INSERT INTO `user` VALUES ('3', 'zz21', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '组织二处内勤', '1', '5', '2018-05-09 15:59:46', '6');
INSERT INTO `user` VALUES ('4', 'zz22', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '组织二处test', '1', '6', '2018-05-09 16:00:13', '6');
INSERT INTO `user` VALUES ('5', 'bgs', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '办公室', '1', '3', '2018-05-09 18:02:22', '3');
INSERT INTO `user` VALUES ('6', 'bld', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '部领导', '1', '2', '2018-08-01 14:14:11', '2');

-- ----------------------------
-- Table structure for user_permission
-- ----------------------------
DROP TABLE IF EXISTS `user_permission`;
CREATE TABLE `user_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '角色id',
  `permission_id` bigint(20) unsigned DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色与权限关联表';

-- ----------------------------
-- Records of user_permission
-- ----------------------------
INSERT INTO `user_permission` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(20) unsigned DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户与角色关联表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');

-- ----------------------------
-- View structure for analysis_all_role
-- ----------------------------
DROP VIEW IF EXISTS `analysis_all_role`;
CREATE ALGORITHM=UNDEFINED DEFINER=`swzzbdbxt`@`` SQL SECURITY DEFINER  VIEW `analysis_all_role` AS SELECT role_id, type, sum( CASE WHEN STATUS = 1 THEN 1 ELSE 0 END ) AS onwork, sum( CASE WHEN STATUS = 2 THEN 1 ELSE 0 END ) AS overtime, sum( CASE WHEN STATUS = 3 THEN 1 ELSE 0 END ) AS partial_done, sum( CASE WHEN STATUS = 4 THEN 1 ELSE 0 END ) AS done, sum( CASE WHEN STATUS = 5 THEN 1 ELSE 0 END ) AS `stop`, 0 AS `year`, 0 AS `month` FROM (( SELECT t0.*, 1 AS type FROM msg_sponsor t0 ) UNION ( SELECT t1.*, 2 AS type FROM `msg_co-sponsor` t1 )) main GROUP BY role_id, type ORDER BY role_id, type ASC ;

-- ----------------------------
-- View structure for analysis_all_year
-- ----------------------------
DROP VIEW IF EXISTS `analysis_all_year`;
CREATE ALGORITHM=UNDEFINED DEFINER=`swzzbdbxt`@``  VIEW `analysis_all_year` AS SELECT 0 AS role_id, 0 AS type, sum( CASE WHEN STATUS = 1 THEN 1 ELSE 0 END ) AS onwork, sum( CASE WHEN STATUS = 2 THEN 1 ELSE 0 END ) AS overtime, sum( CASE WHEN STATUS = 3 THEN 1 ELSE 0 END ) AS partial_done, sum( CASE WHEN STATUS = 4 THEN 1 ELSE 0 END ) AS done, sum( CASE WHEN STATUS = 5 THEN 1 ELSE 0 END ) AS `stop`, `year`, `month` FROM ( SELECT YEAR, MONTH, ct.id AS id, CASE WHEN FIND_IN_SET(2, GROUP_CONCAT(STATUS)) > 0 THEN 2 WHEN FIND_IN_SET(1, GROUP_CONCAT(STATUS)) > 0 THEN 1 WHEN FIND_IN_SET(3, GROUP_CONCAT(STATUS)) > 0 THEN 3 WHEN FIND_IN_SET(4, GROUP_CONCAT(STATUS)) > 0 THEN 4 ELSE 5 END AS STATUS FROM (( SELECT t0.* FROM msg_sponsor t0 WHERE STATUS <> 0 ) UNION ( SELECT t1.* FROM `msg_co-sponsor` t1 WHERE STATUS <> 0 )) main LEFT OUTER JOIN ( SELECT date_format(create_time, '%Y') AS YEAR, date_format(create_time, '%m') AS MONTH, id FROM msg ) ct ON main.msg_id = ct.id GROUP BY YEAR, MONTH, id ) status_table GROUP BY YEAR, MONTH, id ORDER BY YEAR DESC, MONTH ASC ;

-- ----------------------------
-- View structure for analysis_year_role
-- ----------------------------
DROP VIEW IF EXISTS `analysis_year_role`;
CREATE ALGORITHM=UNDEFINED DEFINER=`swzzbdbxt`@``  VIEW `analysis_year_role` AS SELECT role_id, type, sum( CASE WHEN STATUS = 1 THEN 1 ELSE 0 END ) AS onwork, sum( CASE WHEN STATUS = 2 THEN 1 ELSE 0 END ) AS overtime, sum( CASE WHEN STATUS = 3 THEN 1 ELSE 0 END ) AS partial_done, sum( CASE WHEN STATUS = 4 THEN 1 ELSE 0 END ) AS done, sum( CASE WHEN STATUS = 5 THEN 1 ELSE 0 END ) AS `stop`, `year`, `month` FROM (( SELECT t0.*, 1 AS type FROM msg_sponsor t0 WHERE STATUS <> 0 ) UNION ( SELECT t1.*, 2 AS type FROM `msg_co-sponsor` t1 WHERE STATUS <> 0 )) main LEFT OUTER JOIN ( SELECT date_format(create_time, '%Y') AS YEAR, date_format(create_time, '%m') AS MONTH, id FROM msg ) ct ON main.msg_id = ct.id GROUP BY YEAR, MONTH, role_id, type ORDER BY YEAR DESC, MONTH ASC, role_id ASC, type ASC ;

-- ----------------------------
-- View structure for msg_role
-- ----------------------------
DROP VIEW IF EXISTS `msg_role`;
CREATE ALGORITHM=UNDEFINED DEFINER=`swzzbdbxt`@``  VIEW `msg_role` AS select * , 1 as type from msg_sponsor union select * , 2 as type from `msg_co-sponsor` ;
