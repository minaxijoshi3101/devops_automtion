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

                        curl -XPOST 'http://192.168.18.6:8080/view/SEH/createItem?name=sonar_scan' \
                        --data-binary @sonar_scan.xml \
                        -H "Content-Type:text/xml" \
                        -u "${USER}:1118a269715e91e16144ecae875f87060f" \
                        -H \$CRUMB

                        #Create folder as per repo name
                        curl -s -XPOST 'http://192.168.18.6:8080/view/SEH/createItem?name=${repo}&mode=com.cloudbees.hudson.plugins.folder.#Folder&from=&json=%7B%22name%22%3A%22SEH%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' \
                        -u "${USER}:1118a269715e91e16144ecae875f87060f" \
                        -H "Content-Type:application/x-www-form-urlencoded" \
                        -H "\$CRUMB"
                        
                        #create folders for SIT, UAT, PRE-PROD and PROD
                        curl -XPOST 'http://10.13.12.106:8080/view/SEH/job/${repo}/createItem?name=SIT&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22SIT%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' \
                        --user ${USER}:1118a269715e91e16144ecae875f87060f \
                        -H "Content-Type:application/x-www-form-urlencoded" \
                        -H "\$CRUMB

                        curl -XPOST 'http://10.13.12.106:8080/view/SEH/job/${repo}/createItem?name=UAT&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22SIT%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' \
                        --user ${USER}:1118a269715e91e16144ecae875f87060f \
                        -H "Content-Type:application/x-www-form-urlencoded" \
                        -H "\$CRUMB

                        curl -XPOST 'http://10.13.12.106:8080/view/SEH/job/${repo}/createItem?name=PRE_PROD&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22SIT%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' \
                        --user ${USER}:1118a269715e91e16144ecae875f87060f \
                        -H "Content-Type:application/x-www-form-urlencoded" \
                        -H "\$CRUMB

                        curl -XPOST 'http://10.13.12.106:8080/view/SEH/job/${repo}/createItem?name=PROD&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22SIT%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' \
                        --user ${USER}:1118a269715e91e16144ecae875f87060f \
                        -H "Content-Type:application/x-www-form-urlencoded" \
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