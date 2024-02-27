import hudson.util

def call(body)
{
def agent = params.TARGET_SERVER
def indServers = ["lxsehsg020","lxsehsg021"] as String[]
def sitServerList 
    pipeline {
        agent { label 'devops_automation' }
        options {
            skipDefaultCheckout()
            timestamps()
            buildDiscarder(logRotator(numToKeepStr: '20'))
        }
        environment {
            BUILDSERVER_WORKSPACE = "${WORKSPACE}"
        }
        stages {
            stage('Initialization'){
                
            }
            stage ("Checkout SCM")
            {
                steps{
                    script {
                        for (svr in sitServerList) {
                            println("Checkout SCM for "+svr)
                            node(svr)
                            {
                                cleanWs()
                                //clone the code from bitbucket repo
                                def gitHubURL = getGlobalPropertiesValue 'GitHubUrl'
                                git url: "${gitHubURL}/devops-automation.git", branch: "development-hybrid" , credentialsId: 'devops_automation_buildUser'
                                propertyFileName = body.getAt("PropertyFileName")
                                propertyFolderName = body.getAt("PropertyFolderName")
                            }
                        }
                    }
                }
            }
        }
    }
}