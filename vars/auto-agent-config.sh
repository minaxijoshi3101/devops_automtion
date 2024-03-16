#!/bin/bash
#############################################################################
########################Author: Minaxi Joshi#################################
########################This is to configure jenkins agent###################
#############################################################################
lanid='minaxi:minaxi'
agent_ws_path=$1
agent_secret=$2
agent_name=$3
jenkins_url="http://192.168.18.10:8080/"

echo "PLEASE CONFIRM AS BELOW INFO"
echo "================================================="
echo "Agent Name: $agent_name"
echo "Agent Secret String: $agent_secret"
echo "Agent workspace location: $agent_ws_path"
echo "User lanid (for download packages): $lanid"
echo "================================================="

mkdir -p "$agent_ws_path"
cd $agent_ws_path
rm -rf jdk-21_linux-x64_bin 
rm -f secret-file
rm -f agent.jar
# input secret file content
echo $agent_secret > secret-file
# download jenkins agent
curl -skO "jenkins_url/jnlpJars/agent.jar"
#download java jdk from nexus repo
curl -kO -u $lanid http://192.168.18.6:8082/repository/my-repo/jdk-21_linux-x64_bin.tar.gz
tar -xvf jdk-21_linux-x64_bin.tar.gz
rm -f jdk-21_linux-x64_bin.tar.gz
#to import cerst on java
#cat > jdk-21.0.1/lib/security/yourcert.cer << EOL
#-----BEGIN CERTIFICATE------
#-----END CERTIFICATE--------
#EOL
#$agent_ws_path/jdk-21.0.1/bin/keytool -import -alias intermediate -noprompt -keystore jdk-21.0.1/lib/security/cacets -storepass changeit -file jdk-11.0.16.1/lib/security/seh.cer

cat > agent-start.sh << EOL
nohup $agent_ws_path/jdk-21.0.1/bin/java -jar agent.jar -jnlpUrl $jenkins_url/computer/$agent_name/jenkins-agent.jnlp -secret @secret-file -workDir "$agent_ws_path"
EOL

chmod +x agent-start.sh

sh agent-start.sh

to run 
./auto-agent-config.sh '/prodlib/SEH/jenkins' d11b9fe5f40395fb1461027e2bc98d2a5fe59e55df7b31441c5a1dac1512d318 lxsehin003