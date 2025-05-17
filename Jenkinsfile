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

    stages {
        stage('Restore Maven Cache') {
            steps {
                container('maven') {
                    // Ensure the directory exists before restoring
                    sh 'mkdir -p maven-repo'
                }
                readCache name: 'mvn-cache', dir: 'maven-repo'
            }
        }

        stage('Build with Maven') {
            steps {
                container('maven') {
                    sh '''
                        echo "📦 Number of files in Maven local repo before build:"
                        find maven-repo -type f | wc -l || echo "0"

                        echo "🧮 Total size of Maven local repo before build:"
                        du -sh maven-repo || echo "0"
                    '''
                    sh 'mvn install -DskipTests -Dmaven.repo.local=maven-repo'
                    sh '''
                        echo "📦 Number of files in Maven local repo after build:"
                        find maven-repo -type f | wc -l

                        echo "🧮 Total size of Maven local repo after build:"
                        du -sh maven-repo
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
