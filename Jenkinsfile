pipeline {
    agent any
		
    tools 
    {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven-3.8.4"
        jdk "jdk1.8.0"
    }

    parameters
    {
        choice choices: ['dev', 'qa'], name: 'envinronment'
    }
    
    stages 
    {
        stage('Build') 
        {
            steps 
            {
                // Get some code from a GitHub repository
                git 'https://github.com/jusski/framework.git'

                // Run Maven on a Unix agent.
                bat "mvn -Dsurefire.suiteXmlFiles=src/test/resources/testng-smoke.xml clean test"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post 
            {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                always 
                {
                    junit '**/target/surefire-reports/junitreports/TEST-*.xml'
                    archiveArtifacts allowEmptyArchive: true, 'target/*.jar'
                }
            }
        }
    }
}
