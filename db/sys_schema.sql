/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : shiro

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 15/03/2019 18:37:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_dept
-- ----------------------------
DROP TABLE IF EXISTS `tb_dept`;
CREATE TABLE `tb_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `parent_id` bigint(20) NOT NULL COMMENT '上级部门ID',
  `name` varchar(20) NOT NULL COMMENT '部门名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of tb_dept
-- ----------------------------
BEGIN;
INSERT INTO `tb_dept` VALUES (1, 0, '开发部', '2019-01-01 00:00:00');
INSERT INTO `tb_dept` VALUES (2, 1, '开发一部', '2019-02-02 13:08:33');
INSERT INTO `tb_dept` VALUES (3, 1, '开发二部', '2019-02-02 13:08:57');
INSERT INTO `tb_dept` VALUES (4, 0, '测试部', '2019-01-01 00:00:00');
INSERT INTO `tb_dept` VALUES (5, 4, '测试一部', '2019-02-02 13:09:11');
INSERT INTO `tb_dept` VALUES (6, 0, '人事部', '2019-01-01 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for tb_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_log`;
CREATE TABLE `tb_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `username` varchar(20) DEFAULT NULL COMMENT '操作用户',
  `operation` varchar(20) DEFAULT NULL COMMENT '操作描述',
  `time` bigint(20) DEFAULT NULL COMMENT '耗时(毫秒)',
  `method` varchar(100) DEFAULT NULL COMMENT '操作方法',
  `params` varchar(255) DEFAULT NULL COMMENT '操作参数',
  `ip` varchar(20) DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `location` varchar(20) DEFAULT NULL COMMENT '操作地点',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='系统日志表';

-- ----------------------------
-- Records of tb_log
-- ----------------------------
BEGIN;
INSERT INTO `tb_log` VALUES (1, 'admin', '查看用户列表', 20, 'cn.tycoding.system.controller.UserController.queryList()', ' queryPage\"QueryPage(pageCode=1, pageSize=6)\" user\"User(id=null, username=null, password=null, salt=null, deptId=null, deptName=null, createTime=null, modifyTime=null, avatar=null, phone=null, sex=null, description=null, status=null)\"', '127.0.0.1', '2019-03-13 00:42:34', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `tb_log` VALUES (7, 'admin', '更新用户', 83, 'cn.tycoding.system.controller.UserController.update()', ' user\"UserWithRole(roleId=1, roleIds=[1])\"', '127.0.0.1', '2019-03-13 01:21:48', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `tb_log` VALUES (10, 'admin', '删除用户', 65, 'cn.tycoding.system.controller.UserController.delete()', ' ids\"[9]\"', '127.0.0.1', '2019-03-13 05:00:56', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `tb_log` VALUES (11, 'admin', '删除用户', 9, 'cn.tycoding.system.controller.UserController.delete()', ' ids\"[9]\"', '127.0.0.1', '2019-03-13 05:01:18', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `tb_log` VALUES (12, 'admin', '删除登录日志', 39, 'cn.tycoding.monitor.controller.LoginLogController.delete()', ' ids\"[3]\"', '127.0.0.1', '2019-03-13 05:13:03', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `tb_log` VALUES (13, 'admin', '删除日志', 44, 'cn.tycoding.monitor.controller.LogController.delete()', ' ids\"[8]\"', '127.0.0.1', '2019-03-13 05:15:54', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `tb_log` VALUES (14, 'admin', '删除日志', 9, 'cn.tycoding.monitor.controller.LogController.delete()', ' ids\"[9]\"', '127.0.0.1', '2019-03-13 05:15:58', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `tb_log` VALUES (15, 'admin', '删除七牛云文件', 378, 'cn.tycoding.storage.controller.QiniuController.delete()', ' name\"ss1074261491006988288.jpg\"', '127.0.0.1', '2019-03-13 08:30:31', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `tb_log` VALUES (16, 'admin', '更新七牛云文件名', 430, 'cn.tycoding.storage.controller.QiniuController.update()', ' oldname\"ss1074213185631420416.png\" newname\"1074213185631420416.png\"', '127.0.0.1', '2019-03-13 08:32:56', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `tb_log` VALUES (17, 'admin', '上传七牛云文件', 1948, 'cn.tycoding.storage.controller.QiniuController.upload()', ' file\"org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@576e7f4b\" request: org.springframework.web.multipart.support.StandardMultipartHttpServletRequest@5ac44dd6', '127.0.0.1', '2019-03-13 19:14:19', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `tb_log` VALUES (18, 'admin', '添加用户', 96, 'cn.tycoding.system.controller.UserController.add()', ' user\"UserWithRole(roleId=null, roleIds=[3])\"', '127.0.0.1', '2019-03-14 08:27:07', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `tb_log` VALUES (19, 'admin', '文件上传', 7, 'cn.tycoding.storage.controller.UploadController.upload()', ' file\"org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@150e5550\" request: org.springframework.web.multipart.support.StandardMultipartHttpServletRequest@38af3773', '127.0.0.1', '2019-03-14 08:27:50', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `tb_log` VALUES (20, 'admin', '更新用户', 19, 'cn.tycoding.system.controller.UserController.update()', ' user\"UserWithRole(roleId=3, roleIds=[3])\"', '127.0.0.1', '2019-03-14 08:28:01', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `tb_log` VALUES (21, 'admin', '上传七牛云文件', 2087, 'cn.tycoding.storage.controller.QiniuController.upload()', ' file\"org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@367a9b8b\" request: org.springframework.web.multipart.support.StandardMultipartHttpServletRequest@322fa353', '127.0.0.1', '2019-03-14 08:29:17', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `tb_log` VALUES (22, 'admin', '更新用户', 19, 'cn.tycoding.system.controller.UserController.update()', ' user\"UserWithRole(roleId=3, roleIds=[3])\"', '127.0.0.1', '2019-03-14 08:29:21', '内网IP|0|0|内网IP|内网IP');
COMMIT;

-- ----------------------------
-- Table structure for tb_login_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_login_log`;
CREATE TABLE `tb_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `ip` varchar(20) DEFAULT NULL COMMENT 'IP地址',
  `location` varchar(255) DEFAULT NULL COMMENT '登录地点',
  `create_time` datetime DEFAULT NULL COMMENT '登录时间',
  `device` varchar(255) DEFAULT NULL COMMENT '登录设备',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_login_log
-- ----------------------------
BEGIN;
INSERT INTO `tb_login_log` VALUES (1, 'admin', '127.0.0.1', '内网IP|0|0|内网IP|内网IP', '2019-03-13 04:36:13', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `tb_login_log` VALUES (2, 'ss', NULL, NULL, NULL, NULL);
INSERT INTO `tb_login_log` VALUES (4, 'admin', '127.0.0.1', '内网IP|0|0|内网IP|内网IP', '2019-03-13 06:15:56', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `tb_login_log` VALUES (5, 'admin', '127.0.0.1', '内网IP|0|0|内网IP|内网IP', '2019-03-13 08:05:34', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `tb_login_log` VALUES (6, 'admin', '127.0.0.1', '内网IP|0|0|内网IP|内网IP', '2019-03-13 08:52:32', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `tb_login_log` VALUES (7, 'admin', '127.0.0.1', '内网IP|0|0|内网IP|内网IP', '2019-03-13 18:31:09', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `tb_login_log` VALUES (8, 'admin', '127.0.0.1', '内网IP|0|0|内网IP|内网IP', '2019-03-13 20:33:47', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `tb_login_log` VALUES (9, 'admin', '127.0.0.1', '内网IP|0|0|内网IP|内网IP', '2019-03-13 21:32:03', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `tb_login_log` VALUES (10, 'admin', '127.0.0.1', '内网IP|0|0|内网IP|内网IP', '2019-03-14 01:03:48', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `tb_login_log` VALUES (11, 'admin', '127.0.0.1', '内网IP|0|0|内网IP|内网IP', '2019-03-14 01:39:52', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `tb_login_log` VALUES (12, 'admin', '127.0.0.1', '内网IP|0|0|内网IP|内网IP', '2019-03-14 05:25:56', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `tb_login_log` VALUES (13, 'admin', '127.0.0.1', '内网IP|0|0|内网IP|内网IP', '2019-03-14 06:39:46', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `tb_login_log` VALUES (14, 'admin', '127.0.0.1', '内网IP|0|0|内网IP|内网IP', '2019-03-14 18:34:34', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `tb_login_log` VALUES (15, 'admin', '127.0.0.1', '内网IP|0|0|内网IP|内网IP', '2019-03-14 19:04:50', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `tb_login_log` VALUES (16, 'admin', '127.0.0.1', '内网IP|0|0|内网IP|内网IP', '2019-03-15 03:08:48', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `tb_login_log` VALUES (17, 'admin', '127.0.0.1', '内网IP|0|0|内网IP|内网IP', '2019-03-15 05:31:11', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
INSERT INTO `tb_login_log` VALUES (18, 'admin', '127.0.0.1', '内网IP|0|0|内网IP|内网IP', '2019-03-15 05:35:07', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36');
COMMIT;

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '资源编号',
  `name` varchar(20) NOT NULL COMMENT '资源名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `url` varchar(100) DEFAULT NULL COMMENT 'URL',
  `perms` text COMMENT '权限标识',
  `type` varchar(20) NOT NULL COMMENT '类型：如button按钮 menu菜单',
  `icon` varchar(30) DEFAULT NULL COMMENT '菜单图标',
  `priority` bigint(20) DEFAULT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态 0锁定 1有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=447 DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
BEGIN;
INSERT INTO `tb_menu` VALUES (1, '系统管理', 0, '', 'system', 'menu', 'fa fa-gear', 0, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (2, '用户管理', 1, '/system/user', 'user:list', 'menu', NULL, 1, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (3, '角色管理', 1, '/system/role', 'role:list', 'menu', NULL, 2, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (4, '菜单管理', 1, '/system/menu', 'menu:list', 'menu', NULL, 3, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (5, '部门管理', 1, '/system/dept', 'dept:list', 'menu', '', NULL, '2019-02-14 13:19:56', 1);
INSERT INTO `tb_menu` VALUES (10, '新增用户', 2, '', 'user:add', 'button', NULL, 1, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (11, '修改用户', 2, '', 'user:update', 'button', NULL, 2, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (12, '新增角色', 3, '', 'role:add', 'button', '', 1, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (13, '修改角色', 3, '', 'role:update', 'button', NULL, 2, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (111, '系统监控', 0, NULL, 'monitor', 'menu', 'fa fa-shield', 0, '2019-02-05 09:07:39', 1);
INSERT INTO `tb_menu` VALUES (112, '在线用户', 111, '/monitor/online', 'online:list', 'menu', NULL, NULL, '2019-02-14 14:29:53', 1);
INSERT INTO `tb_menu` VALUES (113, '登录日志', 111, '/monitor/loginlog', 'log:list', 'menu', NULL, NULL, '2019-02-14 14:31:54', 0);
INSERT INTO `tb_menu` VALUES (114, '系统日志', 111, '/monitor/log', 'log:list', 'menu', NULL, 1, '2019-02-05 09:09:36', 1);
INSERT INTO `tb_menu` VALUES (115, 'Redis监控', 111, '/monitor/redis/monitor', 'redis:info', 'menu', NULL, 1, '2019-02-05 09:10:28', 1);
INSERT INTO `tb_menu` VALUES (116, 'Druid监控', 111, '/monitor/druid', 'druid:view', 'menu', NULL, NULL, '2019-03-14 13:07:56', 1);
INSERT INTO `tb_menu` VALUES (222, '任务调度', 0, '', 'task', 'menu', 'fa fa-bell', 2, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (223, '定时任务', 222, '/task', 'task', 'menu', NULL, 1, '2019-02-05 09:23:28', 1);
INSERT INTO `tb_menu` VALUES (333, '对象储存', 0, NULL, 'storage', 'menu', 'fa fa-cloud', 1, '2019-02-05 08:49:45', 1);
INSERT INTO `tb_menu` VALUES (334, '七牛云', 333, '/storage/qiniu', 'qiniu:list', 'menu', '', 2, '2019-02-05 08:51:30', 1);
INSERT INTO `tb_menu` VALUES (444, '网络资源', 0, NULL, 'network', 'menu', 'fa fa-support', 1, '2019-02-05 09:23:24', 1);
INSERT INTO `tb_menu` VALUES (445, '天气查询', 444, '/web/weather', 'weather', 'menu', NULL, 1, '2019-02-05 09:25:06', 1);
INSERT INTO `tb_menu` VALUES (446, '影视资讯', 444, '/web/movie', 'movie:view', 'menu', NULL, NULL, '2019-03-14 11:38:01', 1);
COMMIT;

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(20) NOT NULL COMMENT '角色名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态 0锁定 1有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of tb_role
-- ----------------------------
BEGIN;
INSERT INTO `tb_role` VALUES (1, '经理', '2019-01-01 00:00:00', '项目经理', 1);
INSERT INTO `tb_role` VALUES (2, '组长', '2019-01-01 00:00:00', '项目开发组长', 1);
INSERT INTO `tb_role` VALUES (3, '测试员啊', '2019-01-01 00:00:00', '项目开发测试啊', 1);
INSERT INTO `tb_role` VALUES (4, '测试', '2019-02-14 08:51:48', '测试', 1);
INSERT INTO `tb_role` VALUES (5, '测试呀', '2019-02-14 02:54:56', '123', NULL);
INSERT INTO `tb_role` VALUES (6, '测试是', '2019-02-14 02:59:17', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单/按钮ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源关联表';

-- ----------------------------
-- Records of tb_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `tb_role_menu` VALUES (1, 1);
INSERT INTO `tb_role_menu` VALUES (1, 2);
INSERT INTO `tb_role_menu` VALUES (1, 3);
INSERT INTO `tb_role_menu` VALUES (1, 4);
INSERT INTO `tb_role_menu` VALUES (1, 5);
INSERT INTO `tb_role_menu` VALUES (1, 111);
INSERT INTO `tb_role_menu` VALUES (1, 112);
INSERT INTO `tb_role_menu` VALUES (1, 113);
INSERT INTO `tb_role_menu` VALUES (1, 114);
INSERT INTO `tb_role_menu` VALUES (1, 115);
INSERT INTO `tb_role_menu` VALUES (1, 116);
INSERT INTO `tb_role_menu` VALUES (1, 222);
INSERT INTO `tb_role_menu` VALUES (1, 223);
INSERT INTO `tb_role_menu` VALUES (1, 333);
INSERT INTO `tb_role_menu` VALUES (1, 334);
INSERT INTO `tb_role_menu` VALUES (1, 444);
INSERT INTO `tb_role_menu` VALUES (1, 445);
INSERT INTO `tb_role_menu` VALUES (1, 446);
INSERT INTO `tb_role_menu` VALUES (2, 2);
INSERT INTO `tb_role_menu` VALUES (2, 10);
INSERT INTO `tb_role_menu` VALUES (2, 11);
INSERT INTO `tb_role_menu` VALUES (2, 12);
INSERT INTO `tb_role_menu` VALUES (2, 13);
INSERT INTO `tb_role_menu` VALUES (3, 10);
INSERT INTO `tb_role_menu` VALUES (4, 446);
INSERT INTO `tb_role_menu` VALUES (6, 446);
COMMIT;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `salt` varchar(100) NOT NULL COMMENT '盐值',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别 0男 1女',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态 0锁定 1有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` VALUES (1, 'admin', 'e66118cc37a2d48679d2125786da4e32', '4bb248295857990678f15b9410e78e4d', 3, '2019-01-01 00:00:00', '2019-02-05 02:46:38', '/img/avatar/default.jpg', '19809587839', '0', '暂时没有', 1);
INSERT INTO `tb_user` VALUES (2, 'tycoding', '18caf8ff1512ad84f46053ac0f4561cf', 'ab2874470d60b14ed8f1248ab0e527ad', 5, '2019-01-01 00:00:00', '2019-02-05 02:47:26', '/img/avatar/20180414165754.jpg', '18798797687', '0', '暂时没有', 1);
INSERT INTO `tb_user` VALUES (5, 'tumo', '4416ceb6e10bf309149358e90a6ce6bb', 'a9d14a4b3c80b20c3042842393623384', 6, '2019-02-03 03:37:34', '2019-02-03 07:37:12', '/img/avatar/20180414165827.jpg', '781797907', '1', '测试账户', 1);
INSERT INTO `tb_user` VALUES (6, 'test', '', 'xxx', 6, '2019-02-05 02:38:31', '2019-03-13 01:21:48', '/img/avatar/20180414165815.jpg', '1892178297', '0', '测试账户', 1);
INSERT INTO `tb_user` VALUES (7, 'xxx', '', 'xxx', 6, '2019-03-14 08:27:07', '2019-03-14 08:29:21', 'http://img.api.tycoding.cn/1106185519820857344.png', '13173173701', '0', 'ss', 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
BEGIN;
INSERT INTO `tb_user_role` VALUES (1, 1);
INSERT INTO `tb_user_role` VALUES (1, 3);
INSERT INTO `tb_user_role` VALUES (2, 2);
INSERT INTO `tb_user_role` VALUES (5, 2);
INSERT INTO `tb_user_role` VALUES (5, 3);
INSERT INTO `tb_user_role` VALUES (6, 1);
INSERT INTO `tb_user_role` VALUES (7, 3);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
