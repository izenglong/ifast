/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost
 Source Database       : ifast_new

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : utf-8

 Date: 07/21/2018 16:24:57 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `app_demo_base`
-- ----------------------------
DROP TABLE IF EXISTS `app_demo_base`;
CREATE TABLE `app_demo_base` (
  `id` bigint(20) NOT NULL COMMENT '编号',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除标记',
  `version` smallint(6) DEFAULT '0' COMMENT '版本号',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础表';

-- ----------------------------
--  Table structure for `app_user`
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `realName` varchar(20) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `uname` varchar(20) DEFAULT NULL,
  `passwd` varchar(100) DEFAULT NULL,
  `openid` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `app_user`
-- ----------------------------
BEGIN;
INSERT INTO `app_user` VALUES ('1', 'Aron', '15277779999', '2018-04-01 21:59:50', '2018-05-02 21:06:51', 'Aron', 'Aron', 'oy03r0BhZIURZGTNfHZfwijCXkYQ');
COMMIT;

-- ----------------------------
--  Table structure for `sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `k` varchar(100) DEFAULT NULL COMMENT '键',
  `v` varchar(1000) DEFAULT NULL COMMENT '值',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `kvType` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_config`
-- ----------------------------
BEGIN;
INSERT INTO `sys_config` VALUES ('2', 'oss_qiniu', '{\"AccessKey\" : \"8-HMj9EgGNIP-xuOCpSzTn-OMyGOFtR3TxLdn4Uu\",\"SecretKey\" : \"SjpGg3V6PsMdJgn42PeEd5Ik-6aNyuwdqV5CPM6A\",\"bucket\" : \"ifast\",\"AccessUrl\" : \"http://p6r7ke2jc.bkt.clouddn.com/\"}', '七牛对象存储配置', '2018-04-06 14:31:26', '4300'), ('3', 'author', 'Aron', '代码生成器配置', '2018-05-27 19:57:04', '4401'), ('4', 'email', 'izenglong@163.com', '代码生成器配置', '2018-05-27 19:57:04', '4401'), ('5', 'package', 'com.ifast', '代码生成器配置', '2018-05-27 19:57:04', '4401'), ('6', 'autoRemovePre', 'true', '代码生成器配置', '2018-05-27 19:57:04', '4401'), ('7', 'tablePrefix', 'app', '代码生成器配置', '2018-05-27 19:57:04', '4401'), ('8', 'tinyint', 'Integer', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('9', 'smallint', 'Integer', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('10', 'mediumint', 'Integer', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('11', 'int', 'Integer', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('12', 'integer', 'Integer', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('13', 'bigint', 'Long', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('14', 'float', 'Float', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('15', 'double', 'Double', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('16', 'decimal', 'BigDecimal', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('17', 'bit', 'Boolean', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('18', 'char', 'String', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('19', 'varchar', 'String', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('20', 'tinytext', 'String', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('21', 'text', 'String', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('22', 'mediumtext', 'String', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('23', 'longtext', 'String', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('24', 'date', 'Date', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('25', 'datetime', 'Date', '代码生成器配置', '2018-05-27 19:57:04', '4400'), ('26', 'timestamp', 'Date', '代码生成器配置', '2018-05-27 19:57:04', '4400');
COMMIT;

-- ----------------------------
--  Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parentId` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `orderNum` int(11) DEFAULT NULL COMMENT '排序',
  `delFlag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='部门管理';

-- ----------------------------
--  Records of `sys_dept`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES ('6', '0', '研发部', '1', '1'), ('7', '6', '研发一部', '1', '1'), ('8', '6', '研发二部', '2', '1'), ('9', '0', '销售部', '2', '1'), ('11', '0', '产品部', '3', '1'), ('12', '11', '产品一部', '1', '1'), ('13', '0', '测试部', '5', '1'), ('14', '13', '测试一部', '1', '1'), ('15', '13', '测试二部', '2', '1'), ('16', '9', '销售一部', '0', '1');
COMMIT;

-- ----------------------------
--  Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '标签名',
  `value` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '数据值',
  `type` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `description` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序（升序）',
  `parentId` bigint(64) DEFAULT '0' COMMENT '父级编号',
  `createBy` int(64) DEFAULT NULL COMMENT '创建者',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `updateBy` bigint(64) DEFAULT NULL COMMENT '更新者',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `delFlag` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`name`),
  KEY `sys_dict_del_flag` (`delFlag`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='字典表';

-- ----------------------------
--  Records of `sys_dict`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES ('1', '正常', '0', 'del_flag', '删除标记', '10', '0', '1', null, '1', null, null, '0'), ('3', '显示', '1', 'show_hide', '显示/隐藏', '10', '0', '1', null, '1', null, null, '0'), ('4', '隐藏', '0', 'show_hide', '显示/隐藏', '20', '0', '1', null, '1', null, null, '0'), ('5', '是', '1', 'yes_no', '是/否', '10', '0', '1', null, '1', null, null, '0'), ('6', '否', '0', 'yes_no', '是/否', '20', '0', '1', null, '1', null, null, '0'), ('7', '红色', 'red', 'color', '颜色值', '10', '0', '1', null, '1', null, null, '0'), ('8', '绿色', 'green', 'color', '颜色值', '20', '0', '1', null, '1', null, null, '0'), ('9', '蓝色', 'blue', 'color', '颜色值', '30', '0', '1', null, '1', null, null, '0'), ('10', '黄色', 'yellow', 'color', '颜色值', '40', '0', '1', null, '1', null, null, '0'), ('11', '橙色', 'orange', 'color', '颜色值', '50', '0', '1', null, '1', null, null, '0'), ('12', '默认主题', 'default', 'theme', '主题方案', '10', '0', '1', null, '1', null, null, '0'), ('13', '天蓝主题', 'cerulean', 'theme', '主题方案', '20', '0', '1', null, '1', null, null, '0'), ('14', '橙色主题', 'readable', 'theme', '主题方案', '30', '0', '1', null, '1', null, null, '0'), ('15', '红色主题', 'united', 'theme', '主题方案', '40', '0', '1', null, '1', null, null, '0'), ('16', 'Flat主题', 'flat', 'theme', '主题方案', '60', '0', '1', null, '1', null, null, '0'), ('17', '国家', '1', 'sys_area_type', '区域类型', '10', '0', '1', null, '1', null, null, '0'), ('18', '省份、直辖市', '2', 'sys_area_type', '区域类型', '20', '0', '1', null, '1', null, null, '0'), ('19', '地市', '3', 'sys_area_type', '区域类型', '30', '0', '1', null, '1', null, null, '0'), ('20', '区县', '4', 'sys_area_type', '区域类型', '40', '0', '1', null, '1', null, null, '0'), ('21', '公司', '1', 'sys_office_type', '机构类型', '60', '0', '1', null, '1', null, null, '0'), ('22', '部门', '2', 'sys_office_type', '机构类型', '70', '0', '1', null, '1', null, null, '0'), ('23', '小组', '3', 'sys_office_type', '机构类型', '80', '0', '1', null, '1', null, null, '0'), ('24', '其它', '4', 'sys_office_type', '机构类型', '90', '0', '1', null, '1', null, null, '0'), ('25', '综合部', '1', 'sys_office_common', '快捷通用部门', '30', '0', '1', null, '1', null, null, '0'), ('26', '开发部', '2', 'sys_office_common', '快捷通用部门', '40', '0', '1', null, '1', null, null, '0'), ('27', '人力部', '3', 'sys_office_common', '快捷通用部门', '50', '0', '1', null, '1', null, null, '0'), ('28', '一级', '1', 'sys_office_grade', '机构等级', '10', '0', '1', null, '1', null, null, '0'), ('29', '二级', '2', 'sys_office_grade', '机构等级', '20', '0', '1', null, '1', null, null, '0'), ('30', '三级', '3', 'sys_office_grade', '机构等级', '30', '0', '1', null, '1', null, null, '0'), ('31', '四级', '4', 'sys_office_grade', '机构等级', '40', '0', '1', null, '1', null, null, '0'), ('32', '所有数据', '1', 'sys_data_scope', '数据范围', '10', '0', '1', null, '1', null, null, '0'), ('33', '所在公司及以下数据', '2', 'sys_data_scope', '数据范围', '20', '0', '1', null, '1', null, null, '0'), ('34', '所在公司数据', '3', 'sys_data_scope', '数据范围', '30', '0', '1', null, '1', null, null, '0'), ('35', '所在部门及以下数据', '4', 'sys_data_scope', '数据范围', '40', '0', '1', null, '1', null, null, '0'), ('36', '所在部门数据', '5', 'sys_data_scope', '数据范围', '50', '0', '1', null, '1', null, null, '0'), ('37', '仅本人数据', '8', 'sys_data_scope', '数据范围', '90', '0', '1', null, '1', null, null, '0'), ('38', '按明细设置', '9', 'sys_data_scope', '数据范围', '100', '0', '1', null, '1', null, null, '0'), ('39', '系统管理', '1', 'sys_user_type', '用户类型', '10', '0', '1', null, '1', null, null, '0'), ('40', '部门经理', '2', 'sys_user_type', '用户类型', '20', '0', '1', null, '1', null, null, '0'), ('41', '普通用户', '3', 'sys_user_type', '用户类型', '30', '0', '1', null, '1', null, null, '0'), ('42', '基础主题', 'basic', 'cms_theme', '站点主题', '10', '0', '1', null, '1', null, null, '0'), ('43', '蓝色主题', 'blue', 'cms_theme', '站点主题', '20', '0', '1', null, '1', null, null, '1'), ('44', '红色主题', 'red', 'cms_theme', '站点主题', '30', '0', '1', null, '1', null, null, '1'), ('45', '文章模型', 'article', 'cms_module', '栏目模型', '10', '0', '1', null, '1', null, null, '0'), ('46', '图片模型', 'picture', 'cms_module', '栏目模型', '20', '0', '1', null, '1', null, null, '1'), ('47', '下载模型', 'download', 'cms_module', '栏目模型', '30', '0', '1', null, '1', null, null, '1'), ('48', '链接模型', 'link', 'cms_module', '栏目模型', '40', '0', '1', null, '1', null, null, '0'), ('49', '专题模型', 'special', 'cms_module', '栏目模型', '50', '0', '1', null, '1', null, null, '1'), ('50', '默认展现方式', '0', 'cms_show_modes', '展现方式', '10', '0', '1', null, '1', null, null, '0'), ('51', '首栏目内容列表', '1', 'cms_show_modes', '展现方式', '20', '0', '1', null, '1', null, null, '0'), ('52', '栏目第一条内容', '2', 'cms_show_modes', '展现方式', '30', '0', '1', null, '1', null, null, '0'), ('53', '发布', '0', 'cms_del_flag', '内容状态', '10', '0', '1', null, '1', null, null, '0'), ('54', '删除', '1', 'cms_del_flag', '内容状态', '20', '0', '1', null, '1', null, null, '0'), ('55', '审核', '2', 'cms_del_flag', '内容状态', '15', '0', '1', null, '1', null, null, '0'), ('56', '首页焦点图', '1', 'cms_posid', '推荐位', '10', '0', '1', null, '1', null, null, '0'), ('57', '栏目页文章推荐', '2', 'cms_posid', '推荐位', '20', '0', '1', null, '1', null, null, '0'), ('58', '咨询', '1', 'cms_guestbook', '留言板分类', '10', '0', '1', null, '1', null, null, '0'), ('59', '建议', '2', 'cms_guestbook', '留言板分类', '20', '0', '1', null, '1', null, null, '0'), ('60', '投诉', '3', 'cms_guestbook', '留言板分类', '30', '0', '1', null, '1', null, null, '0'), ('61', '其它', '4', 'cms_guestbook', '留言板分类', '40', '0', '1', null, '1', null, null, '0'), ('62', '公休', '1', 'oa_leave_type', '请假类型', '10', '0', '1', null, '1', null, null, '0'), ('63', '病假', '2', 'oa_leave_type', '请假类型', '20', '0', '1', null, '1', null, null, '0'), ('64', '事假', '3', 'oa_leave_type', '请假类型', '30', '0', '1', null, '1', null, null, '0'), ('65', '调休', '4', 'oa_leave_type', '请假类型', '40', '0', '1', null, '1', null, null, '0'), ('66', '婚假', '5', 'oa_leave_type', '请假类型', '60', '0', '1', null, '1', null, null, '0'), ('67', '接入日志', '1', 'sys_log_type', '日志类型', '30', '0', '1', null, '1', null, null, '0'), ('68', '异常日志', '2', 'sys_log_type', '日志类型', '40', '0', '1', null, '1', null, null, '0'), ('69', '请假流程', 'leave', 'act_type', '流程类型', '10', '0', '1', null, '1', null, null, '0'), ('70', '审批测试流程', 'test_audit', 'act_type', '流程类型', '20', '0', '1', null, '1', null, null, '0'), ('71', '分类1', '1', 'act_category', '流程分类', '10', '0', '1', null, '1', null, null, '0'), ('72', '分类2', '2', 'act_category', '流程分类', '20', '0', '1', null, '1', null, null, '0'), ('73', '增删改查', 'crud', 'gen_category', '代码生成分类', '10', '0', '1', null, '1', null, null, '1'), ('74', '增删改查（包含从表）', 'crud_many', 'gen_category', '代码生成分类', '20', '0', '1', null, '1', null, null, '1'), ('75', '树结构', 'tree', 'gen_category', '代码生成分类', '30', '0', '1', null, '1', null, null, '1'), ('76', '=', '=', 'gen_query_type', '查询方式', '10', '0', '1', null, '1', null, null, '1'), ('77', '!=', '!=', 'gen_query_type', '查询方式', '20', '0', '1', null, '1', null, null, '1'), ('78', '&gt;', '&gt;', 'gen_query_type', '查询方式', '30', '0', '1', null, '1', null, null, '1'), ('79', '&lt;', '&lt;', 'gen_query_type', '查询方式', '40', '0', '1', null, '1', null, null, '1'), ('80', 'Between', 'between', 'gen_query_type', '查询方式', '50', '0', '1', null, '1', null, null, '1'), ('81', 'Like', 'like', 'gen_query_type', '查询方式', '60', '0', '1', null, '1', null, null, '1'), ('82', 'Left Like', 'left_like', 'gen_query_type', '查询方式', '70', '0', '1', null, '1', null, null, '1'), ('83', 'Right Like', 'right_like', 'gen_query_type', '查询方式', '80', '0', '1', null, '1', null, null, '1'), ('84', '文本框', 'input', 'gen_show_type', '字段生成方案', '10', '0', '1', null, '1', null, null, '1'), ('85', '文本域', 'textarea', 'gen_show_type', '字段生成方案', '20', '0', '1', null, '1', null, null, '1'), ('86', '下拉框', 'select', 'gen_show_type', '字段生成方案', '30', '0', '1', null, '1', null, null, '1'), ('87', '复选框', 'checkbox', 'gen_show_type', '字段生成方案', '40', '0', '1', null, '1', null, null, '1'), ('88', '单选框', 'radiobox', 'gen_show_type', '字段生成方案', '50', '0', '1', null, '1', null, null, '1'), ('89', '日期选择', 'dateselect', 'gen_show_type', '字段生成方案', '60', '0', '1', null, '1', null, null, '1'), ('90', '人员选择', 'userselect', 'gen_show_type', '字段生成方案', '70', '0', '1', null, '1', null, null, '1'), ('91', '部门选择', 'officeselect', 'gen_show_type', '字段生成方案', '80', '0', '1', null, '1', null, null, '1'), ('92', '区域选择', 'areaselect', 'gen_show_type', '字段生成方案', '90', '0', '1', null, '1', null, null, '1'), ('93', 'String', 'String', 'gen_java_type', 'Java类型', '10', '0', '1', null, '1', null, null, '1'), ('94', 'Long', 'Long', 'gen_java_type', 'Java类型', '20', '0', '1', null, '1', null, null, '1'), ('95', '仅持久层', 'dao', 'gen_category', '代码生成分类\0\0', '40', '0', '1', null, '1', null, null, '1'), ('96', '男', '1', 'sex', '性别', '10', '0', '1', null, '1', null, null, '0'), ('97', '女', '2', 'sex', '性别', '20', '0', '1', null, '1', null, null, '0'), ('98', 'Integer', 'Integer', 'gen_java_type', 'Java类型', '30', '0', '1', null, '1', null, null, '1'), ('99', 'Double', 'Double', 'gen_java_type', 'Java类型', '40', '0', '1', null, '1', null, null, '1'), ('100', 'Date', 'java.util.Date', 'gen_java_type', 'Java类型', '50', '0', '1', null, '1', null, null, '1'), ('104', 'Custom', 'Custom', 'gen_java_type', 'Java类型', '90', '0', '1', null, '1', null, null, '1'), ('105', '会议通告', '1', 'oa_notify_type', '通知通告类型', '10', '0', '1', null, '1', null, null, '0'), ('106', '奖惩通告', '2', 'oa_notify_type', '通知通告类型', '20', '0', '1', null, '1', null, null, '0'), ('107', '活动通告', '3', 'oa_notify_type', '通知通告类型', '30', '0', '1', null, '1', null, null, '0'), ('108', '草稿', '0', 'oa_notify_status', '通知通告状态', '10', '0', '1', null, '1', null, null, '0'), ('109', '发布', '1', 'oa_notify_status', '通知通告状态', '20', '0', '1', null, '1', null, null, '0'), ('110', '未读', '0', 'oa_notify_read', '通知通告状态', '10', '0', '1', null, '1', null, null, '0'), ('111', '已读', '1', 'oa_notify_read', '通知通告状态', '20', '0', '1', null, '1', null, null, '0'), ('112', '草稿', '0', 'oa_notify_status', '通知通告状态', '10', '0', '1', null, '1', null, '', '0'), ('113', '删除', '0', 'del_flag', '删除标记', null, null, null, null, null, null, '', ''), ('118', '关于', 'about', 'blog_type', '博客类型', null, null, null, null, null, null, '全url是:/blog/open/page/about', ''), ('119', '交流', 'communication', 'blog_type', '博客类型', null, null, null, null, null, null, '', ''), ('120', '文章', 'article', 'blog_type', '博客类型', null, null, null, null, null, null, '', '');
COMMIT;

-- ----------------------------
--  Table structure for `sys_file`
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL COMMENT '文件类型',
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1020581767367860226 DEFAULT CHARSET=utf8 COMMENT='文件上传';

-- ----------------------------
--  Records of `sys_file`
-- ----------------------------
BEGIN;
INSERT INTO `sys_file` VALUES ('140', '0', 'http://p6r7ke2jc.bkt.clouddn.com/ifast/20180406/cat003.jpeg', '2018-04-06 17:58:03'), ('141', '0', 'http://p6r7ke2jc.bkt.clouddn.com/ifast/20180406/cat002-1523009188140.jpeg', '2018-04-06 18:06:28'), ('148', '0', 'http://p6r7ke2jc.bkt.clouddn.com/ifast/20180406/cd09920f-7d51-4c60-a3a1-c36c83b3dfb4-1523028484072.png', '2018-04-06 23:28:05');
COMMIT;

-- ----------------------------
--  Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `time` int(11) DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `gmtCreate` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1020582052588920834 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
--  Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parentId` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `orderNum` int(11) DEFAULT NULL COMMENT '排序',
  `gmtCreate` datetime DEFAULT NULL COMMENT '创建时间',
  `gmtModified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
--  Records of `sys_menu`
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES ('1', '0', '基础管理', '', '', '0', 'fa fa-bars', '0', '2017-08-09 22:49:47', null), ('2', '3', '系统菜单', 'sys/menu/', 'sys:menu:menu', '1', 'fa fa-th-list', '2', '2017-08-09 22:55:15', null), ('3', '0', '系统管理', null, null, '0', 'fa fa-desktop', '1', '2017-08-09 23:06:55', '2017-08-14 14:13:43'), ('6', '3', '用户管理', 'sys/user/', 'sys:user:user', '1', 'fa fa-user', '0', '2017-08-10 14:12:11', null), ('7', '3', '角色管理', 'sys/role', 'sys:role:role', '1', 'fa fa-paw', '1', '2017-08-10 14:13:19', null), ('12', '6', '新增', '', 'sys:user:add', '2', '', '0', '2017-08-14 10:51:35', null), ('13', '6', '编辑', '', 'sys:user:edit', '2', '', '0', '2017-08-14 10:52:06', null), ('14', '6', '删除', null, 'sys:user:remove', '2', null, '0', '2017-08-14 10:52:24', null), ('15', '7', '新增', '', 'sys:role:add', '2', '', '0', '2017-08-14 10:56:37', null), ('20', '2', '新增', '', 'sys:menu:add', '2', '', '0', '2017-08-14 10:59:32', null), ('21', '2', '编辑', '', 'sys:menu:edit', '2', '', '0', '2017-08-14 10:59:56', null), ('22', '2', '删除', '', 'sys:menu:remove', '2', '', '0', '2017-08-14 11:00:26', null), ('24', '6', '批量删除', '', 'sys:user:batchRemove', '2', '', '0', '2017-08-14 17:27:18', null), ('25', '6', '停用', null, 'sys:user:disable', '2', null, '0', '2017-08-14 17:27:43', null), ('26', '6', '重置密码', '', 'sys:user:resetPwd', '2', '', '0', '2017-08-14 17:28:34', null), ('27', '91', '系统日志', 'common/log', 'common:log', '1', 'fa fa-warning', '0', '2017-08-14 22:11:53', null), ('28', '27', '刷新', null, 'sys:log:list', '2', null, '0', '2017-08-14 22:30:22', null), ('29', '27', '删除', null, 'sys:log:remove', '2', null, '0', '2017-08-14 22:30:43', null), ('30', '27', '清空', null, 'sys:log:clear', '2', null, '0', '2017-08-14 22:31:02', null), ('48', '77', '代码生成', 'common/generator', 'common:generator', '1', 'fa fa-code', '3', null, null), ('55', '7', '编辑', '', 'sys:role:edit', '2', '', null, null, null), ('56', '7', '删除', '', 'sys:role:remove', '2', null, null, null, null), ('57', '91', '运行监控', '/druid/index.html', '', '1', 'fa fa-caret-square-o-right', '1', null, null), ('61', '2', '批量删除', '', 'sys:menu:batchRemove', '2', null, null, null, null), ('62', '7', '批量删除', '', 'sys:role:batchRemove', '2', null, null, null, null), ('71', '1', '文件管理', '/common/sysFile', 'oss:file:file', '1', 'fa fa-folder-open', '2', null, null), ('72', '77', '计划任务', 'common/job', 'common:taskScheduleJob', '1', 'fa fa-hourglass-1', '4', null, null), ('73', '3', '部门管理', '/sys/dept', 'system:sysDept:sysDept', '1', 'fa fa-users', '3', null, null), ('74', '73', '增加', '/sys/dept/add', 'system:sysDept:add', '2', null, '1', null, null), ('75', '73', '刪除', 'sys/dept/remove', 'system:sysDept:remove', '2', null, '2', null, null), ('76', '73', '编辑', '/sys/dept/edit', 'system:sysDept:edit', '2', null, '3', null, null), ('77', '0', '系统工具', '', '', '0', 'fa fa-gear', '4', null, null), ('78', '1', '数据字典', '/common/sysDict', 'common:sysDict:sysDict', '1', 'fa fa-book', '1', null, null), ('79', '78', '增加', '/common/sysDict/add', 'common:sysDict:add', '2', null, '2', null, null), ('80', '78', '编辑', '/common/sysDict/edit', 'common:sysDict:edit', '2', null, '2', null, null), ('81', '78', '删除', '/common/sysDict/remove', 'common:sysDict:remove', '2', '', '3', null, null), ('83', '78', '批量删除', '/common/sysDict/batchRemove', 'common:sysDict:batchRemove', '2', '', '4', null, null), ('91', '0', '系统监控', '', '', '0', 'fa fa-video-camera', '5', null, null), ('92', '91', '在线用户', 'sys/online', '', '1', 'fa fa-user', null, null, null), ('97', '0', '图表管理', '', '', '0', 'fa fa-bar-chart', '7', null, null), ('98', '97', '百度chart', '/chart/graph_echarts.html', '', '1', 'fa fa-area-chart', null, null, null), ('151', '1', '公司信息管理', '/sign/appCompany', null, '1', 'fa fa-file-code-o', '6', null, null), ('152', '151', '查看', null, 'sign:appCompany:appCompany', '2', null, '6', null, null), ('153', '151', '新增', null, 'sign:appCompany:add', '2', null, '6', null, null), ('154', '151', '修改', null, 'sign:appCompany:edit', '2', null, '6', null, null), ('155', '151', '删除', null, 'sign:appCompany:remove', '2', null, '6', null, null), ('156', '151', '批量删除', null, 'sign:appCompany:batchRemove', '2', null, '6', null, null), ('157', '1', '出差管理', '/sign/appOutWork', null, '1', 'fa fa-file-code-o', '6', null, null), ('158', '157', '查看', null, 'sign:appOutWork:appOutWork', '2', null, '6', null, null), ('159', '157', '新增', null, 'sign:appOutWork:add', '2', null, '6', null, null), ('160', '157', '修改', null, 'sign:appOutWork:edit', '2', null, '6', null, null), ('161', '157', '删除', null, 'sign:appOutWork:remove', '2', null, '6', null, null), ('162', '157', '批量删除', null, 'sign:appOutWork:batchRemove', '2', null, '6', null, null), ('163', '1', '考勤管理', '/sign/appSign', null, '1', 'fa fa-file-code-o', '6', null, null), ('164', '163', '查看', null, 'sign:appSign:appSign', '2', null, '6', null, null), ('165', '163', '新增', null, 'sign:appSign:add', '2', null, '6', null, null), ('166', '163', '修改', null, 'sign:appSign:edit', '2', null, '6', null, null), ('167', '163', '删除', null, 'sign:appSign:remove', '2', null, '6', null, null), ('168', '163', '批量删除', null, 'sign:appSign:batchRemove', '2', null, '6', null, null), ('169', '1', '员工管理', '/sign/appUser', null, '1', 'fa fa-file-code-o', '6', null, null), ('170', '169', '查看', null, 'sign:appUser:appUser', '2', null, '6', null, null), ('171', '169', '新增', null, 'sign:appUser:add', '2', null, '6', null, null), ('172', '169', '修改', null, 'sign:appUser:edit', '2', null, '6', null, null), ('173', '169', '删除', null, 'sign:appUser:remove', '2', null, '6', null, null), ('174', '169', '批量删除', null, 'sign:appUser:batchRemove', '2', null, '6', null, null), ('175', '1', '系统配置', '/common/config', null, '1', 'fa fa-file-code-o', '6', null, null), ('176', '175', '查看', null, 'common:config:config', '2', null, '6', null, null), ('177', '175', '新增', null, 'common:config:add', '2', null, '6', null, null), ('178', '175', '修改', null, 'common:config:edit', '2', null, '6', null, null), ('179', '175', '删除', null, 'common:config:remove', '2', null, '6', null, null), ('180', '175', '批量删除', null, 'common:config:batchRemove', '2', null, '6', null, null), ('181', '199', '公众号管理', '/wxmp/mpConfig', null, '1', 'fa fa-file-code-o', '6', null, null), ('182', '181', '查看', null, 'wxmp:mpConfig:mpConfig', '2', null, '6', null, null), ('183', '181', '新增', null, 'wxmp:mpConfig:add', '2', null, '6', null, null), ('184', '181', '修改', null, 'wxmp:mpConfig:edit', '2', null, '6', null, null), ('185', '181', '删除', null, 'wxmp:mpConfig:remove', '2', null, '6', null, null), ('186', '181', '批量删除', null, 'wxmp:mpConfig:batchRemove', '2', null, '6', null, null), ('187', '199', '微信粉丝表', '/wxmp/mpFans', null, '1', 'fa fa-file-code-o', '6', null, null), ('188', '187', '查看', null, 'wxmp:mpFans:mpFans', '2', null, '6', null, null), ('189', '187', '新增', null, 'wxmp:mpFans:add', '2', null, '6', null, null), ('190', '187', '修改', null, 'wxmp:mpFans:edit', '2', null, '6', null, null), ('191', '187', '删除', null, 'wxmp:mpFans:remove', '2', null, '6', null, null), ('192', '187', '批量删除', null, 'wxmp:mpFans:batchRemove', '2', null, '6', null, null), ('193', '71', '增加', '/common/sysFile/add', 'oss:file:add', '2', null, null, null, null), ('194', '71', '列表', '/common/sysFile/list', 'oss:file:list', '2', null, null, null, null), ('195', '71', '编辑', '/common/sysFile/edit', 'oss:file:update', '2', null, null, null, null), ('196', '71', '查询', '/common/sysFile/info', 'oss:file:info', '2', null, null, null, null), ('197', '71', '删除', '/common/sysFile/remove', 'oss:file:remove', '2', null, null, null, null), ('199', '1', '', '/temp/configCopy', null, '1', 'fa fa-file-code-o', '6', null, null), ('205', '187', '同步', null, 'wxmp:mpFans:sync', '2', 'fa fa-refresh', '5', null, null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `roleSign` varchar(100) DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `userIdCreate` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `gmtCreate` datetime DEFAULT NULL COMMENT '创建时间',
  `gmtModified` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', '超级用户角色', 'admin', '超级管理员', '2', '2017-08-12 00:43:52', '2017-08-12 19:14:59'), ('56', '普通用户', null, '普通用户', null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menuId` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4359 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
--  Records of `sys_role_menu`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES ('367', '44', '1'), ('368', '44', '32'), ('369', '44', '33'), ('370', '44', '34'), ('371', '44', '35'), ('372', '44', '28'), ('373', '44', '29'), ('374', '44', '30'), ('375', '44', '38'), ('376', '44', '4'), ('377', '44', '27'), ('378', '45', '38'), ('379', '46', '3'), ('380', '46', '20'), ('381', '46', '21'), ('382', '46', '22'), ('383', '46', '23'), ('384', '46', '11'), ('385', '46', '12'), ('386', '46', '13'), ('387', '46', '14'), ('388', '46', '24'), ('389', '46', '25'), ('390', '46', '26'), ('391', '46', '15'), ('392', '46', '2'), ('393', '46', '6'), ('394', '46', '7'), ('598', '50', '38'), ('632', '38', '42'), ('737', '51', '38'), ('738', '51', '39'), ('739', '51', '40'), ('740', '51', '41'), ('741', '51', '4'), ('742', '51', '32'), ('743', '51', '33'), ('744', '51', '34'), ('745', '51', '35'), ('746', '51', '27'), ('747', '51', '28'), ('748', '51', '29'), ('749', '51', '30'), ('750', '51', '1'), ('1064', '54', '53'), ('1095', '55', '2'), ('1096', '55', '6'), ('1097', '55', '7'), ('1098', '55', '3'), ('1099', '55', '50'), ('1100', '55', '49'), ('1101', '55', '1'), ('1856', '53', '28'), ('1857', '53', '29'), ('1858', '53', '30'), ('1859', '53', '27'), ('1860', '53', '57'), ('1861', '53', '71'), ('1862', '53', '48'), ('1863', '53', '72'), ('1864', '53', '1'), ('1865', '53', '7'), ('1866', '53', '55'), ('1867', '53', '56'), ('1868', '53', '62'), ('1869', '53', '15'), ('1870', '53', '2'), ('1871', '53', '61'), ('1872', '53', '20'), ('1873', '53', '21'), ('1874', '53', '22'), ('2247', '63', '-1'), ('2248', '63', '84'), ('2249', '63', '85'), ('2250', '63', '88'), ('2251', '63', '87'), ('2252', '64', '84'), ('2253', '64', '89'), ('2254', '64', '88'), ('2255', '64', '87'), ('2256', '64', '86'), ('2257', '64', '85'), ('2258', '65', '89'), ('2259', '65', '88'), ('2260', '65', '86'), ('2262', '67', '48'), ('2263', '68', '88'), ('2264', '68', '87'), ('2265', '69', '89'), ('2266', '69', '88'), ('2267', '69', '86'), ('2268', '69', '87'), ('2269', '69', '85'), ('2270', '69', '84'), ('2271', '70', '85'), ('2272', '70', '89'), ('2273', '70', '88'), ('2274', '70', '87'), ('2275', '70', '86'), ('2276', '70', '84'), ('2277', '71', '87'), ('2278', '72', '59'), ('2279', '73', '48'), ('2280', '74', '88'), ('2281', '74', '87'), ('2282', '75', '88'), ('2283', '75', '87'), ('2284', '76', '85'), ('2285', '76', '89'), ('2286', '76', '88'), ('2287', '76', '87'), ('2288', '76', '86'), ('2289', '76', '84'), ('2292', '78', '88'), ('2293', '78', '87'), ('2294', '78', null), ('2295', '78', null), ('2296', '78', null), ('2308', '80', '87'), ('2309', '80', '86'), ('2310', '80', '-1'), ('2311', '80', '84'), ('2312', '80', '85'), ('2328', '79', '72'), ('2329', '79', '48'), ('2330', '79', '77'), ('2331', '79', '84'), ('2332', '79', '89'), ('2333', '79', '88'), ('2334', '79', '87'), ('2335', '79', '86'), ('2336', '79', '85'), ('2337', '79', '-1'), ('2338', '77', '89'), ('2339', '77', '88'), ('2340', '77', '87'), ('2341', '77', '86'), ('2342', '77', '85'), ('2343', '77', '84'), ('2344', '77', '72'), ('2345', '77', '-1'), ('2346', '77', '77'), ('3178', '56', '68'), ('3179', '56', '60'), ('3180', '56', '59'), ('3181', '56', '58'), ('3182', '56', '51'), ('3183', '56', '50'), ('3184', '56', '49'), ('3185', '56', '-1'), ('4308', '1', '79'), ('4309', '1', '80'), ('4310', '1', '81'), ('4311', '1', '83'), ('4312', '1', '176'), ('4313', '1', '177'), ('4314', '1', '178'), ('4315', '1', '179'), ('4316', '1', '180'), ('4317', '1', '20'), ('4318', '1', '21'), ('4319', '1', '22'), ('4320', '1', '61'), ('4321', '1', '12'), ('4322', '1', '13'), ('4323', '1', '14'), ('4324', '1', '24'), ('4325', '1', '25'), ('4326', '1', '26'), ('4327', '1', '15'), ('4328', '1', '55'), ('4329', '1', '56'), ('4330', '1', '62'), ('4331', '1', '74'), ('4332', '1', '75'), ('4333', '1', '76'), ('4334', '1', '48'), ('4335', '1', '72'), ('4336', '1', '28'), ('4337', '1', '29'), ('4338', '1', '30'), ('4339', '1', '57'), ('4340', '1', '92'), ('4341', '1', '78'), ('4342', '1', '175'), ('4343', '1', '2'), ('4344', '1', '6'), ('4345', '1', '7'), ('4346', '1', '73'), ('4347', '1', '3'), ('4348', '1', '77'), ('4349', '1', '27'), ('4350', '1', '91'), ('4351', '1', '71'), ('4352', '1', '193'), ('4353', '1', '194'), ('4354', '1', '195'), ('4355', '1', '196'), ('4356', '1', '197'), ('4357', '1', '-1'), ('4358', '1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `sys_task`
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cronExpression` varchar(255) DEFAULT NULL COMMENT 'cron表达式',
  `methodName` varchar(255) DEFAULT NULL COMMENT '任务调用的方法名',
  `isConcurrent` varchar(255) DEFAULT NULL COMMENT '任务是否有状态',
  `description` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `updateBy` varchar(64) DEFAULT NULL COMMENT '更新者',
  `beanClass` varchar(255) DEFAULT NULL COMMENT '任务执行时调用哪个类的方法 包名+类名',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `jobStatus` varchar(255) DEFAULT NULL COMMENT '任务状态',
  `jobGroup` varchar(255) DEFAULT NULL COMMENT '任务分组',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  `createBy` varchar(64) DEFAULT NULL COMMENT '创建者',
  `springBean` varchar(255) DEFAULT NULL COMMENT 'Spring bean',
  `jobName` varchar(255) DEFAULT NULL COMMENT '任务名',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1020572889410367491 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_task`
-- ----------------------------
BEGIN;
INSERT INTO `sys_task` VALUES ('2', '0/10 * * * * ?', 'run1', '1', '', '4028ea815a3d2a8c015a3d2f8d2a0002', 'com.ifast.job.TestJob', '2017-05-19 18:30:56', '0', 'group1', '2017-05-19 18:31:07', null, '', 'TestJob'), ('1020572889410367490', 'ddd', null, null, 'eee', null, 'ccc', null, '0', 'bbb', null, null, null, 'aaa');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `name` varchar(100) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `deptId` bigint(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(255) DEFAULT NULL COMMENT '状态 0:禁用，1:正常',
  `userIdCreate` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `gmtCreate` datetime DEFAULT NULL COMMENT '创建时间',
  `gmtModified` datetime DEFAULT NULL COMMENT '修改时间',
  `sex` bigint(32) DEFAULT NULL COMMENT '性别',
  `birth` datetime DEFAULT NULL COMMENT '出身日期',
  `picId` bigint(32) DEFAULT NULL,
  `liveAddress` varchar(500) DEFAULT NULL COMMENT '现居住地',
  `hobby` varchar(255) DEFAULT NULL COMMENT '爱好',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '所在城市',
  `district` varchar(255) DEFAULT NULL COMMENT '所在地区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', 'admin', '超级管理员', '33808479d49ca8a3cdc93d4f976d1e3d', '6', 'izenglong@163.com', '15277778888', '1', '1', '2017-08-15 21:40:39', '2017-08-15 21:41:00', '96', '2018-04-02 00:00:00', '151', '创客基地', null, '广东省', '广州市', '番禺区'), ('2', 'test', '临时用户', 'b132f5f968c9373261f74025c23c2222', '6', 'test@bootdo.com', null, '1', '1', '2017-08-14 13:43:05', '2017-08-14 21:15:36', null, null, null, null, null, null, null, null), ('36', 'ldh', '刘德华', 'bfd9394475754fbe45866eba97738c36', '6', 'ldh@bootdo.com1', null, '1', null, null, null, null, null, null, null, null, null, null, null), ('123', 'zxy', '张学友', '35174ba93f5fe7267f1fb3c1bf903781', '6', 'zxy@bootdo', null, '1', null, null, null, null, null, null, null, null, null, null, null), ('124', 'wyf', '吴亦凡', 'e179e6f687bbd57b9d7efc4746c8090a', '6', 'wyf@bootdo.com', null, '1', null, null, null, null, null, null, null, null, null, null, null), ('130', 'lh', '鹿晗', '7924710cd673f68967cde70e188bb097', '9', 'lh@bootdo.com', null, '1', null, null, null, null, null, null, null, null, null, null, null), ('131', 'lhc', '令狐冲', 'd515538e17ecb570ba40344b5618f5d4', '6', 'lhc@bootdo.com', null, '1', null, null, null, null, null, null, null, null, null, null, null), ('132', 'lyf', '刘亦菲', '7fdb1d9008f45950c1620ba0864e5fbd', '13', 'lyf@bootdo.com', null, '1', null, null, null, null, null, null, null, null, null, null, null), ('134', 'lyh', '李彦宏', 'dc26092b3244d9d432863f2738180e19', '8', 'lyh@bootdo.com', null, '1', null, null, null, null, null, null, null, null, null, null, null), ('135', 'wjl', '王健林', '3967697dfced162cf6a34080259b83aa', '6', 'wjl@bootod.com', null, '1', null, null, null, null, null, null, null, null, null, null, null), ('136', 'gdg', '郭德纲', '3bb1bda86bc02bf6478cd91e42135d2f', '9', 'gdg@bootdo.com1', null, '1', null, null, null, null, null, null, null, null, null, null, null), ('137', 'sign123', 'sign123', '1e3d9b9fb85ecfc0041f5b944e6a7854', '15', 'sign@sign.com', null, '1', null, null, null, null, null, null, null, null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('73', '30', '48'), ('74', '30', '49'), ('75', '30', '50'), ('76', '31', '48'), ('77', '31', '49'), ('78', '31', '52'), ('79', '32', '48'), ('80', '32', '49'), ('81', '32', '50'), ('82', '32', '51'), ('83', '32', '52'), ('84', '33', '38'), ('85', '33', '49'), ('86', '33', '52'), ('87', '34', '50'), ('88', '34', '51'), ('89', '34', '52'), ('106', '124', '1'), ('110', '1', '1'), ('111', '2', '1'), ('117', '135', '1'), ('120', '134', '1'), ('121', '134', '48'), ('123', '130', '1'), ('124', null, '48'), ('125', '132', '52'), ('126', '132', '49'), ('127', null, '1'), ('128', null, '1'), ('129', null, '1'), ('130', '36', '48'), ('131', '137', '57');
COMMIT;

-- ----------------------------
--  Table structure for `wx_mp_config`
-- ----------------------------
DROP TABLE IF EXISTS `wx_mp_config`;
CREATE TABLE `wx_mp_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `token` varchar(120) DEFAULT NULL,
  `appId` varchar(64) NOT NULL COMMENT 'APPID',
  `appSecret` varchar(128) NOT NULL COMMENT 'AppSecret',
  `msgMode` int(11) DEFAULT NULL COMMENT '1加密 0不加密',
  `msgSecret` varchar(128) DEFAULT NULL,
  `mpName` varchar(250) DEFAULT NULL COMMENT '公众号名字',
  `appType` int(11) NOT NULL COMMENT '公众号类型： 1.订阅号 2.服务号 3.企业号 4.小程序 5. 测试号',
  `isAuth` int(11) DEFAULT NULL COMMENT '认证授权：1已认证 0未认证',
  `subscribeUrl` varchar(500) DEFAULT NULL COMMENT '提示订阅URL',
  `appKey` varchar(100) DEFAULT NULL COMMENT '开放的公众号key',
  `logo` varchar(255) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='微信配置表';

-- ----------------------------
--  Records of `wx_mp_config`
-- ----------------------------
BEGIN;
INSERT INTO `wx_mp_config` VALUES ('10', 'weixin', 'wx4c2c55425d476e48', 'f1849eedf5d2bbbe9fcb2caaa71f6cb1', '0', 'LiUJz8XMKhBH4wh40VwgLsSwFdHCGTmEj4VK3T71SkL', '源码在线', '2', '0', 'http://xxx.com', 'ymh', 'http://wx.qlogo.cn/mmopen/dH8QVxmk2IVAic06s166sFJOTInGwqld2JJIYQicb4pQdwsTXWTJEjeDN1BCDRsDtuDkFp6sDQy67TwaKWsTdXXGv6hGQe67UY/0', '2017-11-03 17:32:48'), ('11', '72597b9628704ab09e8b9e8cbe9b540a', 'wxeb5638d307d3df71', '03b1501c72a91f2935037336e43e67e6', '0', null, '源码在线-测试', '5', '0', 'http://xxx.com/test', 'ymhTest', null, '2017-11-03 17:32:53'), ('12', 'token_huimeitkb', 'wx4d106f4dc7aa68fe', 'f2375d2d6e38561a093dd95f1cc5726e', '0', 'WYNP4CmAsGaFABav3lvXXAz5iBJRh5UnUJvDaDZi92T', '广州灵猫', '1', '0', 'http://xxx.com/test', 'lm', null, '2017-11-03 17:32:56'), ('13', 'token_yunyoujiaxiang', 'wx2ae79bb50a968369', '7038c1a75d8e8ce35467d68613d2522a', '0', 'rzJee4cTxtEqL4pifsuaS4W6DzUHGz8BUwEWfLdxAF0', '云游佳乡-test', '2', '0', null, 'yunyoujiaxiang_test', null, '2017-11-20 11:24:26'), ('14', 'token_jiaxianghui', 'wx20e48f3aa36fadab', '512c5271f15133e951247104c09089ad', '0', 'aYtN2TxTBh3nDOv0Bn11Enw5PvQ6TLfBP4WLjD76JVi', '佳乡汇-test', '2', '0', null, 'jiaxianghui_test', null, '2017-11-20 15:12:02');
COMMIT;

-- ----------------------------
--  Table structure for `wx_mp_fans`
-- ----------------------------
DROP TABLE IF EXISTS `wx_mp_fans`;
CREATE TABLE `wx_mp_fans` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mpId` bigint(11) NOT NULL COMMENT '公众号id',
  `openid` varchar(100) DEFAULT NULL COMMENT '用户的标识，对当前公众号唯一',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `subscribe` tinyint(4) DEFAULT NULL COMMENT '用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。0未关注，1已关注',
  `subscribeTime` datetime DEFAULT NULL COMMENT '用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间',
  `subscribeKey` varchar(100) DEFAULT NULL COMMENT '关注来源',
  `sex` tinyint(4) DEFAULT NULL COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  `city` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `language` varchar(100) DEFAULT NULL COMMENT '语言',
  `headimgurl` varchar(500) DEFAULT NULL COMMENT '用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。',
  `unionid` varchar(125) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL COMMENT '公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注',
  `groupid` int(20) DEFAULT NULL COMMENT '分组ID',
  `status` tinyint(11) DEFAULT NULL COMMENT '用户状态 1:正常，0：禁用',
  `tagidList` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2905 DEFAULT CHARSET=utf8mb4 COMMENT='微信粉丝表';

-- ----------------------------
--  Records of `wx_mp_fans`
-- ----------------------------
BEGIN;
INSERT INTO `wx_mp_fans` VALUES ('2891', '5', 'oorK7v9q1VpB4avDq9ui8_oeGvcU', '罗恩勤-商标专利，建筑资质', '1', '2017-06-29 19:19:24', null, '1', '深圳', '中国', '广东', 'zh_CN', 'http://wx.qlogo.cn/mmopen/kWOnVmxq6fYcB7S1ULr22aWICsEyF25SvlTeRLlGCbD1laVuSCgYwnwe7f04aJl8uDpv1JFQZ0Ivq2o3ejgCDIKXWFib4OKsK/0', 'oI55m1lueqEi13WG3D3VRz3QyECE', '', '0', '0', null), ('2892', '5', 'oorK7v4LWw3GMbwt_Ck6PuQ_8_D8', '你是我的眼', '0', null, null, '2', '广州', '中国', '广东', 'zh_CN', 'http://wx.qlogo.cn/mmopen/A7sq8BD8oewx50myY72SwetEVkBXbXDvT5jj6ytorRxqyGwtBu1XTnWGohGXhdTtTwh6VSAbUEUSWpibddJDChg/0', 'oI55m1o8VxrTFvV57WkzEFTHLRIM', null, null, '0', null), ('2893', '5', 'oorK7vyBCT3WOGcc-ijmO2U4p9dM', '老丙汇客服', '0', '2017-11-01 20:02:39', null, '2', '广州', '中国', '广东', 'zh_CN', 'http://wx.qlogo.cn/mmopen/2QZKlomn2rSCSH7SWGoYnNK7wfJLq8yjxs7YR5ribPTGibw3QzMlf6nic2CJhtaAqzkOXsyJBAxhpuqD4sXNmRTibFaDy9p5VAjw/0', 'oI55m1vLSjJMqoiKjhrOpvXxdWuI', '', '0', '0', null), ('2895', '7', 'otO0P09bRa-YRLNlPbJEL1bdDjkQ', 'Aron', '1', '2017-11-24 11:14:28', '', '1', '广州', '中国', '广东', 'zh_CN', 'http://wx.qlogo.cn/mmopen/BQD9yxMcK6CicIrF6tU8Pqucb2VgYicn1iaTMTwm9war1KLT9RlibbsJ9cYal7yypERODjt6XGXC4dVJdVs9woJZBHwI0ibmaGQxY/0', 'oVzGa0kCIhSXljL9wDALjN00ylOs', '', '0', '0', null), ('2896', '7', 'otO0P08gP66AEAthpg3TnlZ6ktYA', 'Jethro Chen', null, null, null, '1', '广州', '中国', '广东', 'zh_CN', 'http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLFGLh61UCcAZHM67bLwvFCp2GG2yYlA36P16198o7nCWppmQ3QjTDYPIjT6rhjHcM6Ikm7r4nBIg/0', 'oVzGa0peHnOzSQiMeUCIQQElTiBo', null, null, null, null), ('2897', '9', 'ozbGr0vZhCS8Pe1lpTuy1tq9Wlls', 'SuSu', '0', '2017-11-26 21:03:25', '', '1', '江北', '中国', '重庆', 'zh_CN', 'http://wx.qlogo.cn/mmopen/8o7KgbIM6ibFyF3coD1mMMdm91kic6Tb68P0hq9lDccec0TllUm5MnBa4WEesfiaW1HUXWqNqCTNUsrYM5iceq9gnesbSPSaE0Yw/0', 'oJitl0bd590o0ONtSt1nB7hFh0Bo', '', '0', null, null), ('2898', '5', 'oorK7v9q1VpB4avDq9ui8_oeGvcU', '罗恩勤-商标专利，建筑资质', '1', '2017-06-29 19:19:24', null, '1', '深圳', '中国', '广东', 'zh_CN', 'http://wx.qlogo.cn/mmopen/kWOnVmxq6fYcB7S1ULr22aWICsEyF25SvlTeRLlGCbD1laVuSCgYwnwe7f04aJl8uDpv1JFQZ0Ivq2o3ejgCDIKXWFib4OKsK/0', 'oI55m1lueqEi13WG3D3VRz3QyECE', '', '0', '0', null), ('2899', '5', 'oorK7v4LWw3GMbwt_Ck6PuQ_8_D8', '你是我的眼', '0', null, null, '2', '广州', '中国', '广东', 'zh_CN', 'http://wx.qlogo.cn/mmopen/A7sq8BD8oewx50myY72SwetEVkBXbXDvT5jj6ytorRxqyGwtBu1XTnWGohGXhdTtTwh6VSAbUEUSWpibddJDChg/0', 'oI55m1o8VxrTFvV57WkzEFTHLRIM', null, null, '0', null), ('2900', '5', 'oorK7vyBCT3WOGcc-ijmO2U4p9dM', '老丙汇客服', '0', '2017-11-01 20:02:39', null, '2', '广州', '中国', '广东', 'zh_CN', 'http://wx.qlogo.cn/mmopen/2QZKlomn2rSCSH7SWGoYnNK7wfJLq8yjxs7YR5ribPTGibw3QzMlf6nic2CJhtaAqzkOXsyJBAxhpuqD4sXNmRTibFaDy9p5VAjw/0', 'oI55m1vLSjJMqoiKjhrOpvXxdWuI', '', '0', '0', null), ('2902', '7', 'otO0P09bRa-YRLNlPbJEL1bdDjkQ', 'Aron', '1', '2017-11-24 11:14:28', '', '1', '广州', '中国', '广东', 'zh_CN', 'http://wx.qlogo.cn/mmopen/BQD9yxMcK6CicIrF6tU8Pqucb2VgYicn1iaTMTwm9war1KLT9RlibbsJ9cYal7yypERODjt6XGXC4dVJdVs9woJZBHwI0ibmaGQxY/0', 'oVzGa0kCIhSXljL9wDALjN00ylOs', '', '0', '0', null), ('2903', '7', 'otO0P08gP66AEAthpg3TnlZ6ktYA', 'Jethro Chen', null, null, null, '1', '广州', '中国', '广东', 'zh_CN', 'http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLFGLh61UCcAZHM67bLwvFCp2GG2yYlA36P16198o7nCWppmQ3QjTDYPIjT6rhjHcM6Ikm7r4nBIg/0', 'oVzGa0peHnOzSQiMeUCIQQElTiBo', null, null, null, null), ('2904', '9', 'ozbGr0vZhCS8Pe1lpTuy1tq9Wlls', 'SuSu', '0', '2017-11-26 21:03:25', '', '1', '江北', '中国', '重庆', 'zh_CN', 'http://wx.qlogo.cn/mmopen/8o7KgbIM6ibFyF3coD1mMMdm91kic6Tb68P0hq9lDccec0TllUm5MnBa4WEesfiaW1HUXWqNqCTNUsrYM5iceq9gnesbSPSaE0Yw/0', 'oJitl0bd590o0ONtSt1nB7hFh0Bo', '', '0', null, null);
COMMIT;

-- ----------------------------
--  Table structure for `wx_mp_menu`
-- ----------------------------
DROP TABLE IF EXISTS `wx_mp_menu`;
CREATE TABLE `wx_mp_menu` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parentIdx` bigint(20) DEFAULT NULL,
  `menuType` varchar(50) NOT NULL COMMENT '菜单类型：1主菜单，2链接，3文本，4关键字，5扫码，6发图，7发送位置',
  `menuName` varchar(128) NOT NULL,
  `menuKey` varchar(64) DEFAULT NULL,
  `menuUrl` varchar(500) DEFAULT NULL COMMENT '菜单链接',
  `replyContent` varchar(500) DEFAULT NULL,
  `scanType` int(4) DEFAULT NULL COMMENT '扫码类型：1扫码带事件，2扫码带提示',
  `pictureType` int(4) DEFAULT NULL COMMENT '发图类型：1，系统拍照发图；2,，拍照或者相册发图；3，微信相册发图',
  `location` varchar(255) DEFAULT NULL COMMENT '发送位置',
  `sort` int(11) DEFAULT NULL COMMENT '菜单排序',
  `status` int(4) DEFAULT NULL COMMENT '菜单状态',
  `createBy` bigint(20) DEFAULT NULL COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateBy` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `mpId` bigint(20) NOT NULL COMMENT '微信配置ID',
  `idx` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信菜单表';

-- ----------------------------
--  Table structure for `wx_mp_wechat_keys`
-- ----------------------------
DROP TABLE IF EXISTS `wx_mp_wechat_keys`;
CREATE TABLE `wx_mp_wechat_keys` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mpId` bigint(20) NOT NULL COMMENT '公众号id',
  `mediaId` varchar(50) DEFAULT NULL COMMENT '素材Id',
  `keyword` varchar(200) DEFAULT NULL COMMENT '关键字，以#,#隔开',
  `type` tinyint(4) DEFAULT NULL COMMENT '回复类型，1：关键字 2：关注自动回复 3：默认回复',
  `replyType` varchar(20) DEFAULT NULL COMMENT '回复类型，文字：text 图文：news 图片： image 语音：voice 音乐：music 视频：video',
  `content` text COMMENT '内容',
  `newsId` bigint(20) DEFAULT NULL COMMENT '图文素材Id',
  `musicTitle` varchar(100) DEFAULT NULL COMMENT '音乐标题',
  `musicCover` varchar(255) DEFAULT NULL COMMENT '音乐封面',
  `musicUrl` varchar(255) DEFAULT NULL COMMENT '音乐url',
  `musicDesc` varchar(255) DEFAULT NULL COMMENT '音乐描述',
  `imageUrl` varchar(255) DEFAULT NULL COMMENT '图片URL',
  `voiceUrl` varchar(255) DEFAULT NULL COMMENT '音频URL',
  `videoTitle` varchar(100) DEFAULT NULL COMMENT '视频标题',
  `videoUrl` varchar(255) DEFAULT NULL COMMENT '视频url',
  `videoDesc` varchar(255) DEFAULT NULL COMMENT '视频描述',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 0：禁用 1：启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信-关键字回复';

SET FOREIGN_KEY_CHECKS = 1;
