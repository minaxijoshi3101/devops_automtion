//import java.util.logging.Logger
def call(body) {
   // transient Logger logger = Logger.getLogger('com.seh.utils')
    Map jiraParams= [:]
    def APP_CODE
    try {
    timeout(time: 60, unit: 'MINUTES') {
    pipeline {
        /* agent {
            label 'devops_automation'
        } */
        agent any
        options {
            skipDefaultCheckout()
            timestamps()
            buildDiscarder(logRotator(numToKeepStr: '20'))
        }
        environment{
            BUILDSERVER_WORKSPACE = "${WORKSPACE}"
            APP_CODE = "${params.APP_CODE}"
        }
        stages {
            stage("Create view in Jenkins") {
                steps {
                    script {
                        Jenkins jenkins = Jenkins.getInstance()
                        def viewName = "${APP_CODE}"
                        jenkins.addView(new ListView(viewName))
                        // get the view
                        def myView = hudson.model.Hudson.instance.getView(viewName)

                        println "view is "+myView
                        //logger.info('view will be created')
                        
                        // Save the view
                        myView.save()
                        println "view created "+myView
                    }
                }   
            }
            /* stage("Create Repo & Jenkins Job") {
                steps {
                    script {
                        for(repo in repoNames) {
                            if(!exclusions.contains("Exclude Jenkins Jobs")){
                                new autoCreateJob().call(repo,appName)
                            } 
                        }
                    }
                }   
            } */
        }
        }
    }
    }   catch (err) {
            echo "Caught: ${err}"
            throw err
        }finally {
            cleanWs()
        }
}