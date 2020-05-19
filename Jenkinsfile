pipeline {
  stages {
    stage ("Checkout"){
        steps {
            checkout()
        }
    }
    stage ("Build") {
        steps {
            mavenBuild()
        }
    }
    stage ("Analysis") {
        steps {
            mavenSonarqube()
        }
    }
    stage ("Quality Gate") {
        steps {
            mavenQualityGates()
        }
    }
    stage ("Publish") {
        steps {
            mavenPublish()
        }
    }
    stage ("Docker") {
        steps {
            dockerBuildMicroservice()
        }
    }
    stage('Deployment') {
      parallel {
        stage('Staging') {
          when {
            branch 'staging'
          }
          steps {
            withAWS(region:'<your-bucket-region>',credentials:'<AWS-Staging-Jenkins-Credential-ID>') {
              s3Delete(bucket: '<bucket-name>', path:'**/*')
              s3Upload(bucket: '<bucket-name>', workingDir:'build', includePathPattern:'**/*');
            }
            mail(subject: 'Staging Build', body: 'New Deployment to Staging', to: 'jenkins-mailing-list@mail.com')
          }
        }
        stage('Production') {
          when {
            branch 'master'
          }
          steps {
            withAWS(region:'<your-bucket-region>',credentials:'<AWS-Production-Jenkins-Credential-ID>') {
              s3Delete(bucket: '<bucket-name>', path:'**/*')
              s3Upload(bucket: '<bucket-name>', workingDir:'build', includePathPattern:'**/*');
            }
            mail(subject: 'Production Build', body: 'New Deployment to Production', to: 'jenkins-mailing-list@mail.com')
          }
        }
      }
    }
}