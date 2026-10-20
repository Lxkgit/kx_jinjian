<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">浙江省金融监管局防非打非网格系统 v1.0.0</h1>
<h4 align="center">基于 Vue/Element UI 和 Spring Boot/Spring Cloud & Alibaba 前后端分离的分布式微服务架构</h4>


## 系统框架

* 采用前后端分离的模式，微服务版本前端。
* 后端采用Spring Boot、Spring Cloud & Alibaba。
* 注册中心、配置中心选型Nacos，权限认证使用Redis。
* 流量控制框架选型Sentinel，分布式事务选型Seata。

## 系统模块

~~~
com.kx     
├── kx-ui              // 前端框架 [80]
├── kx-gateway         // 网关模块 [8080]
├── kx-auth            // 认证中心 [9200]
├── kx-api             // 接口模块
│       └── kx-api-system                          // 系统接口
├── kx-common          // 通用模块
│       └── kx-common-core                         // 核心模块
│       └── kx-common-datascope                    // 权限范围
│       └── kx-common-datasource                   // 多数据源
│       └── kx-common-log                          // 日志记录
│       └── kx-common-redis                        // 缓存服务
│       └── kx-common-seata                        // 分布式事务
│       └── kx-common-security                     // 安全模块
│       └── kx-common-swagger                      // 系统接口
├── kx-modules         // 业务模块
│       └── kx-system                              // 基础模块 [9201]
│       └── kx-job                                 // 定时任务 [9300]
│       └── kx-file                                // 文件服务 [9400]
├── kx-visual          // 图形化管理模块
│       └── kx-visual-monitor                      // 监控中心 [9100]
├──pom.xml                // 公共依赖
~~~

## 微服务架构图


## 数据架构图


## 系统功能

1. 网格地图大屏：
2. 网格信息管理：网格数据列表，网格责任网点，网格电子边界。
3. 扫楼清街管理：扫楼上班管理，扫楼清街数据报表。
4. 线索报送管理：线索上报管理，线索上报数据报表。
5. 系统管理：用户管理，机构管理，权限管理，菜单管理，字典管理等。

