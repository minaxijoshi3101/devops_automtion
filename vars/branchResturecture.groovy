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
                    withCredentials([string(credentialsId: 'GITHUB_TOKEN', variable: 'GITHUB_TOKEN')]) {
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
                            sh """
                                git add .
                                git commit -m "cleanup"
                                git push "https://${GIT_USERNAME}:${GITHUB_TOKEN}@github.com/minaxijoshi3101/seh-students.git"
                                #git push -f origin release/release_t3_1.2.3.4
                            """    
                        }
                    
                    }
                }
            }
        }
    }
}
