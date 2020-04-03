FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD target/backend-codeanalysis-*.jar backend-codeanalysis.jar
ENTRYPOINT ["java", "-jar", "/backend-codeanalysis.jar", "--spring.datasource.url=jdbc:mysql://mysql_ca:3306/code_analysis?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC", "--spring.redis.host=redis_ca"]

# docker run -d -p 3306:3306 --name mysql_ca --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_PASSWORD=root" --env="MYSQL_DATABASE=code_analysis" mysql
# docker run -d -p 6379:6379 --name redis_ca redis
# docker run -d -p 8080:8080 --link mysql_ca:mysql_ca --link redis_ca:redis_ca --rm codeanalysis