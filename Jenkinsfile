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
      mountPath: /tmp/jenkins
  volumes:
  - name: maven-cache
    emptyDir: {}
"""
        }
    }

    stages {
        stage('Restore Maven Cache') {
            steps {
                dir('maven-cache') {
                    readCache name: 'mvn-cache'
                }
                sh '''
                    mkdir -p /tmp/jenkins/maven-repo
                    cp -r maven-cache/* /tmp/jenkins/maven-repo || true
                '''
            }
        }

        stage('Build with Maven') {
            steps {
                container('maven') {
                    sh '''
                        echo "📦 Number of files in Maven local repo before build:"
                        find /tmp/jenkins/maven-repo -type f | wc -l || echo "0"

                        echo "🧮 Total size of Maven local repo before build:"
                        du -sh /tmp/jenkins/maven-repo || echo "0"
                    '''
                    sh 'mvn install -DskipTests -Dmaven.repo.local=/tmp/jenkins/maven-repo'
                    sh '''
                        echo "📦 Number of files in Maven local repo after build:"
                        find /tmp/jenkins/maven-repo -type f | wc -l

                        echo "🧮 Total size of Maven local repo after build:"
                        du -sh /tmp/jenkins/maven-repo
                    '''
                }
            }
        }
    }

    post {
        success {
            container('maven') {
                sh '''
                    echo "📦 Copying Maven repo to workspace for caching..."
                    mkdir -p $WORKSPACE/maven-cache
                    cp -r /tmp/jenkins/maven-repo/* $WORKSPACE/maven-cache || true
                '''
                dir('maven-cache') {
                    writeCache name: 'mvn-cache', includes: '**'
                }
            }
        }
    }
}
