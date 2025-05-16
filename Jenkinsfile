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
        memory: "2Gi"
        cpu: "1000m"
      limits:
        memory: "4Gi"
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
                readCache name: 'mvn-cache'
            }
        }

        stage('Build with Maven') {
            steps {
                container('maven') {
                    sh '''
        echo "📦 Number of files in maven local repo before build:"
        find /home/jenkins/.m2/repository -type f | wc -l

        echo "🧮 Total size of local Maven repo before build:"
        du -sh /home/jenkins/.m2/repository
                    sh 'mvn install -DskipTests -Dmaven.repo.local=./maven-repo'
                    // New summary block
      sh '''
        echo "📦 Number of files in maven local repo files after:"
        find /home/jenkins/.m2/repository -type f | wc -l

        echo "🧮 Total size of local Maven repo after build:"
        du -sh /home/jenkins/.m2/repository
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
