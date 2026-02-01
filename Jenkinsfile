pipeline {
    agent any
    
    options {
        timeout(time: 60, unit: 'MINUTES')
        disableConcurrentBuilds()
    }
    
    triggers {
        githubPush()
    }
    
    parameters {
        choice(
            name: 'DEPLOY_ENV',
            choices: ['dev', 'staging', 'production'],
            description: 'Deployment environment'
        )
    }
    
    environment {
        PROJECT_NAME = 'Automation'
        VERSION = "1.0.${env.BUILD_NUMBER}"
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

        stage('Build') {
            steps {
                script {
                    // Check what type of project this is
                    if (fileExists('pom.xml')) {
                        echo 'Maven project detected'
                        sh 'mvn clean compile'
                    } else if (fileExists('package.json')) {
                        echo 'Node.js project detected'
                        sh 'npm install'
                        sh 'npm run build'
                    } else if (fileExists('requirements.txt')) {
                        echo 'Python project detected'
                        sh 'pip install -r requirements.txt'
                    } else {
                        echo 'Unknown project type, running generic build'
                        sh 'echo "Building application..."'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    if (fileExists('pom.xml')) {
                        sh 'mvn test'
                    } else if (fileExists('package.json')) {
                        sh 'npm test'
                    } else {
                        sh 'echo "No specific test command found"'
                    }
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Quality Checks') {
            steps {
                script {
                    echo "Running quality checks..."
                    
                    // Code linting
                    if (fileExists('package.json')) {
                        sh 'npm run lint || true'  // Continue even if linting fails
                    }
                    
                    // Security scanning
                    if (fileExists('package.json')) {
                        sh 'npm audit || true'
                    }
                }
            }
        }
        
        stage('Package') {
            steps {
                script {
                    if (fileExists('pom.xml')) {
                        sh 'mvn package -DskipTests'
                        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                    } else if (fileExists('package.json')) {
                        sh 'npm run package || true'
                    }
                }
            }
        }
        
        stage('Deploy') {
            when {
                expression { params.DEPLOY_ENV != null }
            }
            steps {
                script {
                    echo "Deploying to ${params.DEPLOY_ENV}"
                    
                    // Based on the project type, deploy appropriately
                    if (fileExists('deploy.sh')) {
                        sh "./deploy.sh ${params.DEPLOY_ENV}"
                    } else if (fileExists('docker-compose.yml')) {
                        sh "docker-compose -f docker-compose.${params.DEPLOY_ENV}.yml up -d"
                    } else {
                        echo "No deployment script found for ${params.DEPLOY_ENV}"
                    }
                }
            }
        }
    }
    
    post {
        always {
            echo "Build ${env.BUILD_NUMBER} completed"
            // Clean workspace
            cleanWs()
        }
        success {
            echo "✅ Pipeline succeeded"
            // Optional notifications
            // slackSend(message: "Build ${env.BUILD_NUMBER} succeeded")
        }
        failure {
            echo "❌ Pipeline failed"
            // Optional notifications
            // slackSend(message: "Build ${env.BUILD_NUMBER} failed")
        }
    }
}
