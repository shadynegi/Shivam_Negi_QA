package chatbot;

public class Guide {
	
//	* git: https://shivamnegieminence@bitbucket.org/appweb123/testscripts.git
	
//	* Install JAVA v21 to make script compatible with jenkins
	
//  * install maven integration, testng results, email extension plugins in jenkins

//	* updated code for jenkins pipeline*	
//	pipeline {
//	    agent any
//	    
//	    tools {
//	        // Define Maven tool (configure in Jenkins Global Tool Configuration)
//	        maven 'Maven-3.12.1' 				// Replace with your Maven version name in Jenkins
//	        jdk 'JDK-21'						// Replace with your JDK version name in Jenkins
//	    }
//	    
//	    environment {
//	        // Define environment variables
//	        CHROME_DRIVER_PATH = '/usr/local/bin/chromedriver' 		// Adjust path as needed
//	        DISPLAY = ':99' 										// For headless Chrome in Linux environments
//	    }
//	    
//	    stages {
//	        stage('Checkout') {
//	            steps {
//	                // Checkout code from your repository
//	                checkout scm
//	                // Or use specific git checkout:
//	                // git branch: 'main', url: 'https://shivamnegieminence@bitbucket.org/appweb123/testscripts.git' 		// i am currently using my own bitbucket account, change accordingly	
//	            }
//	        }
//	        
//	        stage('Setup Environment') {
//	            steps {
//	                script {
//	                    // Install Chrome and ChromeDriver if not already installed
//	                    sh '''
//	                        # Check if Chrome is installed
//	                        if ! command -v google-chrome &> /dev/null; then
//	                            echo "Installing Chrome..."
//	                            wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add -
//	                            echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list
//	                            apt-get update
//	                            apt-get install -y google-chrome-stable
//	                        fi
//	                        
//	                        # Check Chrome version
//	                        google-chrome --version
//	                        
//	                        # Start Xvfb for headless display
//	                        Xvfb :99 -screen 0 1024x768x24 > /dev/null 2>&1 &
//	                    '''
//	                }
//	            }
//	        }
//	        
//	        stage('Build') {
//	            steps {
//	                // Clean and compile the project
//	                sh 'mvn clean compile'
//	            }
//	        }
//	        
//	        stage('Run Tests') {
//	            steps {
//	                script {
//	                    try {
//	                        // Run TestNG suite
//	                        sh 'mvn test -DsuiteXmlFile=testng.xml'
//	                    } catch (Exception e) {
//	                        // Mark build as unstable if tests fail, but continue pipeline
//	                        currentBuild.result = 'UNSTABLE'
//	                        echo "Tests failed, but continuing pipeline for report generation"
//	                    }
//	                }
//	            }
//	        }
//	        
//	        stage('Generate Reports') {
//	            steps {
//	                script {
//	                    // Archive test results
//	                    publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'
//	                    
//	                    // Archive HTML reports if they exist
//	                    archiveArtifacts artifacts: '**/TestReport_*.html', allowEmptyArchive: true
//	                    
//	                    // Archive screenshots if they exist
//	                    archiveArtifacts artifacts: '**/screenshots/*.png', allowEmptyArchive: true
//	                }
//	            }
//	        }
//	    }
//	    
//	    post {
//	        always {
//	            // Clean workspace
//	            cleanWs()
//	        }
//	        
//	        success {
//	            echo 'Pipeline completed successfully!'
//	            // Send email notification on success
//	            emailext (
//	                subject: "Test Suite Passed - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
//	                body: "The automated test suite has been completed successfully. Check the reports for details.",
//	                to: "bharatkumar.eminence@gmail.com, shivamnegi.eminence@gmail.com"
//	            )
//	        }
//	        
//	        failure {
//	            echo 'Pipeline failed!'
//	            // Send email notification on failure
//	            emailext (
//	                subject: "Test Suite Failed - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
//	                body: "The automated test suite has failed. Check the console output and reports for details.",
//	                to: "bharatkumar.eminence@gmail.com, shivamnegi.eminence@gmail.com"
//	            )
//	        }
//	        
//	        unstable {
//	            echo 'Tests failed but pipeline completed!'
//	            // Send email notification for unstable build
//	            emailext (
//	                subject: "Test Suite completed but Unstable - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
//	                body: "The automated test suite has been completed but some tests failed. Check the test reports for details.",
//	                to: "bharatkumar.eminence@gmail.com, shivamnegi.eminence@gmail.com, shadynegi9@gmail.com"
//	            )
//	        }
//	    }
//	}	  
}
