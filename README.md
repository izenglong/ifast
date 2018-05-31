
# 简介
ifast是一款基于Spring Boot + Mybatis + Mybatis Plus搭建的快速开发平台。ifast集成了代码生成器，具有高效的开发效率。以Spring Boot为基础框架，Mybatis plus为数据访问层， Apache Shiro为权限授权层，Ehcahe对常用数据进行缓存，基于Bootstrap构建的Admin LTE作为前端框架。

# 定位
 * 中小型企业项目开发脚手架，以基础建设和实用性为主 
 * 接私活利器 
 * 个人学习

# 内置功能
ifast以基础建设和实用性为切入点，内置了众多很多优秀的基础功能和工具。包括：
 * 系统权限  
    基于shiro实现的rbac的权限管理，能够基于不同的角色控制到按钮权限级别
 * 数据字典  
 	对系统使用的枚举类进行预览、管理。
 * 核心工具  
	系统对常用的工具类进行了封装，使用更加方便。
 * 代码生成  
 	基于volecity模板，可根据db生成单表的增删改查代码，包括菜单sql、dao、service、cotroller、js、html。
 * 在线用户  
 	查看在线的用户，支持强T退出。
 * 七牛对象存储  
 	支持第三方对象存储七牛云oss，需申请相应的账户信息。
 * 定时任务  
 	基于quartz实现的动态定时任务。
 * Swagger 文档  
 	api开发方便在线调试，简化了与前端的对接。
 * 基于 JWT 实现的 API 模块  
	简化了app的api开发基础工作。
	
# 技术选型
1.后端
 * 核心框架：Spring Boot
 * 安全框架：Apache Shiro
 * 视图框架：Spring MVC
 * 服务端验证：Hibernate Validator
 * 任务调度：Quartz
 * 持久层框架：Mybatis、Mybatis plus
 * 数据库连接池：Alibaba Druid
 * 缓存框架：Ehcache
 * 日志管理：SLF4J、Log4j
 * 工具类：Apache Commons、Jackson、Xstream、

2.前端

 * JS框架：jQuery。
 * CSS框架：Twitter Bootstrap
 * 数据表格：bootstrap table
 * 对话框：layer
 * 树结构控件：jQuery zTree
 * 日期控件： datepicker
 
# 运行截图
![运行截图](http://p6r7ke2jc.bkt.clouddn.com/ifast/20180415/login-1523788364377.png)

# 在线演示

> 演示地址： [http://ifast.site](http://ifast.site)

# 快速上手
 * 导入ifast.sql文件到mysql数据库
 * 导入项目到Eclipse.
 * 修改数据库配置文件application-dev.xml中的账号密码.
 * 启动项目,管理员账号  admin / 1
 
# 交流、反馈、参与贡献
 * QQ群：[746358408](https://jq.qq.com/?_wv=1027&k=5Ofijn8)
 * E-mail：izenglong@163.com
 * gitee地址：https://gitee.com/icron/ifast
 * github地址：https://github.com/izenglong/ifast

# 目录结构说明
```
├─ main
   │  
   ├─ java
   │   │
   │   └─ com.ifast ----------------主代码
   │             │    
   │             ├─ api ----------------基于jwt实现的api模块
   │             │
   │             ├─ common ----------------核心依赖模块
   │             │    
   │             ├─ generator ----------------代码生成器模块
   │             │    
   │             ├─ job ----------------定时任务模块
   │             │    
   │             ├─ shiro ----------------权限模块
   │             │    
   │             ├─ oss ----------------对象存储模块
   │             │    
   │             ├─ sys ----------------系统基础功能模块
   │             │    
   │             ├─ Application.Java ---------------- 启动入口类
   │
   ├─resources----------------资源文件
         │
         ├─ config ----------------缓存配置（ehcache.xml缓存配置文件,启动banner等）
         │ 
         ├─ mapper ------------------- mybatis 的mapper文件
         │ 
         ├─ public ------------- 静态资源文件，css，js
         │ 
         ├─ static ----- 静态资源文件，css，js
         │ 
         ├─ templates ---- 页面模板
         │ 
         ├─ application*.yml------ 项目配置文件
         │ 
         ├─ generator.properties------- 代码生成配置

```

# 近期开发计划
 * 集成微信公众号管理
