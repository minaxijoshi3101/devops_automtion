import com.seh.utils.checkOutSCM
@Library("devops_automation")_

 pipeline {
    options{
         skipDefaultCheckout()
         buildDiscarder(logRotator(numToKeepStr: 20))
    }
    environment{
        BUILDSERVER_WORKSPACE = "${WORKSPACE}"
    }
    agent {
        label: 'lxsehin003'
    }
    stages()
    {
        stage('Build Provisioning') {
            new checkOutSCM().call()
        }
    }
 }