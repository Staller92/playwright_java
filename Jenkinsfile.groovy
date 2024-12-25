pipeline {

    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'github', url: 'git@github.com:Staller92/playwright_java.git'
            }
        }

               stage('Run Tests') {
            steps {
                script {
                    docker.image("mjdk8").inside("-v ${env.WORKSPACE}:/app") {
                        withEnv(["PLAYWRIGHT_BROWSERS_PATH=/app/.cache"]) {
                            sh '''
                                # Перемещаемся в рабочую директорию
                                cd /app
                                
                                # Создаем папку для кеша браузеров
                                mkdir -p /app/.cache
                                chmod 777 /app/.cache
                                
                                # Запускаем тесты
                                mvn clean test
                            '''
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            script {
                cleanWs(deleteDirs: true)
            }
        }
    }
}