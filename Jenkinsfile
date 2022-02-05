pipeline {
  agent any
  stages {
    stage('print stage') {
      parallel {
        stage('print stage') {
          steps {
            echo 'hi mi'
          }
        }

        stage('alternative print') {
          steps {
            echo 'opa'
            echo 'bu'
          }
        }

      }
    }

    stage('some error stage') {
      steps {
        timeout(time: 25) {
          echo 'bu'
          node(label: 'windows')
          error 'hi'
        }

      }
    }

  }
}