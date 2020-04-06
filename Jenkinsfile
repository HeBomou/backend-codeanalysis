pipeline {
    agent {
        docker {
            image 'openjdk:8-jdk-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
    }
    stage('init') {
        steps {
            script {
                def dockerPath = tool 'docker' //全局配置里的docker
                env.PATH = "${dockerPath}/bin:${env.PATH}" //添加了系统环境变量上
            }
        }
    }
    stages {
        stage('Build') { 
            steps {
                sh './mvnw -B -DskipTests clean package' 
            }
        }
        stage('Deliver') {
            steps {
                sh 'docker stop codeanalysis'
                sh 'docker rm codeanalysis'
                sh 'docker rmi codeanalysis'
                sh 'docker stop mysql_ca'
                sh 'docker rm mysql_ca'
                sh 'docker run -d --name mysql_ca --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_PASSWORD=root" --env="MYSQL_DATABASE=code_analysis" mysql'
                sh 'docker build -t codeanalysis .'
                sh 'docker run -d --name codeanalysis -v /root/.m2:/root/.m2 -p 8080:8080 --link mysql_ca:mysql_ca codeanalysis'
                sh 'echo "Deliver success"'
            }
        }
    }
}
