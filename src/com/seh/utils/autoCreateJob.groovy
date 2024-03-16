package com.seh.utils;


def call(String repo,String appName) {
      try{
            dir("$WORKSPACE"){
                  node {
                        def CRUMB
                        def auth_token
                        sh "mkdir -p  ${repo}"
                        def fileWriteSIT = libraryResource "jenkins_job_SIT.xml"
                        writeFile file: "${repo}/sit_config.xml", text: fileWriteSIT
                        def http_response
                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'svc_devops_jenkins_token',
                        usernameVariable: 'USER', passwordVariable: 'PASSWORD']]) {
                              sh """
                                    cd ${repo}
                                    # Retrieve Jenkins CSRF crumb
                                    CRUMB=\$(curl -s -u ${USER}:1118a269715e91e16144ecae875f87060f 'http://192.168.18.6:8080/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)')
                                    echo \$CRUMB
                                    curl -s -XPOST 'http://192.168.18.6:8080/createItem?name=${repo}&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22SEH%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' \
                                    -u "${USER}:1118a269715e91e16144ecae875f87060f" \
                                    -H "Content-Type:application/x-www-form-urlencoded" \
                                    -H "\$CRUMB"
                              """
                        } 
                  }
            }
      catch(Exception e)
      {
            echo "Caught: ${e}"
            throw e
      }
}