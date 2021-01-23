# springboot插件式开发框架

### 介绍
此框架可在springboot项目上开发出用于扩展项目的插件，可在插件模块中单独定义接口、静态文件、mybatis-xml等扩展功能。

### 核心功能
1. 插件配置式插拔于springboot项目。
2. 在springboot上可以进行插件式开发, 扩展性极强, 可以针对不同项目开发不同插件, 进行不同插件jar包的部署。
3. 可通过配置文件指定要启用或者禁用插件。
4. 支持上传插件和插件配置文件到服务器, 并且无需重启主程序, 动态部署插件、更新插件。
5. 支持查看插件运行状态, 查看插件安装位置。
6. 无需重启主程序, 动态的安装插件、卸载插件、启用插件、停止插件、备份插件、删除插件。
7. 在插件应用模块上可以使用Spring注解定义组件, 进行依赖注入。
8. 支持在插件中开发Rest接口。
9. 支持在插件中单独定义持久层访问等需求。
10. 可以遵循主程序提供的插件接口开发任意扩展功能。
11. 插件可以自定义配置文件。目前只支持yml文件。
12. 支持自定义扩展开发接口, 使用者可以在预留接口上扩展额外功能。
13. 支持插件之间的通信。
14. 支持插件中使用事务注解。
15. 支持Swagger。(仅支持首次启动初始化的插件)

### 扩展包功能
1. `SpringBoot-Mybatis`扩展包

- 支持在插件中自定义`Mapper`接口、`Mapper xml` 以及对应的实体`bean`

- 支持集成`Mybatis`、`Mybatis-Plus`、`Tk-Mybatis`

- 支持可在插件中独立配置数据源

详见 [插件SpringBoot Mybatis扩展](https://gitee.com/starblues/springboot-plugin-framework-parent/wikis/pages?sort_id=1693493&doc_id=343010)

2. 静态资源访问扩展包

- 支持通过http访问插件中静态资源
- 支持`Thymeleaf`模板引擎进行界面插件式开发

详见 [插件静态资源访问扩展](https://gitee.com/starblues/springboot-plugin-framework-parent/wikis/pages?sort_id=1719990&doc_id=343010)

### 运行环境
1. jdk1.8+
2. apache maven 3.6+
3. spring-boot 2.0.0+

### maven 仓库地址

[https://mvnrepository.com/artifact/com.gitee.starblues/springboot-plugin-framework](https://mvnrepository.com/artifact/com.gitee.starblues/springboot-plugin-framework)

### 文档地址

[https://gitee.com/starblues/springboot-plugin-framework-parent/wikis/pages](https://gitee.com/starblues/springboot-plugin-framework-parent/wikis/pages)


### 使用案例

1、[Rope-ETL系统](https://gitee.com/starblues/rope)

### QQ交流群
859570617(**点赞框架后可进群, 进群前请备注gitee昵称**)


