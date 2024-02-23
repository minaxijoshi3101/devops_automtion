import java.net.*

def buildNumberForAppComponents() {
    //create credentials in jenkins for nexus
    //nexus url - http://192.168.18.8:8082/
    def credentialsId = 'SEHNexusArtifactory'
}
pipeline{
    agent {

    }
    environment{

    }
    stages{
        stage("download from nexus")
        {
            steps{
                script{

                }
            }
        }
    }
}