pipeline {
    agent any
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
