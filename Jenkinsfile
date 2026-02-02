pipeline {
    agent any
    
    tools {
        // This ensures ALL stages use JDK 17 and maven 3.9.8 (stable)
        jdk 'jdk17'
        maven-3.9
    }
    
    options {
        timeout(time: 15, unit: 'MINUTES')
        disableConcurrentBuilds()
        timestamps()
    }
    
    triggers {
        githubPush()
    }
    
    environment {
        GIT_COMMIT_SHORT = "${env.GIT_COMMIT.take(8)}"
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
                        credentialsId: 'github-token'
                    ]]
                ])
                
                script {
                    // Get commit info
                    sh '''
                        echo "Git Commit: ${GIT_COMMIT}"
                        echo "Git Branch: main"
                        echo "Commit Author: $(git log -1 --pretty=format:'%an')"
                        echo "Commit Message: $(git log -1 --pretty=format:'%s')"
                    '''
                }
            }
        }
        
        stage('Build') {
            steps {
                script {
                    echo "Building Automation project..."
                    
                    // Detect project type and build
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
                    // Archive artifacts if they exist
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
                        // Generic test execution
                        sh 'python -m pytest tests/ || true'
                    } else {
                        echo "No test configuration found - skipping tests"
                    }
                }
            }
            
            post {
                always {
                    // Collect test results if they exist
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
                    
                    // Run linting if configured
                    if (fileExists('package.json') && sh(script: 'npm run lint -- --help >/dev/null 2>&1', returnStatus: true) == 0) {
                        sh 'npm run lint'
                    }
                    
                    // Run security checks if available
                    if (fileExists('package.json') && sh(script: 'npm audit --help >/dev/null 2>&1', returnStatus: true) == 0) {
                        sh 'npm audit || true'  // Don't fail on audit warnings
                    }
                }
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                script {
                    echo "Running SonarQube analysis..."
                    
                    // Detect project type and run appropriate SonarQube scanner
                    if (fileExists('pom.xml')) {
                        // For Maven projects
                        withSonarQubeEnv('SonarQube') { // Make sure 'SonarQube' matches your Jenkins server config name
                            sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=Automation -Dsonar.projectName="Automation"'
                        }
                    } else if (fileExists('package.json')) {
                        // For Node.js projects
                        withSonarQubeEnv('SonarQube') {
                            sh 'sonar-scanner -Dsonar.projectKey=Automation -Dsonar.projectName="Automation"'
                        }
                    } else if (fileExists('build.gradle')) {
                        // For Gradle projects
                        withSonarQubeEnv('SonarQube') {
                            sh './gradlew sonarqube -Dsonar.projectKey=Automation -Dsonar.projectName="Automation"'
                        }
                    } else {
                        echo "No supported project type detected for SonarQube analysis"
                    }
                    
                    // This waits for SonarQube to process the analysis and check the Quality Gate
                    timeout(time: 15, unit: 'MINUTES') {
                        waitForQualityGate abortPipeline: true
                    }
                }
            }
            
            post {
                success {
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
                    
                    // Add your deployment logic here
                    // Example:
                    // sh './deploy.sh'
                    // or
                    // sh 'docker push your-registry/automation:${GIT_COMMIT_SHORT}'
                    
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
                echo "Commit: ${GIT_COMMIT_SHORT}"
                echo "========================="
                
                // Update build description
                currentBuild.description = "Commit: ${GIT_COMMIT_SHORT}"
            }
        }
        
        success {
            script {
                echo "Pipeline completed successfully!"
                
                // Send success notification (optional)
                // emailext(
                //     subject: "SUCCESS: Automation Build #${env.BUILD_NUMBER}",
                //     body: "Build completed successfully\nCommit: ${GIT_COMMIT_SHORT}",
                //     to: 'your-email@example.com'
                // )
            }
        }
        
        failure {
            script {
                echo "Pipeline failed!"
                
                // Send failure notification (optional)
                // emailext(
                //     subject: "FAILED: Automation Build #${env.BUILD_NUMBER}",
                //     body: "Build failed\nCheck logs: ${env.BUILD_URL}",
                //     to: 'your-email@example.com'
                // )
            }
        }
        
        cleanup {
            echo "Cleaning up workspace..."
            // Optional: Delete workspace after build
            // cleanWs()
        }
    }
}
