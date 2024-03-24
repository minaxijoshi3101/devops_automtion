package com.seh.utils;

def call(String repo,String appName) {
    def githubUrl = 'https://api.github.com/user/repos'
    def owner = 'minaxijoshi3101'
    def repoName = "${repo}"
    def command
    def process
    def repoConfigJson = """
    {
    "name": "${repoName}",
    "description":"This is your first repo!",
    "homepage":"https://github.com",
    "private": false,
    "is_template":true
    }
    """
    // Define repository configuration JSON payload
    //def repoConfigJson 
    withCredentials([string(credentialsId: 'GITHUB_TOKEN', variable: 'GITHUB_TOKEN')]) {
        sh """
            #Execute cURL command to create the repository
            command="curl -L -X POST -H Accept: application/vnd.github+json -H Authorization: Bearer ${GITHUB_TOKEN} -H "X-GitHub-Api-Version: 2022-11-28" -d ${repoConfigJson} ${githubUrl}"
            echo "Executing: \${command}"
            \${command}
        """
        /*process.waitFor()
         // Check if the repository creation was successful
        if (process.exitValue() == 0) {
            println "Repository ${repoName} created successfully on GitHub."
        } else {
            println "Failed to create repository ${repoName}."
            println "Error: ${process.err.text}"
        } */
    }
    try{
    } catch (Exception e) {
        echo "Caught: ${e}"
        throw e
    }
}