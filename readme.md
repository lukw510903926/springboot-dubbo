1:此项目使用springboot2构建,分为基础模块dubbo-common,对外提供的dubbo接口模块dubbo-api,采用dubbo协议的
生产者模块dubbo-server,采用restful协议的生产者模块dubbo-rest-server,dubbo消费者模块dubbo-client,标准
springMVC模块boot-mvc

2:springboot2与dubbo的集成使用的是自动配置的方式,starter 使用的为appache提供的dubbo-spring-boot-starter,
文档可参考https://github.com/apache/incubator-dubbo-spring-boot-project,也可以改为阿里巴巴提供的starter
文档地址为:https://github.com/alibaba/dubbo-spring-boot-starter,apache提供的starter有springboot1与springboot2
两个版本,阿里巴巴提供的只有springboot1版本,个人建议采用apache版本

3:项目中使用的orm框架为mybatis,快速开发插件mybatis-plugs,使用mybatis-plus-boot-starter集成,文档可参考
https://gitee.com/baomidou/mybatisplus-spring-boot

4:boot-mvc使用springboot开发的标准restful接口模块,包括自定义配置文件读取,filter Interceptor注册,媒体解析器配置,
跨域配置,redis配置,自定义的自动配置类 SsoAutoConfiguration,OssAutoConfiguration,前端使用thymeleaf+jquery+bootStrap
全局异常拦截GlobalExceptionHandler

5:zookeeper window 下安装
    zookeeper官网下载后解压 在zookeeper/conf目录下添加配置文件zoo.cfg 内容可参考 zoo_sample.cfg文件
    本人配置为 tickTime=2000  initLimit=10  dataDir=./tmp/zookeeper clientPort=2181 dataLogDir=./logs
    配置完成在    zookeeper/bin 目录下通过zkServer.cmd 启动

6:dubbo-admin 下载部署
    dubbo-admin 为dubbo监控系统可在 https://pan.baidu.com/s/1LS2QUF-AhO5wnmI6B3ws8A 下载 密码76im
    文件war包可直接部署在tomcat下 war包解压后更改/WEB-INF/dubbo.properties 配置文件修改
    dubbo.registry.address=zookeeper://127.0.0.1:2181(修改为自己的zookeeper地址)
    部署完成后在浏览器即可通过地址http://localhost:8080/dubbo 访问 账户密码admin admin

7:程序开发可参考代码