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
    resources:
      requests:
        memory: "6Gi"
        cpu: "1000m"
      limits:
        memory: "6Gi"
        cpu: "2000m"
    volumeMounts:
    - name: maven-cache
      mountPath: /home/jenkins/.m2
  volumes:
  - name: maven-cache
    emptyDir: {}
"""
        }
    }

    environment {
        // Tell Maven where to store its downloaded dependencies
        MAVEN_REPO = 'maven-repo'
    }

    stages {
        stage('Prepare Workspace') {
            steps {
                container('maven') {
                    sh 'mkdir -p ${MAVEN_REPO}'
                }
            }
        }

        stage('Restore Maven Cache') {
            steps {
                readCache name: 'mvn-cache'
            }
        }

        stage('Build with Maven') {
            steps {
                container('maven') {
                    sh '''
                        echo "📦 Number of files in Maven local repo before build:"
                        find ${MAVEN_REPO} -type f | wc -l || echo "0"

                        echo "🧮 Total size of Maven local repo before build:"
                        du -sh ${MAVEN_REPO} || echo "0"
                    '''
                    sh 'mvn install -DskipTests -Dmaven.repo.local=${MAVEN_REPO}'
                    sh '''
                        echo "📦 Number of files in Maven local repo after build:"
                        find ${MAVEN_REPO} -type f | wc -l

                        echo "🧮 Total size of Maven local repo after build:"
                        du -sh ${MAVEN_REPO}
                    '''
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
