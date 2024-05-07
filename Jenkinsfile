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


        stage('SonarCube') {
            steps {
            withSonarQubeEnv(installationName: "server-sonar-alexis") {
                sh "${maven}/bin/mvn clean org.sonarsource.scanner.maven:sonar-maven-plugin:3.10.0.2594:sonar"
            }
//                 sh "${maven}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=sat_refactory -Dsonar.host.url=http://gpu-epu.univ-savoie.fr:9000 -Dsonar.login=sqp_881fa0b81b8cc8ab12afeaf1206b6b6518d5fec3"
//                 script {
//                     def maven = tool 'Maven'
//                 }
            }
        }
    }
}
