import java.util.logging.Logger
def call(body) {
    Logger logger = Logger.getLogger('com.seh.utils')
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
                        def viewName = "${APP_CODE}"
                        def view = jenkins.model.Jenkins.instance.getView(viewName)
                        logger.info('view will be created')
                        if (view == null) {
                            view = new hudson.model.ListView(viewName)
                            jenkins.model.Jenkins.instance.addView(view)
                        }
                        // Save the view
                        view.save()

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