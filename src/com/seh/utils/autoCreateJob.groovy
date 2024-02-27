package com.seh.utils;

def call(String repo,String appName) {
      sh "mkdir -p  ${repo}"
      def fileWriteSIT = libraryResource "jenkins_job_SIT.xml"
      writeFile file: "${repo}/sit_config.xml", text: fileWriteSIT
      def fileWriteSITRollback = libraryResource "jenkins_rollback_job_SIT.xml"
      writeFile file: "${repo}/sit_rollback_config.xml", text: fileWriteSITRollback
      def fileWriteSITRetrofit = libraryResource "jenkins_retrofit_job_SIT.xml"
      writeFile file: "${repo}/retrofit_sit_config.xml", text: fileWriteSITRetrofit
      def fileWriteUAT = libraryResource "jenkins_job_UAT.xml"
      writeFile file: "${repo}/uat_config.xml", text: fileWriteUAT
      def fileWriteUATRollback = libraryResource "jenkins_rollback_job_UAT.xml"
      writeFile file: "${repo}/uat_rollback_config.xml", text: fileWriteUATRollback
      def fileWriteUATRetrofit = libraryResource "jenkins_retrofit_job_UAT.xml"
      writeFile file: "${repo}/retrofit_uat_config.xml", text: fileWriteUATRetrofit
      def fileWritePRE_PROD = libraryResource "jenkins_job_PRE_PROD.xml"
      writeFile file: "${repo}/pre_prod_config.xml", text: fileWritePRE_PROD
      def fileWritePRE_PRODRollback = libraryResource "jenkins_rollback_job_PRE_PROD.xml"
      writeFile file: "${repo}/pre_prod_rollback_config.xml", text: fileWritePRE_PRODRollback
      def fileWritePROD = libraryResource "jenkins_job_PROD.xml"
      writeFile file: "${repo}/prod_config.xml", text: fileWritePROD
      def fileWritePRODRollback = libraryResource "jenkins_rollback_job_PROD.xml"
      writeFile file: "${repo}/prod_rollback_config.xml", text: fileWritePRODRollback
      def fileWriteCoverityScan = libraryResource "coverity_scan.xml"
      writeFile file: "${repo}/coverity_scan.xml", text: fileWriteCoverityScan
      withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'svc_devops_jenkins_token',
  usernameVariable: 'USER', passwordVariable: 'PASSWORD']
]) {
      sh """  
            cd ${repo}
            curl -XPOST 'http://10.13.12.106:8080/createItem?name=${appName}&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22${appName}%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
            curl -s -XPOST 'http://10.13.12.106:8080/job/${appName}/createItem?name=coverity_scan' --data-binary @coverity_scan.xml -H "Content-Type:text/xml" -u ${USER}:${PASSWORD}
            curl -XPOST 'http://10.13.12.106:8080/job/${appName}/createItem?name=${repo}&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22${repo}%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
            curl -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/createItem?name=SIT&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22SIT%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
		curl -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/createItem?name=UAT&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22UAT%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
            curl -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/createItem?name=PRE_PROD&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22PRE_PROD%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
		curl -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/createItem?name=PROD&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22PROD%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
		curl -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/job/SIT/createItem?name=ROLLBACK&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22ROLLBACK%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
            curl -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/job/UAT/createItem?name=ROLLBACK&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22ROLLBACK%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
		curl -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/job/PRE_PROD/createItem?name=ROLLBACK&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22ROLLBACK%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
		curl -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/job/PROD/createItem?name=ROLLBACK&mode=com.cloudbees.hudson.plugins.folder.Folder&from=&json=%7B%22name%22%3A%22ROLLBACK%22%2C%22mode%22%3A%22com.cloudbees.hudson.plugins.folder.Folder%22%2C%22from%22%3A%22%22%2C%22Submit%22%3A%22OK%22%7D&Submit=OK' --user ${USER}:${PASSWORD} -H "Content-Type:application/x-www-form-urlencoded"
            curl -s -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/job/SIT/createItem?name=build_and_deployment' --data-binary @sit_config.xml -H "Content-Type:text/xml" -u ${USER}:${PASSWORD}
		curl -s -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/job/UAT/createItem?name=build_and_deployment' --data-binary @uat_config.xml -H "Content-Type:text/xml" -u ${USER}:${PASSWORD}
		curl -s -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/job/SIT/createItem?name=retrofit_deployment' --data-binary @retrofit_sit_config.xml -H "Content-Type:text/xml" -u ${USER}:${PASSWORD}
		curl -s -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/job/UAT/createItem?name=retrofit_deployment' --data-binary @retrofit_uat_config.xml -H "Content-Type:text/xml" -u ${USER}:${PASSWORD}
		curl -s -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/job/PRE_PROD/createItem?name=release_deployment' --data-binary @pre_prod_config.xml -H "Content-Type:text/xml" -u ${USER}:${PASSWORD}
		curl -s -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/job/PROD/createItem?name=release_deployment' --data-binary @prod_config.xml -H "Content-Type:text/xml" -u ${USER}:${PASSWORD}
		curl -s -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/job/SIT/job/ROLLBACK/createItem?name=rollback_deployment' --data-binary @sit_rollback_config.xml -H "Content-Type:text/xml" -u ${USER}:${PASSWORD}
		curl -s -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/job/UAT/job/ROLLBACK/createItem?name=rollback_deployment' --data-binary @uat_rollback_config.xml -H "Content-Type:text/xml" -u ${USER}:${PASSWORD}
		curl -s -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/job/PRE_PROD/job/ROLLBACK/createItem?name=rollback_deployment' --data-binary @pre_prod_rollback_config.xml -H "Content-Type:text/xml" -u ${USER}:${PASSWORD}
		curl -s -XPOST 'http://10.13.12.106:8080/job/${appName}/job/${repo}/job/PROD/job/ROLLBACK/createItem?name=rollback_deployment' --data-binary @prod_rollback_config.xml -H "Content-Type:text/xml" -u ${USER}:${PASSWORD}
      
      """
} 
}