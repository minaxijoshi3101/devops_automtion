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
}