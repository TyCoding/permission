DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`(
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `salt` varchar(100) NOT NULL COMMENT '盐值',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别 0男 1女',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `status` tinyint(1) DEFAULT 0 COMMENT '状态 0锁定 1有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户表';

INSERT INTO `tb_user` VALUES (1, 'admin', 'admin', 'xx', 1, '2019-01-01 00:00:00', 'https://tycoding.cn/author/avatar.png', '19809587839', '0', '', 1);
INSERT INTO `tb_user` VALUES (2, 'tycoding', '123', 'xx', 1, '2019-01-01 00:00:00', 'https://tycoding.cn/author/avatar.png', '18798797687', '0', '', 1);

DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(20) NOT NULL COMMENT '角色名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `status` tinyint(1) DEFAULT 0 COMMENT '状态 0锁定 1有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '角色表';

INSERT INTO `tb_role` VALUES (1, '经理', '2019-01-01 00:00:00', '', 1);
INSERT INTO `tb_role` VALUES (2, '组长', '2019-01-01 00:00:00', '', 1);

DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '资源编号',
  `name` varchar(20) NOT NULL COMMENT '资源名称',
  `parent_id` bigint DEFAULT NULL COMMENT '父级ID',
  `url` varchar(100) DEFAULT NULL COMMENT 'URL',
  `perms` text COMMENT '权限标识',
  `type` varchar(20) NOT NULL COMMENT '类型：如button按钮 menu菜单',
  `priority` bigint DEFAULT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `status` tinyint(1) DEFAULT 0 COMMENT '状态 0锁定 1有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '资源表';

INSERT INTO `tb_menu` VALUES (1, '系统管理', 0, '', 'system', 'button', 0, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (2, '用户管理', 1, '/user', 'user:list', 'button', 1, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (3, '角色管理', 1, '/role', 'role:list', 'button', 2, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (4, '资源管理', 1, '/menu', 'menu:list', 'button', 3, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (10, '新增用户', 2, '', 'user:add', 'button', 1, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (11, '修改用户', 2, '', 'user:update', 'button', 2, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (12, '新增角色', 3, '', 'role:add', 'button', 1, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (13, '修改角色', 3, '', 'role:update', 'button', 2, '2019-01-01 00:00:00', 1);
INSERT INTO `tb_menu` VALUES (111, '任务调度', 0, '/task', 'task', 'button', 2, '2019-01-01 00:00:00', 1);

DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户角色关联表';

INSERT INTO `tb_user_role` VALUES (1, 1);
INSERT INTO `tb_user_role` VALUES (2, 2);

DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单/按钮ID',
  PRIMARY KEY (`role_id`, `menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '角色资源关联表';

INSERT INTO `tb_role_menu` VALUES (1, 1);
INSERT INTO `tb_role_menu` VALUES (1, 2);
INSERT INTO `tb_role_menu` VALUES (1, 3);
INSERT INTO `tb_role_menu` VALUES (1, 4);
INSERT INTO `tb_role_menu` VALUES (2, 2);
INSERT INTO `tb_role_menu` VALUES (2, 10);
INSERT INTO `tb_role_menu` VALUES (2, 11);
INSERT INTO `tb_role_menu` VALUES (2, 12);
INSERT INTO `tb_role_menu` VALUES (2, 13);

DROP TABLE IF EXISTS `tb_dept`;
CREATE TABLE `tb_dept` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `parent_id` bigint NOT NULL COMMENT '上级部门ID',
  `name` varchar(20) NOT NULL COMMENT '部门名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '部门表';

INSERT INTO `tb_dept` VALUES (1, 0, '开发部', '2019-01-01 00:00:00');
INSERT INTO `tb_dept` VALUES (2, 0, '测试部', '2019-01-01 00:00:00');
INSERT INTO `tb_dept` VALUES (3, 0, '人事部', '2019-01-01 00:00:00');

