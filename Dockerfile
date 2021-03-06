FROM openjdk:8-jdk-alpine
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
RUN apk --update add --no-cache git
EXPOSE 8080
ADD target/backend-codeanalysis-*.jar backend-codeanalysis.jar
ADD .mvn .mvn
ADD mvnw mvnw
RUN mkdir /root/.m2
ADD settings.xml /root/.m2/
ENTRYPOINT ["java", "-jar", "/backend-codeanalysis.jar", "--spring.datasource.url=jdbc:mysql://mysql_ca:3306/code_analysis?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC", "--spring.redis.host=redis_ca"]

# 重启mysql容器
# docker stop mysql_ca
# docker rm mysql_ca
# docker run -d --name mysql_ca --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_PASSWORD=root" --env="MYSQL_DATABASE=code_analysis" mysql

# 项目重新部署
# ./mvnw clean package -DskipTests
# docker stop codeanalysis
# docker rm codeanalysis
# docker rmi codeanalysis
# docker build -t codeanalysis .
# docker run -d --name codeanalysis -v /root/.m2:/root/.m2 -p 8080:8080 --link mysql_ca:mysql_ca codeanalysis