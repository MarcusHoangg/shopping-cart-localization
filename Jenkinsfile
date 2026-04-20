pipeline {
    agent any

    environment {
        IMAGE_NAME = "marcushoangg/shopping-cart-localization"
        IMAGE_TAG = "latest"
        SONAR_TOKEN = "sqp_4f11448ec86d6e20aa72a30a5b36f1c553872415"
    }

    tools {
        maven 'Maven3'
        jdk 'JDK17'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build + Test + Sonar') {
            steps {
                bat """
                mvn clean verify sonar:sonar ^
                -Dsonar.projectKey=shopping-cart-localization ^
                -Dsonar.host.url=http://localhost:9000 ^
                -Dsonar.token=%SONAR_TOKEN%
                """
            }
        }

        stage('Package') {
            steps {
                bat 'mvn package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %IMAGE_NAME%:%IMAGE_TAG% .'
            }
        }

        stage('Push Docker Image') {
            steps {
                bat 'docker push %IMAGE_NAME%:%IMAGE_TAG%'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
    }
}