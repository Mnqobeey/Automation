pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven-3.9'
    }

    options {
        disableConcurrentBuilds()
        timestamps()
        timeout(time: 20, unit: 'MINUTES')
    }

    environment {
        MAVEN_OPTS = '-Dfile.encoding=UTF-8'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn -B -DskipTests test'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn -B test -Dheadless=true'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml,target/cucumber-report.xml'
            archiveArtifacts allowEmptyArchive: true, artifacts: 'target/cucumber-report.html,target/cucumber-report.json'
        }
    }
}
