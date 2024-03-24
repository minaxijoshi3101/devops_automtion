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
                    def branchName = "release/release_t3_1.2.3.4"
                    println fileName
                    withCredentials([string(credentialsId: 'GITHUB_TOKEN', variable: 'GITHUB_TOKEN')]) {
                        sh """
                            git clone "https://minaxijoshi3101:Joshi%402405@github.com/minaxijoshi3101/seh-students.git"
                        """
                        dir("seh-students"){
                            // Checkout release/t3 branch
                            sh 'git checkout release/release_t3'
                            //sh 'git config http:sslVerify false'
                            // Check if the branch already exists
                            def branchExists = sh(script: "git show-ref --quiet refs/heads/${branchName}", returnStatus: true) == 0
                            
                            if (branchExists) {
                                echo "Branch ${branchName} already exists"
                                sh "git checkout ${branchName}"
                            }
                            else {
                            // Create and checkout new branch
                            echo "Branch ${branchName} doesn't exist"
                            sh "git checkout -b ${branchName}"
                            }
                            // Check if dbscripts folder exists
                            def dbscriptsExists = fileExists('cbo/dbscripts')
                            if (dbscriptsExists) {
                                // Remove files from dbscripts folder
                                sh "rm -rf cbo/dbscripts/*"
                                sh "touch cbo/dbscripts/${fileName}"
                            } else {
                                echo 'dbscripts folder does not exist'
                            }
                            
                            // Push new branch
                            sh """
                                git add .
                                git commit -m "cleanup"
                                git push "https://minaxijoshi3101:${GITHUB_TOKEN}@github.com/minaxijoshi3101/seh-students.git"
                                #git push -f origin release/release_t3_1.2.3.4
                            """    
                        }
                    
                    }
                }
            }
        }
    }
}
