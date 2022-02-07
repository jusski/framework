pipeline {
    agent any
		
    tools 
    {
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
                //Checkout project from git
                git 'https://github.com/jusski/framework.git'
                
                //Run surefire tests
                bat "mvn --batch-mode -Dsurefire.suiteXmlFiles=src/test/resources/testng-smoke.xml clean test"
            }

            post 
            {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the screenshots.
                always 
                {
                    junit '**/target/surefire-reports/junitreports/TEST-*.xml'
                    archiveArtifacts artifacts: 'target/screenshots/*.png', allowEmptyArchive: true, onlyIfSuccessful: true
                   
                }
            }
        }
    }
}

