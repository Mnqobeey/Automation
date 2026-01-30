pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK'
    }

    parameters {
        string(name: 'BROWSER', defaultValue: 'chrome', description: 'Browser to use for tests')
        string(name: 'ENVIRONMENT', defaultValue: 'qa', description: 'Environment to run tests against')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Setup') {
            steps {
                sh '''
                    echo "Setting up test environment..."
                    echo "Browser: ${BROWSER}"
                    echo "Environment: ${ENVIRONMENT}"
                    java -version
                    mvn -version
                '''
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh """
                    mvn test \
                    -Dbrowser=${BROWSER} \
                    -Denvironment=${ENVIRONMENT} \
                    -Dcucumber.filter.tags="@regression"
                """
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            cucumber reportTitle: 'Cucumber Report', 
                   fileIncludePattern: '**/cucumber.json',
                   jsonReportDirectory: 'target'
        }
        success {
            echo 'Tests completed successfully!'
            emailext (
                subject: "BUILD SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "The build ${env.JOB_NAME} #${env.BUILD_NUMBER} passed successfully.\n\nCheck console output at: ${env.BUILD_URL}",
                to: 'team@example.com',
                attachLog: false
            )
        }
        failure {
            echo 'Tests failed!'
            emailext (
                subject: "BUILD FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "The build ${env.JOB_NAME} #${env.BUILD_NUMBER} failed.\n\nCheck console output at: ${env.BUILD_URL}",
                to: 'team@example.com',
                attachLog: true
            )
        }
    }
}
