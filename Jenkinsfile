pipeline {
    agent any
    
    tools {
        // CORRECTED: Use 'toolName "installName"' syntax
        jdk 'jdk17' // 'jdk17' is the name you configured in Jenkins
        maven 'maven-3.9' // 'maven-3.9' is the name you configured in Jenkins
    }
    
    options {
        timeout(time: 15, unit: 'MINUTES')
        disableConcurrentBuilds()
        timestamps()
    }
    
    triggers {
        // FIXED: Use a valid trigger.
        // 'githubPush' is not valid. Use 'pollSCM' for periodic checks or
        // configure the GitHub webhook in your Jenkins job settings instead.
        pollSCM('H/5 * * * *') // Example: Poll every 5 minutes. Remove if using webhooks.
    }
    
    environment {
        GIT_COMMIT_SHORT = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
        BUILD_TIMESTAMP = new Date().format("yyyyMMdd-HHmmss")
        ARTIFACT_NAME = "automation-build-${env.BUILD_NUMBER}.jar"
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    extensions: [
                        [$class: 'CleanBeforeCheckout'],
                        [$class: 'LocalBranch', localBranch: 'main']
                    ],
                    userRemoteConfigs: [[
                        url: 'https://github.com/Mnqobeey/Automation.git',
                        // Ensure 'github-token' credential exists in Jenkins
                        credentialsId: 'github-token'
                    ]]
                ])
                
                script {
                    // FIXED: Use double quotes for Groovy string interpolation.
                    // Single quotes in sh block prevent variable expansion.
                    sh """
                        echo "Git Commit: ${env.GIT_COMMIT}"
                        echo "Git Branch: main"
                        echo "Commit Author: \$(git log -1 --pretty=format:'%an')"
                        echo "Commit Message: \$(git log -1 --pretty=format:'%s')"
                    """
                }
            }
        }
        
        stage('Build') {
            steps {
                script {
                    echo "Building Automation project..."
                    
                    if (fileExists('pom.xml')) {
                        echo "Maven project detected"
                        sh 'mvn clean compile -DskipTests'
                    } else if (fileExists('package.json')) {
                        echo "Node.js project detected"
                        sh 'npm ci'
                        sh 'npm run build'
                    } else if (fileExists('build.gradle')) {
                        echo "Gradle project detected"
                        sh 'gradle build -x test'
                    } else if (fileExists('requirements.txt')) {
                        echo "Python project detected"
                        sh 'pip install -r requirements.txt'
                    } else {
                        echo "No build configuration found - skipping build"
                    }
                }
            }
            
            post {
                success {
                    echo "Build completed successfully"
                    script {
                        if (fileExists('target/*.jar')) {
                            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                        } else if (fileExists('dist/*')) {
                            archiveArtifacts artifacts: 'dist/*', fingerprint: true
                        } else if (fileExists('build/*')) {
                            archiveArtifacts artifacts: 'build/*', fingerprint: true
                        }
                    }
                }
            }
        }
        
        stage('Test') {
            steps {
                script {
                    echo "Running tests..."
                    
                    if (fileExists('pom.xml')) {
                        sh 'mvn test'
                    } else if (fileExists('package.json')) {
                        sh 'npm test'
                    } else if (fileExists('build.gradle')) {
                        sh 'gradle test'
                    } else if (fileExists('tests/')) {
                        sh 'python -m pytest tests/ || true'
                    } else {
                        echo "No test configuration found - skipping tests"
                    }
                }
            }
            
            post {
                always {
                    script {
                        if (fileExists('target/surefire-reports/*.xml')) {
                            junit 'target/surefire-reports/*.xml'
                        } else if (fileExists('build/test-results/**/*.xml')) {
                            junit 'build/test-results/**/*.xml'
                        } else if (fileExists('test-results.xml')) {
                            junit 'test-results.xml'
                        }
                    }
                }
            }
        }
        
        stage('Quality Checks') {
            steps {
                script {
                    echo "Running quality checks..."
                    
                    if (fileExists('package.json') && sh(script: 'npm run lint -- --help >/dev/null 2>&1', returnStatus: true) == 0) {
                        sh 'npm run lint'
                    }
                    
                    if (fileExists('package.json') && sh(script: 'npm audit --help >/dev/null 2>&1', returnStatus: true) == 0) {
                        sh 'npm audit || true'
                    }
                }
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                script {
                    echo "Running SonarQube analysis..."
                    
                    // NOTE: The `withSonarQubeEnv` block is required.
                    // Ensure 'SonarQube' matches your Jenkins server config name.
                    if (fileExists('pom.xml')) {
                        withSonarQubeEnv('SonarQube') {
                            sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=Automation -Dsonar.projectName="Automation"'
                        }
                    } else if (fileExists('package.json')) {
                        withSonarQubeEnv('SonarQube') {
                            sh 'sonar-scanner -Dsonar.projectKey=Automation -Dsonar.projectName="Automation"'
                        }
                    } else if (fileExists('build.gradle')) {
                        withSonarQubeEnv('SonarQube') {
                            sh './gradlew sonarqube -Dsonar.projectKey=Automation -Dsonar.projectName="Automation"'
                        }
                    } else {
                        echo "No supported project type detected for SonarQube analysis"
                    }
                }
            }
            
            post {
                success {
                    // FIXED: Moved the quality gate check to the POST success block.
                    // It should run AFTER the analysis step succeeds.
                    script {
                        timeout(time: 5, unit: 'MINUTES') {
                            waitForQualityGate abortPipeline: true
                        }
                    }
                    echo "SonarQube analysis completed successfully"
                }
                failure {
                    echo "SonarQube analysis failed"
                }
            }
        }
        
        stage('Package') {
            when {
                expression { 
                    fileExists('pom.xml') || fileExists('package.json') || fileExists('build.gradle')
                }
            }
            steps {
                script {
                    echo "Packaging application..."
                    
                    if (fileExists('pom.xml')) {
                        sh 'mvn package -DskipTests'
                    } else if (fileExists('package.json')) {
                        sh 'npm run package'
                    } else if (fileExists('build.gradle')) {
                        sh 'gradle assemble'
                    }
                }
            }
        }
        
        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                script {
                    echo "Deploying application..."
                    echo "Deployment completed (simulated)"
                }
            }
        }
    }
    
    post {
        always {
            script {
                echo "=== BUILD SUMMARY ==="
                echo "Build Number: ${env.BUILD_NUMBER}"
                echo "Status: ${currentBuild.currentResult}"
                echo "Duration: ${currentBuild.durationString}"
                echo "Commit: ${env.GIT_COMMIT_SHORT}"
                echo "========================="
                
                currentBuild.description = "Commit: ${env.GIT_COMMIT_SHORT}"
            }
        }
        
        success {
            script {
                echo "Pipeline completed successfully!"
            }
        }
        
        failure {
            script {
                echo "Pipeline failed!"
            }
        }
        
        cleanup {
            echo "Cleaning up workspace..."
        }
    }
}
