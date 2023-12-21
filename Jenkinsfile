pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                 checkout scmGit(branches: [[name: '*/master']], extensions: [],
userRemoteConfigs: [[url: 'https://github.com/yadavs19/todoapp.git']])
                echo 'successful checkout'
} }
        stage('Build jar and image using Docker file ') {
            steps {
                script {
                     def imageTag = "skrjit89/todoapijenkins:1.0"
                    docker.build(imageTag, '.')
                    echo 'successful Build Docker Image'
                }
} }
        stage('Push to Docker Hub') {
            steps {
                script {
                    withDockerRegistry(credentialsId: 'docker', url: 'https://index.docker.io/v1/') {
                        def imageTag = "skrjit89/todoapijenkins:1.0"
                        docker.image(imageTag).push()
                        echo 'successful Push to Docker Hub'
                    }
} }
} }
}
