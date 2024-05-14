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
                    sh "test -f pom.xml"
                }
            }
//             post {
//                 always {
//                     junit 'target/surefire-reports/*.xml'
//                 }
//             }

        }
        stage('Javadoc') {
            steps {
                script {
                    def maven = tool 'Maven'
                    sh "${maven}/bin/mvn javadoc:javadoc -DreportDir=target/site/apidocs"
                }
            }
        }

//
//         stage('SonarCube') {
//             steps {
//                 script {
//                     def maven = tool 'Maven'
//                     sh "${maven}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=sat_refactory -Dsonar.host.url=http://gpu-epu.univ-savoie.fr:9000 -Dsonar.login=sqa_ba53a54bf38616fec9572cda5cc21b1345ef6463"
//                 }
//             }
//         }
        stage('SCM') {
            steps {
                checkout scm
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    def maven = tool 'Maven';
                    withSonarQubeEnv(credentialsId: 'INFO832-Alexis', installationName: 'server-sonar-alexis') {
                        sh "${maven}/bin/mvn clean verify sonar:sonar -Dsonar.token=sqp_94c9cf65fa929f3801a9348974d4b03240173f8d"
                    }
                }
            }
        }
    }
}
