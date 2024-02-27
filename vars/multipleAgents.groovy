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
                steps {
                    script {
                        switch(agent)
                        {
                            case "ALL IND":
                                sitServerList = indServers
                            break
                            case "ALL SG":
                                sitServerList = sgServers
                            break
                        }

                    }
                }
            }
            stage ("Checkout SCM from perticular region")
            {
                steps{
                    script {
                        for (svr in sitServerList) {
                            println("Checkout SCM for "+svr)
                            node(svr)
                            {
                                cleanWs()
                                //clone the code from bitbucket repo
                                def bitBucketURL = getGlobalPropertiesValue 'BitBucketUrl' 
                            }
                        }
                    }
                }
            }
        }
    }
}