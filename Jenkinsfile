pipeline {
    agent any
    
    options {
<<<<<<< HEAD
        timeout(time: 15, unit: 'MINUTES')
        disableConcurrentBuilds()
        timestamps()
=======
        timeout(time: 60, unit: 'MINUTES')
        disableConcurrentBuilds()
>>>>>>> 00225920aaa4131c464f3c1e3bfeb08af6c0cf89
    }
    
    triggers {
        githubPush()
    }
    
<<<<<<< HEAD
    environment {
        GIT_COMMIT_SHORT = "${env.GIT_COMMIT.take(8)}"
        BUILD_TIMESTAMP = new Date().format("yyyyMMdd-HHmmss")
        ARTIFACT_NAME = "automation-build-${env.BUILD_NUMBER}.jar"
=======
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
>>>>>>> 00225920aaa4131c464f3c1e3bfeb08af6c0cf89
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
<<<<<<< HEAD
                    extensions: [
                        [$class: 'CleanBeforeCheckout'],
                        [$class: 'LocalBranch', localBranch: 'main']
                    ],
=======
                    extensions: [],
>>>>>>> 00225920aaa4131c464f3c1e3bfeb08af6c0cf89
                    userRemoteConfigs: [[
                        url: 'https://github.com/Mnqobeey/Automation.git',
                        credentialsId: 'github-token'
                    ]]
                ])
<<<<<<< HEAD
                
                script {
                    // Get commit info
                    sh '''
                        echo "Git Commit: ${GIT_COMMIT}"
                        echo "Git Branch: main"
                        echo "Commit Author: $(git log -1 --pretty=format:'%an')"
                        echo "Commit Message: $(git log -1 --pretty=format:'%s')"
                    '''
                }
=======
>>>>>>> 00225920aaa4131c464f3c1e3bfeb08af6c0cf89
            }
        }
        
        stage('Build') {
            steps {
                script {
<<<<<<< HEAD
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
=======
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
>>>>>>> 00225920aaa4131c464f3c1e3bfeb08af6c0cf89
                    }
                }
            }
        }
        
        stage('Test') {
            steps {
                script {
<<<<<<< HEAD
                    echo "Running tests..."
                    
=======
>>>>>>> 00225920aaa4131c464f3c1e3bfeb08af6c0cf89
                    if (fileExists('pom.xml')) {
                        sh 'mvn test'
                    } else if (fileExists('package.json')) {
                        sh 'npm test'
<<<<<<< HEAD
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
=======
                    } else {
                        sh 'echo "No specific test command found"'
                    }
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
>>>>>>> 00225920aaa4131c464f3c1e3bfeb08af6c0cf89
                }
            }
        }
        
        stage('Quality Checks') {
            steps {
                script {
                    echo "Running quality checks..."
                    
<<<<<<< HEAD
                    // Run linting if configured
                    if (fileExists('package.json') && sh(script: 'npm run lint -- --help >/dev/null 2>&1', returnStatus: true) == 0) {
                        sh 'npm run lint'
                    }
                    
                    // Run security checks if available
                    if (fileExists('package.json') && sh(script: 'npm audit --help >/dev/null 2>&1', returnStatus: true) == 0) {
                        sh 'npm audit || true'  // Don't fail on audit warnings
=======
                    // Code linting
                    if (fileExists('package.json')) {
                        sh 'npm run lint || true'  // Continue even if linting fails
                    }
                    
                    // Security scanning
                    if (fileExists('package.json')) {
                        sh 'npm audit || true'
>>>>>>> 00225920aaa4131c464f3c1e3bfeb08af6c0cf89
                    }
                }
            }
        }
        
        stage('Package') {
<<<<<<< HEAD
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
=======
            steps {
                script {
                    if (fileExists('pom.xml')) {
                        sh 'mvn package -DskipTests'
                        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                    } else if (fileExists('package.json')) {
                        sh 'npm run package || true'
>>>>>>> 00225920aaa4131c464f3c1e3bfeb08af6c0cf89
                    }
                }
            }
        }
        
        stage('Deploy') {
            when {
<<<<<<< HEAD
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
=======
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
>>>>>>> 00225920aaa4131c464f3c1e3bfeb08af6c0cf89
                }
            }
        }
    }
    
    post {
        always {
<<<<<<< HEAD
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
=======
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
>>>>>>> 00225920aaa4131c464f3c1e3bfeb08af6c0cf89
