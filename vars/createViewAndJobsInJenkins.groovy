//import java.util.logging.Logger
import hudson.model.*
import hudson.model.ListView
import com.seh.utils.*
def call(body) {
   // transient Logger logger = Logger.getLogger('com.seh.utils')
    Map jiraParams= [:]
    def APP_CODE
    String appName;
    def repoNames = "${params.REPO_NAMES}".split(',')
    try {
    timeout(time: 60, unit: 'MINUTES') {
    node{
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
            APPLICATION_CODE = "${params.APP_CODE}"
        }
        stages {
            stage("Create view in Jenkins") {
                steps {
                    script{
                        Jenkins jenkins = Jenkins.getInstance()
                        def viewName = "${APPLICATION_CODE}"
                        println "view name is "+viewName
                        def myView = hudson.model.Hudson.instance.getView(viewName)
                        if (myView == null) {
                        jenkins.addView(new ListView(viewName))
                        myView.save()
                        // get the view
                        }
                        println "view is "+myView
                        println "view created "+myView
                    }
                }   
            }
            stage("Create Jenkins Job") {
                steps{
                    script{
                        println "reponames are"+repoNames
                        for(repo in repoNames) {
                            new autoCreateJob().call(repo,APPLICATION_CODE)
                        }
                    }
                }
            }
            /* stage("Create Repos") {
                steps {
                    script {
                        for(repo in repoNames) {
                            new autoCreateRepos().call(repo,appName)
                        }
                    }
                }   
            } */
        }
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