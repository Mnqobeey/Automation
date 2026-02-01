pipeline {
    agent any
    
    options {
        timeout(time: 60, unit: 'MINUTES')
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '30'))
    }
    
    triggers {
        githubPush()
    }
    
    parameters {
        choice(
            name: 'DEPLOY_ENVIRONMENT',
            choices: ['dev', 'staging', 'production'],
            description: 'Select deployment environment'
        )
        booleanParam(
            name: 'RUN_INTEGRATION_TESTS',
            defaultValue: true,
            description: 'Run integration tests'
        )
        booleanParam(
            name: 'RUN_PERFORMANCE_TESTS',
            defaultValue: false,
            description: 'Run performance tests'
        )
    }
    
    environment {
        ARTIFACT_VERSION = "${env.BUILD_NUMBER}"
        DEPLOY_TARGET = "${params.DEPLOY_ENVIRONMENT}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    extensions: [],
                    userRemoteConfigs: [[
                        url: 'https://github.com/Mnqobeey/Automation.git',
                        credentialsId: 'github-token'
                    ]]
                ])
            }
        }
        
        stage('Initialize') {
            steps {
                script {
                    echo "Starting build #${env.BUILD_NUMBER}"
                    echo "Deploying to: ${DEPLOY_TARGET}"
                    echo "Workspace: ${env.WORKSPACE}"
                }
            }
        }
        
        stage('Install Dependencies') {
            steps {
                sh '''
                    echo "Installing dependencies..."
                    # Example: npm install or pip install or mvn dependency:resolve
                    # npm ci --only=production
                    # or
                    # pip install -r requirements.txt
                '''
            }
        }
        
        stage('Build') {
            steps {
                sh '''
                    echo "Building application..."
                    # Example build commands:
                    # mvn clean compile
                    # gradle build
                    # docker build -t myapp:$ARTIFACT_VERSION .
                '''
            }
            post {
                success {
                    archiveArtifacts artifacts: '**/target/*.jar,**/dist/*.zip', fingerprint: true
                }
            }
        }
        
        stage('Unit Tests') {
            steps {
                sh '''
                    echo "Running unit tests..."
                    # Example: mvn test or npm test
                    # mvn test
                '''
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml, **/test-results/*.xml'
                }
            }
        }
        
        stage('Integration Tests') {
            when {
                expression { params.RUN_INTEGRATION_TESTS == true }
            }
            steps {
                sh '''
                    echo "Running integration tests..."
                    # Example: mvn verify or specific integration test command
                '''
            }
        }
        
        stage('Code Quality') {
            steps {
                script {
                    echo "Running code quality checks..."
                    // SonarQube analysis (if configured)
                    // withSonarQubeEnv('SonarQube') {
                    //     sh 'mvn sonar:sonar'
                    // }
                }
            }
        }
        
        stage('Security Scan') {
            steps {
                sh '''
                    echo "Running security scans..."
                    # Example: OWASP dependency check
                    # mvn org.owasp:dependency-check-maven:check
                '''
            }
        }
        
        stage('Performance Tests') {
            when {
                expression { params.RUN_PERFORMANCE_TESTS == true }
            }
            steps {
                sh '''
                    echo "Running performance tests..."
                    # Example: JMeter or Gatling tests
                '''
            }
        }
        
        stage('Package Artifact') {
            steps {
                sh '''
                    echo "Packaging final artifact..."
                    # Example: mvn package or create docker image
                    # mvn package -DskipTests
                    # docker tag myapp:$ARTIFACT_VERSION registry/myapp:$ARTIFACT_VERSION
                '''
            }
        }
        
        stage('Deploy to Environment') {
            when {
                expression { params.DEPLOY_ENVIRONMENT != 'none' }
            }
            steps {
                script {
                    echo "Deploying to ${DEPLOY_TARGET} environment"
                    
                    switch(DEPLOY_TARGET) {
                        case 'dev':
                            sh './deploy-dev.sh'
                            break
                        case 'staging':
                            sh './deploy-staging.sh'
                            break
                        case 'production':
                            // Typically requires approval
                            echo "Production deployment requires manual approval"
                            break
                    }
                }
            }
        }
        
        stage('Post-Deployment Verification') {
            when {
                expression { params.DEPLOY_ENVIRONMENT != 'none' }
            }
            steps {
                sh '''
                    echo "Verifying deployment..."
                    # Example: health check, smoke tests
                    # curl -f http://app-url/health
                '''
            }
        }
    }
    
    post {
        always {
            echo "Build #${env.BUILD_NUMBER} completed"
            // Clean up workspace if needed
            // cleanWs()
        }
        success {
            echo "Pipeline succeeded! ✅"
            // Optional: Send success notification
            // slackSend(color: 'good', message: "Build ${env.BUILD_NUMBER} succeeded")
        }
        failure {
            echo "Pipeline failed! ❌"
            // Optional: Send failure notification
            // slackSend(color: 'danger', message: "Build ${env.BUILD_NUMBER} failed")
        }
        unstable {
            echo "Pipeline completed with unstable status"
        }
    }
}
