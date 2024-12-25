pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'github', url: 'git@github.com:Staller92/playwright_java.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    // Use Docker image for Maven build
                    docker.image('maven:3.9.4-eclipse-temurin-17').inside {
                        sh 'mvn clean install'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Use Docker image for Maven test
                    docker.image('maven:3.9.4-eclipse-temurin-17').inside {
                        sh 'mvn test'
                    }
                }
            }
        }
    }

    post {
        always {
            // Publish Allure report
            allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]

            // Clean up workspace
            cleanWs(deleteDirs: true)
        }
    }
}