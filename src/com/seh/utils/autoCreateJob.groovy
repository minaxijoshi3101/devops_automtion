package com.seh.utils;

def call(String repo,String appName) {
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

            curl -XPOST 'http://192.168.18.11:8080/jenkins/createItem?name=${appName}&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json={"name":"${appName}","mode":"com.cloudbees.hudson.plugins.folder.Folder","from":"","Submit":"OK"}&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"

            #curl -s -Lkgf -o out.html -n -X POST "http://192.168.18.11:8080/jenkins/${appName}/createItem?name=test&mode=com.cloudbees.hudson.plugins.folder.Folder&Submit=OK" --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"

            # Retrieve Jenkins CSRF crumb
            #CRUMB=\$(curl -s 'http://192.168.18.11:8080/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)' -u ${USER}:${PASSWORD})

            #echo \$CRUMB
            # create folder
            #curl -XPOST 'http://192.168.18.11:8080/createItem?name=${appName}&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22${appName}%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
            echo "${USER}"

            #auth_token=”${USER}:${PASSWORD}”
            #echo "${auth_token}"
            #http_response=\$(curl --cookie-jar ./cookie -s "http://192.168.18.11:8080/jenkins/crumbIssuer/api/json" -u "${USER}:${PASSWORD}")
            #echo "${http_response}"
            #CRUMB=\$(echo "${http_response}" | jq -r '.crumb')

            #CRUMB=\$(curl -s 'http://192.168.18.11:8080/jenkins/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)' -u ${USER}:${PASSWORD})

            #echo "\$CRUMB"

            #curl -XPOST --cookie "./cookie" "http://192.168.18.11:8080/jenkins/createItem?name=${appName}&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22${appName}%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK" --user "${USER}:${PASSWORD}" -H "Content-Type:application/x-www-form-urlencoded" -H "\$CRUMB"

            #curl -X POST -u "${USER}:${PASSWORD}" "http://192.168.18.11:8080/jenkins/createItem?name=${appName}&mode=com.cloudbees.hudson.plugins.folder.Folder" -H "\$(curl -s 'http://192.168.18.11:8080/jenkins/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)' --user ${USER}:${PASSWORD})" --data-urlencode "json={ 'name': '${appName}', 'mode': 'com.cloudbees.hudson.plugins.folder.Folder', 'Submit': 'OK' }"

            #curl -XPOST 'http://192.168.18.11:8080/createItem?name=${appName}&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22${appName}%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
            
            #curl -s -XPOST 'http://192.168.18.9:8080/job/${appName}/createItem?name=coverity_scan' --data-binary @coverity_scan.xml -H "Content-Type:text/xml" -u ${USER}:${PASSWORD}
            #curl -XPOST 'http://192.168.18.9:8080/job/${appName}/createItem?name=${repo}&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22${repo}%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
            #curl -XPOST 'http://192.168.18.9:8080/job/${appName}/job/${repo}/createItem?name=SIT&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22SIT%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
		#curl -XPOST 'http://192.168.18.9:8080/job/${appName}/job/${repo}/createItem?name=UAT&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22UAT%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
            #curl -XPOST 'http://192.168.18.9:8080/job/${appName}/job/${repo}/createItem?name=PRE_PROD&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22PRE_PROD%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
		#curl -XPOST 'http://192.168.18.9:8080/job/${appName}/job/${repo}/createItem?name=PROD&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22PROD%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
		#curl -XPOST 'http://192.168.18.9:8080/job/${appName}/job/${repo}/job/SIT/createItem?name=ROLLBACK&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22ROLLBACK%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
            #curl -XPOST 'http://192.168.18.9:8080/job/${appName}/job/${repo}/job/UAT/createItem?name=ROLLBACK&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22ROLLBACK%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
		#curl -XPOST 'http://192.168.18.9:8080/job/${appName}/job/${repo}/job/PRE_PROD/createItem?name=ROLLBACK&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22ROLLBACK%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
		#curl -XPOST 'http://192.168.18.9:8080/job/${appName}/job/${repo}/job/PROD/createItem?name=ROLLBACK&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22ROLLBACK%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
            #curl -s -XPOST 'http://192.168.18.9:8080/job/${appName}/job/${repo}/job/SIT/createItem?name=build_and_deployment' --data-binary @sit_config.xml -H "Content-Type:text/xml" -u ${USER}:${PASSWORD}
      """
} 
}