package com.seh.utils;

package com.seh.utils;

def call(String repo,String appName) {
    def githubUrl = 'https://api.github.com/user/repos'
    def owner = 'minaxijoshi3101'
    def repoName = "${repo}"

    // Define repository configuration JSON payload
    def repoConfigJson = """
    {
    "name": "${repoName}",
    "private": false,
    "auto_init": true,
    "gitignore_template": "nanoc"
    }
    """
    withCredentials([string(credentialsId: 'GITHUB_TOKEN', variable: 'githubToken')]) {
        sh """
            // Execute cURL command to create the repository
            def command = "curl -X POST -H 'Authorization: token ${githubToken}' -d '${repoConfigJson}' ${githubUrl}"
            def process = command.execute()
            process.waitFor()
            // Check if the repository creation was successful
            if (process.exitValue() == 0) {
                println "Repository ${repoName} created successfully on GitHub."
            } else {
                println "Failed to create repository ${repoName}."
                println "Error: ${process.err.text}"
            }
        """
    }
    try{
    } catch (Exception e) {
        echo "Caught: ${e}"
        throw e
    }
}