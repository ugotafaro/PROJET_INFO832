pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }

    stages {
        stage('Build') {
            steps {
                script {
                    def maven = tool 'Maven'
                    sh "${maven}/bin/mvn -B -DskipTests clean install"
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    def maven = tool 'Maven'
                    sh "${maven}/bin/mvn -Dmaven.test.failure.ignore=true test"
                }
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('SCM') {
            steps {
                checkout scm
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    def maven = tool 'Maven';
                    withSonarQubeEnv(installationName: "server-sonar-alexis") {
                        sh "${maven}/bin/mvn sonar:sonar -Dsonar.host.url=http://gpu-epu.univ-savoie.fr:9000 -Dsonar.projectKey=groupe2 -Dsonar.login=groupe2 -Dsonar.password=groupe2 -Dsonar.java.binaries=target/classes"
                    }
                }
            }
        }
        stage('Javadoc') {
            steps {
                script {
                    def maven = tool 'Maven'
                    sh "${maven}/bin/mvn javadoc:javadoc -DreportDir=target/site/apidocs -Dmaven.javadoc.failOnError=false"
                }
            }
        }
    }
}