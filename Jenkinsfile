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
    command:
    - cat
    tty: true
    volumeMounts:
    - name: maven-cache
      mountPath: /home/jenkins/.m2
  volumes:
  - name: maven-cache
    emptyDir: {}
"""
        }
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
                    sh 'mvn install -DskipTests -Dmaven.repo.local=./maven-repo'
                }
            }
        }
    }

    post {
        success {
           
            writeCache name: 'mvn-cache', includes: 'maven-repo/**'
        }
    }
}
