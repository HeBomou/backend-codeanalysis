# 迭代二后端检查点导航

以下代码实现都在src/main/java/local/happysixplus/backendcodeanalysis目录下

/controller中有RESTFUL接口

/service中有service层接口

/data中有data层JPA接口

/exception中定义了异常处理

### 检查点1

#### 多线程初始化依赖图

todo（从url到边信息那边）

service/impl/AsyncCallGraphForProjectServiceImpl.java

#### 保存代码文本

todo

#### 包结构

todo

（如果这俩在一个函数实现的就不分开了）

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

第六，后端，对于点的初始位置生成，我们做了优化。在同一个联通域内，我们基于求强联通分量的Tarjan算法设计了一个改良布局算法，他能保证在有环的情况下也能让排列中边的方向都尽可能的相同；在全局上，使用了jts解析几何库让所有联通域都按照环形围绕排列 

第七，后端的依赖抽取和源码读取。由于之前提供的call-graph工具只能对jar包进行分析，而查看函数对应的源码必须要从java文件入手，所以必须同时拥有待分析项目的jar包和源文件。因此采用在git上clone下来源文件，然后手动打包的方式（不支持maven打包的项目需要提供jar包）。首先进行源码的读取，遍历项目路径，返回一个函数名到函数源码的Map，然后调用call-graph工具对jar包进行依赖抽取，最后进行过滤，删除未出现在依赖中的函数。
