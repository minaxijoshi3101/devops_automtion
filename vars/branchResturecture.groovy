pipeline {
    options {
        skipDefaultCheckout()
    }
    /* agent {
        label 'devops_automation'
    } */
    agent any
    stages {
        stage('Git Operations') {
            steps {
                script {
                    cleanWs()
                    def random = new Random()
                    def randomNumber = random.nextInt(1000)
                    def fileName = "test_${randomNumber}.txt"
                    println fileName
                    withCredentials([usernamePassword(credentialsId: 'devops_automation_buildUser', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')]) {
                        sh """
                            git clone "https://${GIT_USERNAME}:Joshi%402405@github.com/minaxijoshi3101/seh-students.git"
                        """
                        dir("seh-students"){
                            // Checkout release/t3 branch
                            sh 'git checkout release/release_t3'
                            //sh 'git config http:sslVerify false'
                            // Create and checkout new branch
                            sh 'git checkout -b release/release_t3_1.2.3.4'
                            // Check if dbscripts folder exists
                            def dbscriptsExists = fileExists('cbo/dbscripts')
                            if (dbscriptsExists) {
                                // Remove files from dbscripts folder
                                sh "rm -rf dbscripts/*"
                                sh "touch ${fileName}"
                            } else {
                                echo 'dbscripts folder does not exist'
                            }
                            
                            // Push new branch
                            sh 'git push origin release/t3_1.2.3.4'
                        }
                    
                    }
                }
            }
        }
    }
}
