# 简介
IFast是一款基于Spring Boot + Mybatis + Mybatis Plus搭建的快速开发平台。IFast集成了代码生成器，具有高效的开发效率。以Spring Boot为基础框架，Mybatis plus为数据访问层， Apache Shiro为权限授权层，Ehcahe对常用数据进行缓存，基于Bootstrap构建的Admin LTE作为前端框架。

# 定位
 * 中小型企业项目开发脚手架，以基础建设和实用性为主 
 * 接私活利器 
 * 个人学习

# 功能
IFast内置很多优秀的基础功能和高效的代码生成工具，包括：系统权限、数据字典、核心工具、代码生成、在线用户、七牛对象存储、定时任务等。前端界面风格采用了结构简单、性能优良、页面美观大气的Twitter Bootstrap页面展示框架。采用分层设计、提交数据安全编码、密码加密、访问验证、数据权限验证。使用Maven做项目管理，提高项目的易开发性、扩展性。

安全考虑：严格遵循了web安全的规范，前后台双重验证，参数编码传输，密码md5加密存储，shiro权限验证，从根本上避免了SQL注入，XSS攻击，CSRF攻击等常见的web攻击手段。

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

# 快速上手
 * 导入ifast.sql文件到mysql数据库
 * 导入项目到Eclipse.
 * 修改数据库配置文件application-dev.xml中的账号密码.
 * 启动项目,管理员账号  admin / 1
 
# 交流、反馈、参与贡献
 * QQ群：[746358408](https://jq.qq.com/?_wv=1027&k=5Ofijn8)
 * E-mail：izenglong@163.com
 * gitee：https://gitee.com/icron/ifast

# 目录结构说明
```
├─ main
   │  
   ├─ java
   │   │
   │   └─ com.ifast ----------------主代码
   │             │
   │             ├─ common ----------------核心依赖模块
   │             │    
   │             ├─ generator ----------------代码生成器模块
   │             │    
   │             ├─ job ----------------定时任务模块
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
         ├─application*.yml------ 项目配置文件
         │ 
         ├─generator.properties------- 代码生成配置

```

# 近期开发计划
 * 集成微信公众号管理
