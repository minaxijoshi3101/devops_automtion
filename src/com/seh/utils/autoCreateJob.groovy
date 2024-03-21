package com.seh.utils;


def call(String repo,String appName) {
      try{ 
            def CRUMB
            def auth_token
            sh "mkdir -p  ${repo}"
            def fileWriteSIT = libraryResource "jenkins_job_SIT.xml"
            writeFile file: "${repo}/sit_config.xml", text: fileWriteSIT
            def fileWriteSonarScan = libraryResource "sonar_scan.xml"
            writeFile file: "${repo}/sonar_scan.xml", text: fileWriteSonarScan
            def http_response
            withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'svc_devops_jenkins_token',
            usernameVariable: 'USER', passwordVariable: 'PASSWORD']]) {
                  sh """
                        cd ${repo}
                        # Retrieve Jenkins CSRF crumb using username and API token
                        CRUMB=\$(curl -s -u ${USER}:1118a269715e91e16144ecae875f87060f 'http://192.168.18.6:8080/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)')
                        
                        echo \$CRUMB
                       
                        #create sonar scan common job for all the modules

                        #Create folder as per repo name
                        #
                        curl -XPOST 'http://192.168.18.6:8080/view/SEH/job/${repo}/createItem?name=SIT&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json={"name":"SIT","mode":"com.cloudbees.hudson.plugins.folder.Folder","from":"","Submit":"OK"}&Submit=OK' \
                        -u ${USER}:1118a269715e91e16144ecae875f87060f \
                        -H "Content-Type: application/x-www-form-urlencoded" \
                        -H "\$CRUMB

                        #Create jenkins job
                        #Create build and depolyment job

                  """
            } 
      } catch(Exception e)
      {
            echo "Caught: ${e}"
            throw e
      }
}