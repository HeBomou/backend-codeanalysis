# 迭代二后端检查点导航

已经部署在服务器上，为了方便检查我们配置了跨域，助教可以用直接postman根据接口文档检查接口，服务器地址是[http://101.201.150.49:8080/](http://101.201.150.49:8080/)，如果无响应可能是Jenkins正在自动构建，Jenkins地址是[http://101.201.150.49:8088/](http://101.201.150.49:8088/)，账号密码请联系组长

以下代码实现都在src/main/java/local/happysixplus/backendcodeanalysis目录下

/controller中有RESTFUL接口

/service中有service层接口

/data中有data层JPA接口

/exception中定义了异常处理

### 检查点1

#### 多线程初始化依赖图

util/callgraph/CallGraphMethodsImpl.java：cloneProject

service/impl/AsyncCallGraphForProjectServiceImpl.java

#### 保存代码文本

util/callgraph/CallGraphMethodsImpl.java：loadSourceCode

#### 包结构

service/impl/ProjectServiceImpl.java：initAndSaveProject

service/impl/ProjectServiceImpl.java：PackageNode内部静态类的insertFunc递归函数

#### 获取项目信息

service/impl/ProjectServiceImpl.java：getProjectBasicAttribute

service/impl/ProjectServiceImpl.java：getProjectProfile

service/impl/ProjectServiceImpl.java：getProjectAll



### 检查点2

#### 设置紧密度阀值生成连通域

service/impl/ProjectServiceImpl.java：addSubgraph

#### 路径查找

service/impl/ProjectServiceImpl.java：getOriginalGraphPath



### 检查点3

#### 维护顶点信息（文本标记、位置）

service/impl/ProjectServiceImpl.java：updateVertexDynamic

#### 维护边信息（文本标记）

service/impl/ProjectServiceImpl.java：updateEdgeDynamic

#### 维护连通域信息（文本标记、颜色、位置）

service/impl/ProjectServiceImpl.java：updateConnectiveDomainDynamic

service/impl/ProjectServiceImpl.java：updateConnectiveDomainAllVertex



### 检查点4

#### 函数名搜索

service/impl/ProjectServiceImpl.java：getSimilarFunction



### 检查点5

#### 用户登陆

service/impl/SessionServiceImpl.java

#### 用户增删改查

service/impl/UserServiceImpl.java

#### 管理员登陆

service/impl/AdminSessionServiceImpl.java

#### 管理员增删改查

service/impl/AdminUserServiceImpl.java
