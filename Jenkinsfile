pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK'
    }

    parameters {
        string(name: 'BROWSER', defaultValue: 'chrome', description: 'Browser for tests (chrome, firefox)')
        string(name: 'ENVIRONMENT', defaultValue: 'qa', description: 'Test environment (qa, staging)')
    }

    environment {
        SONAR_PROJECT_KEY = 'smsc-cucumber-automation'
        SONAR_PROJECT_NAME = 'SMS Cucumber Automation'
    }

    

    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[
                        url: 'https://github.com/Mnqobeey/Automation',
                        // 4. Ensure this credential exists in Jenkins
                        credentialsId: 'github-creds'
                    ]]
                ])
            }
        }

        stage('Setup') {
            steps {
                sh '''
                    echo "Build Number: ${BUILD_NUMBER}"
                    echo "Testing with Browser: ${BROWSER}"
                    echo "Target Environment: ${ENVIRONMENT}"
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

        stage('SonarQube Analysis') {
            steps {
                // 5. **** 'My SonarQube Server' must match your Jenkins configuration ****
                withSonarQubeEnv('My SonarQube Server') {
                    // This command runs tests WITH coverage (verify) and then Sonar analysis
                    sh 'mvn clean verify sonar:sonar'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    // If quality fails, the pipeline stops here
                    waitForQualityGate abortPipeline: true
                }
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

<<<<<<< HEAD
        stage('Docker Build') {
            steps {
                script {
                    // Uncomment and customize if you want to build a Docker image
                    // docker.build("your-username/smsc-automation:${env.BUILD_ID}")
                }
            }
        }
    }
=======
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('My SonarQube Server') {
                    sh 'mvn sonar:sonar'

                }
            }

        }
>>>>>>> 803b7260ae444aa4e4c4c2a96e4c209c3c53debc

    post {
        always {
            // Archive test reports
            junit 'target/surefire-reports/**/*.xml'
            cucumber reportTitle: 'Cucumber Report',
                   fileIncludePattern: '**/cucumber.json',
                   jsonReportDirectory: 'target'
        }
        success {
            echo 'Pipeline Succeeded!'
            emailext (
<<<<<<< HEAD
                subject: "SUCCESS: Pipeline '${env.JOB_NAME}' #${env.BUILD_NUMBER}",
                body: "All stages passed. View details: ${env.BUILD_URL}",
                to: 'thandoomntamboo@gmail.com', 
=======
                subject: "BUILD SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "The build ${env.JOB_NAME} #${env.BUILD_NUMBER} passed successfully.\n\nCheck console output at: ${env.BUILD_URL}",
                to: 'team@example.com',
>>>>>>> 803b7260ae444aa4e4c4c2a96e4c209c3c53debc
                attachLog: false
            )
        }
        failure {
            echo 'Pipeline Failed!'pipeline {
    agent any

    tools {
        maven 'Maven' // Ensure this tool is configured in Jenkins (Manage Jenkins > Tools)
        jdk 'JDK'     // Ensure this tool is configured in Jenkins
    }

    parameters {
        string(name: 'BROWSER', defaultValue: 'chrome', description: 'Browser for tests (chrome, firefox)')
        string(name: 'ENVIRONMENT', defaultValue: 'qa', description: 'Test environment (qa, staging)')
    }

    environment {
        // Define your SonarQube project details here
        SONAR_PROJECT_KEY = 'smsc-cucumber-automation'
        SONAR_PROJECT_NAME = 'SMS Cucumber Automation'
    }

    stages {
        // Stage 1: Get the latest code from GitHub
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[
                        url: 'https://github.com/Mnqobeey/Automation',
                        // Ensure 'github-creds' exists in Jenkins (Manage Jenkins > Credentials)
                        credentialsId: 'github-creds'
                    ]]
                ])
            }
        }

        // Stage 2: Display environment information
        stage('Setup') {
            steps {
                sh '''
                    echo "Build Number: ${BUILD_NUMBER}"
                    echo "Testing with Browser: ${BROWSER}"
                    echo "Target Environment: ${ENVIRONMENT}"
                    java -version
                    mvn -version
                '''
            }
        }

        // Stage 3: Compile the source code (added back from your first script)
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        // Stage 4: Run analysis and send results to SonarQube
        stage('SonarQube Analysis') {
            steps {
                // 'My SonarQube Server' must match the server name configured in Jenkins System Settings
                withSonarQubeEnv('My SonarQube Server') {
                    // 'verify' goal runs tests and generates the JaCoCo code coverage report
                    sh 'mvn clean verify sonar:sonar'
                }
            }
        }

        // Stage 5: Halt pipeline if code quality does not meet the defined standards
        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        // Stage 6: Run the functional tests (now only runs if quality gate passes)
        stage('Test') {
            steps {
                sh """
                    mvn test \\
                    -Dbrowser=${BROWSER} \\
                    -Denvironment=${ENVIRONMENT} \\
                    -Dcucumber.filter.tags="@regression"
                """
            }
        }

        // Stage 7: (Optional) Build a Docker image from the application
        stage('Docker Build') {
            steps {
                script {
                    // Uncomment and customize when you are ready to build Docker images
                    // docker.build("your-username/smsc-automation:${env.BUILD_ID}")
                }
            }
        }
    }

    // Post-build actions always run, regardless of success or failure
    post {
        always {
            // Archive test reports for Jenkins to display
            junit 'target/surefire-reports/**/*.xml'
            cucumber reportTitle: 'Cucumber Report',
                   fileIncludePattern: '**/cucumber.json',
                   jsonReportDirectory: 'target'
        }
        success {
            echo 'Pipeline Succeeded!'
            emailext (
<<<<<<< HEAD
                subject: "SUCCESS: Pipeline '${env.JOB_NAME}' #${env.BUILD_NUMBER}",
                body: "All stages passed. View details: ${env.BUILD_URL}",
                to: 'thandoomntamboo@gmail.com', // Your email
                attachLog: false
            )
        }
        failure {
            echo 'Pipeline Failed!'
            emailext (
                subject: "FAILURE: Pipeline '${env.JOB_NAME}' #${env.BUILD_NUMBER}",
                body: "Pipeline failed. Check the logs: ${env.BUILD_URL}",
                to: 'thandoomntamboo@gmail.com', // Your email
=======
                subject: "BUILD FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "The build ${env.JOB_NAME} #${env.BUILD_NUMBER} failed.\n\nCheck console output at: ${env.BUILD_URL}",
                to: 'team@example.com',
>>>>>>> 803b7260ae444aa4e4c4c2a96e4c209c3c53debc
                attachLog: true
            )
        }
    }
}
            emailext (
                subject: "FAILURE: Pipeline '${env.JOB_NAME}' #${env.BUILD_NUMBER}",
                body: "Pipeline failed. Check logs: ${env.BUILD_URL}",
                to: 'thandoomntamboo@gmail.com', // Change this email
                attachLog: true
            )
        }
    }
}