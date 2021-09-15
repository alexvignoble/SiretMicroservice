def dockerImage
pipeline {
    agent any
    tools {
        maven 'maven'
        jdk 'jdk8'
        dockerTool 'docker'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -U -DskipTests clean package'
            }
        }

        stage('Test') {
            parallel {
                stage('Unit Tests') {
                    steps {
                        sh 'mvn surefire:test@unit-tests'
                    }
                }
                stage('Integration Tests') {
                    steps {
                        sh 'mvn surefire:test@integration-tests'
                    }
                }
            }
        }

        stage('Build Docker image') {
            steps {
               script {
                dockerImage = docker.build('company/siretservice')
               }
            }
        }

        stage('Push Docker image') {
            when {
                branch 'master'
            }
            steps {
               script {
                    //docker.withRegistry('https://custom.docker.repository', 'credentials-id') {
                    //    dockerImage.push("${env.BUILD_NUMBER}")
                    //    dockerImage.push("latest")
                    //}
                    echo 'TODO: Push'
               }
            }
        }

        stage('Deploy to DEV') {
            when {
                branch 'master'
            }
            steps {
                echo 'TODO: Deployment to DEV env'
            }
        }

        stage('Run QA tests') {
            when {
                not { changeRequest() }
            }
            steps {
                echo 'TODO: Wait for deployment to finish and trigger Non-Regression tests'
            }
        }

        stage('Deploy to TST') {
            when {
                branch pattern: "release-\\d+", comparator: "REGEXP"
            }
            steps {
                echo 'TODO: Deployment to TST env'
            }
        }
    }
}