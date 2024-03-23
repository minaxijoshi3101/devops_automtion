def call()
{
    pipeline{
        options{
            skipDefaultCheckout()
        }
        agent {
            label ''
        }
        stages{
            stage('Git Operations'){
                steps{
                    script{
                        def random = new Random()
                        def randomNumber = random.nextInt(1000)
                        def fileName = "test_${randomNumber}.txt"
                        withCredentials([usernamePassword(credentialsId: 'devops_automation_buildUser', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')]) {
                            sh """
                            git clone https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/minaxijoshi3101/seh-students.git
                            """
                            // Checkout release/t3 branch
                            sh 'git checkout release/t3'
                            sh 'git config http:sslVerify false'
                            // Create and checkout new branch
                            sh 'git checkout -b release/t3_1.2.3.4'
                            // Check if dbscripts folder exists
                            def dbscriptsExists = fileExists('dbscripts')
                            if (dbscriptsExists) {
                                // Remove files from dbscripts folder
                                sh "rm -rf dbscripts/*"
                                sh 'touch $fileName'
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