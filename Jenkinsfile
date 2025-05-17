pipeline {
    agent {
        kubernetes {
            label 'maven-pod'
            defaultContainer 'maven'
            yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: maven
    image: maven:3.9-eclipse-temurin-17
    command: ['cat']
    tty: true
    resources:
      requests:
        memory: "6Gi"
        cpu: "1000m"
      limits:
        memory: "6Gi"
        cpu: "2000m"
"""
        }
    }

    environment {
        MAVEN_REPO_LOCAL = "${env.WORKSPACE}/.m2/repository"
    }

    stages {
        stage('Restore Maven Cache') {
            steps {
                readCache name: 'mvn-cache'
            }
        }

        stage('Build with Maven') {
            steps {
                container('maven') {
                    sh '''
                        echo "📦 Files before build:"
                        find .m2/repository -type f | wc -l || echo "0"

                        echo "🧮 Size before build:"
                        du -sh .m2/repository || echo "0"

                        mvn install -DskipTests -Dmaven.repo.local=.m2/repository

                        echo "📦 Files after build:"
                        find .m2/repository -type f | wc -l

                        echo "🧮 Size after build:"
                        du -sh .m2/repository
                    '''
                }
            }
        }
    }

    post {
        success {
            writeCache name: 'mvn-cache', includes: '.m2/repository/**'
        }
    }
}
