# 迭代一项目说明

## 项目启动

- main函数：local.happysixplus.backendcodeanalysis.MainApplication

首先会依次经过检查点2、4、5，请根据提示完成检查

然后可以选择进入交互式命令行工具

## 交互式命令行工具

可以在完成检查点的检查后进入

输入一行命令，程序将予以反馈

命令格式为

- [命令名称] [可选的参数]

例如

- init ./call_dependencies_update.txt

  表示载入该文件的函数依赖关系

- basic-attribute

  表示输出图的基本信息

- help

  显示帮助

- quit

  退出

![example](/documents/imgs/example.png)

## 检查点对应函数

- 以下函数所在类为：

  local.happysixplus.backendcodeanalysis.service.GraphServiceImpl

#### 检查点1

- loadCode(String path)
- Graph(List<String> caller, List<String> callee, Double clo)

#### 检查点2

- Graph(List<String> caller, List<String> callee, Double clo)

#### 检查点3

- setClosenessMin(double closeness)
- Graph(List<String> caller, List<String> callee, Double clo)

#### 检查点4

- getConnectiveDomainsWithClosenessMin( )
- Graph(List<String> caller, List<String> callee, Double clo)

#### 检查点5

- getSimilarVertex(String funcName)
- public PathVo getShortestPath(VertexVo start, VertexVo end)
- getAllPathDFS(Vertex end, Vertex p, List<Edge> path, List<List<EdgeVo>> res)

