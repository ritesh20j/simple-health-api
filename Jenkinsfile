pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    credentialsId: 'github-creds',
                    url: 'https://github.com/ritesh20j/simple-health-api.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        
    }
    stage('Docker Build') {
    steps {
        sh 'docker build -t simple-health-api:latest .'
    }
}

stage('Deploy') {
    steps {
        sh '''
        docker stop health || true
        docker rm health || true
        docker run -d -p 8081:8080 --name health simple-health-api:latest
        '''
    }
}


}
