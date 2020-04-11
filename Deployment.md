# CodeAnalysis项目后端部署文档

## 概要

先用maven wrapper打包，然后构建Docker镜像，最后根据该镜像运行Docker容器，另外需要一个MySQL容器用于数据库  
整个过程由Jenkins控制并在开发过程中自动部署，实现持续集成  

## 手动部署详细步骤

1. 使用Maven Wrapper打jar包  

    在项目根目录下根据操作系统执行以下命令

    Mac或Linux下

    > $ ./mvnw -B -DskipTests clean package

    Windows下使用PowerShell
    > $ mvnw.cmd -B -DskipTests clean package

2. 运行后端所依赖的MySQL容器

    如果之前没有运行过MySQL容器，我们应当启动一个MySQL容器用于存储后端数据

    > $ docker run -d --name mysql_ca \\  
        --env="MYSQL_ROOT_PASSWORD=root" \\  
        --env="MYSQL_PASSWORD=root" \\  
        --env="MYSQL_DATABASE=code_analysis" mysql  

3. 如果后端已经在运行，我们应当停止它并清除相关容器与镜像

    > $ docker stop codeanalysis  
    > $ docker rm codeanalysis  
    > $ docker rmi codeanalysis  

4. 构建新的Docker镜像

    > $ docker build -t codeanalysis .  

5. 最后我们根据这个镜像运行容器

    需要注意一个问题是数据卷的挂载，因为后端需要用Maven命令下载项目并解析函数依赖，所以我们把.m2文件夹挂载到宿主机的.m2下以避免Maven依赖包的重复下载，此外宿主机的.m2下的setting.xml也可以为后端配置国内镜像，应当根据宿主机.m2文件夹的位置修改命令中的-v参数  
    例如下面的命令中宿主机的.m2在root用户下

    另外有一个问题是，--link参数里要把后端跟第二步中的MySQL容器链接起来

    > $ docker run -d --name codeanalysis \\  
    > -v /root/.m2:/root/.m2 -p 8080:8080 \\  
    > --link mysql_ca:mysql_ca codeanalysis

## Jenkins持续集成

Jenkins也使用Docker部署，需要注意一个问题是在Jenkins容器内构建并发布项目需要链接到宿主机或远程的Docker Daemon  

连接到远程Docker Daemon需要配置远程Docker Daemon的远程连接；为确保安全性，可以使用Docker内置的证书加密，或者使用阿里云安全组策略限制访问ip  

但是我们小组没有这么多台服务器，因此本文以链接到宿主机Docker Daemon为示例  

1. 首先使用Docker部署Jenkins

    > $ docker run -d --name Jenkins \\  
    > -u root -p 8088:8080 \\  
    > -v /var/run/docker.sock:/var/run/docker.sock \\  
    > jenkinsci/blueocean  

    挂载docker.sock是为了访问宿主机的Docker Daemon，-u root是为了保证访问docker.sock的权限，端口映射自己选一个就行  

    然后使用浏览器访问宿主机的8088端口（或者你映射的端口），跟随指引配置即可  

    访问初始密钥的时候需要进入Jenkins容器内部，根据网页提示到容器中的指定目录下寻找

    > $ docker exec -it Jenkins bash

2. 安装所需的Jenkins插件

    首先更新Jenkins本体，以及更新所有可更新的插件

    然后安装下述插件  
    SSH、GitLab、GitLab Hook、Build Authorization Token Root

3. 创建并配置一个流水线项目

    因为Jenkinsfile已经写好，只需要配置好GitLab地址和GitLab Hook就行了

    TODO: 下面是SSD的表演时间

4. 如果服务器CPU核心较少或内存不够

    注意到一个问题，如果连续push，Jenkins构建会同时进行，这必定会导致我们价值十块钱的学生服务器爆炸  

    因此需要在Jenkins系统设置里把最大同时构建数改为1，这样push产生的新的构建请求会被排进构建队列里，就不会炸服

5. 最后在master分支push就能自动部署了
