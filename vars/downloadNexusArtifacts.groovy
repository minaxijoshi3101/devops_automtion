import java.net.*

def buildNumberForAppComponents() {
    //create credentials in jenkins for nexus
    //nexus url - http://192.168.18.8:8082/
    def credentialsId = 'SEHNexusArtifactory'
}
def nexusUrl = 'http://your-nexus-url'
def nexusCredentialId = 'your-credential-id'

pipeline{
    agent {

    }
    environment{
    }
    stages{
        stage("download from nexus using API")
        {
            steps{
                script{
                    /* // Retrieve Nexus credentials using the credential ID
                    def credentials = credentials(nexusCredentialId)

                    // Initialize Nexus server
                    def server = nexus.server(nexusUrl)
                    server.credentials(credentials.username, credentials.password)

                    // Define the files pattern
                    def filesPattern = 'path/to/files/*.jar' // Adjust the pattern as per your file structure

                    // Upload files using the files pattern
                    server.upload(filesPattern) */
                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: '192.168.18.8:8082',
                        groupId: 'com.seh',
                        version: version,
                        repository: 'my-repo',
                        credentialsId: 'nexus-credentials',
                        artifacts: [
                            [artifactId: projectName,
                            classifier: '',
                            file: 'my-service-' + version + '.jar',
                            type: 'jar']
                        ]
                    )

                }
            }
        }
    }
}