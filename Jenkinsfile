pipeline {
    agent {
        docker {
            image 'openjdk:8-jdk-alpine' 
            args '-v /root/.m2:/root/.m2' 
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
                sh 'echo "cnm"'
            }
        }
    }
}
