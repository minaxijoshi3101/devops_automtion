//@Library('devops_automation') _
//@Library("jenkins-shared-library@feature/SI-280-do-ui-enhancements")_
try 
{
timeout(time: 60, unit: 'MINUTES') {
pipeline {
    Map jiraParams= [:]
    def JIRA_KEY = params.JIRA_ISSUE_KEY
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
        APP_CODE = params.APP_CODE
    }
    String appName;
    stages {
        stage("Create view in Jenkins") {
            steps {
                script {
                    def viewName = "${APP_CODE}"
                    def view = jenkins.model.Jenkins.instance.getView(viewName)
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
         node("Jenkins_Agent_NON_PROD") {
         cleanWs()
        }
    }
