/*
*The provided Groovy script is meant to retrieve environment variables from Jenkins. 
import jenkins.model.Jenkins: This line imports the jenkins.model.Jenkins class, which provides access to the Jenkins instance.

def call(String name) { ... }: This defines a method named call that takes a single parameter name of type String. In Groovy, methods can be defined using the def keyword without specifying a return type.

Jenkins.instance: This accesses the singleton instance of the Jenkins class, allowing you to interact with the Jenkins server.

getGlobalNodeProperties()[0]: This retrieves the global node properties. Since getGlobalNodeProperties() returns a list, [0] accesses the first element of that list.

getEnvVars(): This retrieves the environment variables associated with the node.

[name] ?: '': This attempts to retrieve the value of the environment variable specified by the name parameter. If the variable does not exist or is null, it returns an empty string ('').

So, overall, this script is a Groovy function that, given the name of an environment variable, retrieves its value from the Jenkins environment associated with the global node properties.
*/
import jenkins.model.Jenkins

def call(String name) {
    return Jenkins.instance.getGlobalNodeProperties()[0].getEnvVars()[name] ?: ''
}