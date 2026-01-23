pipeline {
    agent any

    environment {
        AWS_REGION = "us-east-1"
        AWS_ACCOUNT_ID = "125814533602"
        ECR_REPO = "simple-health-api"
        IMAGE_TAG = "${BUILD_NUMBER}"

        ECR_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO}"
    }

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

        stage('Docker Build') {
            steps {
                sh '''
                docker build -t ${ECR_REPO}:${IMAGE_TAG} .
                '''
            }
        }

        stage('Login to ECR') {
            steps {
                sh '''
                aws ecr get-login-password --region ${AWS_REGION} \
                | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
                '''
            }
        }

        stage('Tag & Push Image to ECR') {
            steps {
                sh '''
                docker tag ${ECR_REPO}:${IMAGE_TAG} ${ECR_URI}:${IMAGE_TAG}
                docker push ${ECR_URI}:${IMAGE_TAG}
                '''
            }
        }
stage('Update K8s Manifest Repo') {
  steps {
    withCredentials([usernamePassword(
      credentialsId: 'github-creds',
      usernameVariable: 'GIT_USER',
      passwordVariable: 'GIT_TOKEN'
    )]) {
      sh '''
        rm -rf simple-health-k8s
        git clone https://${GIT_USER}:${GIT_TOKEN}@github.com/ritesh20j/simple-health-k8s.git
        cd simple-health-k8s/base

        sed -i "s|image: .*|image: 125814533602.dkr.ecr.us-east-1.amazonaws.com/simple-health-api:${BUILD_NUMBER}|" deployment.yaml

        git config user.email jenkins@local
        git config user.name jenkins
        git add deployment.yaml
        git diff --quiet || git commit -m "Update image to ${BUILD_NUMBER}"
        git push origin main
      '''
    }
  }
}



    
    }
}
