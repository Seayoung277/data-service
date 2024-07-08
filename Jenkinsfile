pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                // Add your build steps here
                sh 'echo "Building..."'
            }
        }
        stage('Test') {
            steps {
                // Add your test steps here
                sh 'echo "Testing..."'
            }
        }
        stage('Deploy') {
            steps {
                // Add your deployment steps here
                sh 'echo "Deploying..."'
            }
        }
    }
    post {
        always {
            cleanWs()
        }
        success {
            script {
                if (env.CHANGE_ID) {
                    githubNotify context: 'Jenkins', description: 'Build succeeded', status: 'SUCCESS'
                }
            }
        }
        failure {
            script {
                if (env.CHANGE_ID) {
                    githubNotify context: 'Jenkins', description: 'Build failed', status: 'FAILURE'
                }
            }
        }
    }
}