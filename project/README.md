# Project description
 
Online shopping application developed with AWS 

 

# Feature 

+ Cart API with AWS API gateway and AWS Lambda function 
+ Web crawler to crawl data
    + URL 
    + Send request to queue 
    + The batch server pick up the request and update the record to IN_PROGRESS 
+ Bot feature - shopping bot 
    + AWS lambda function for Amazon Lex Shopping bot  
+ PDF service 
+ Streaming service 
    + Audio stream service
+ Service to detect object and then index in AWS ElasticSearch database 
+ AWS Router 53 - hosted zone DNS record 
+ Allow Read / Write from local to remote instances
+ Detect online shop labels with AWS Rekognition 
+ ETL processing on the cloud 
+ Product photo upload AWS S3 service 
+ Chat bot provided for customers to ask about shopping product with AWS Lex   



# Stack

 
+ Java - Spring framework

+ Apache Maven 

+ Reactive streams - Async queues 

+ AWS 
    + AWS Lambda functions  
    + AWS API gateway
    + Database : AWS DynamoDB
    + SQS endpoint 
    + Amazon Elastic Beanstalk
       
   
+ Jenkins 

+ JMS  

+ Test : Wiremocks 

+ Jsoup




# Step  
+ AWS
    + Set up AWS credentials 
    + Create AWS VPC 
    + Create RDS instance 
    + Create AWS ElastiCache instance 
    + Build application `mvn package`
    + Deploy the application in Amazon Elastic Beanstalk 
    + AWS Lambda functions 
    + AWS CLI 
    + MySQL RDS database 
    + Serverless framework 
    + Spring Cloud AWS 
    
    
   


+ Create table on DynamoDB
    + Name : 'cart'
    + Partition key ; 'loginId'(String)
    + Sort key : 
    + Read capacity units 
    + Write capacity units 
    + Provision :  
    
    
+ AWS Lambda function
    +  
    
    
+ AWS Kinesis stream 
    + Subscribe to AWS Kinesis stream
    + Devices send data to cloud  
    + Subscribe Kinesis stream to ARTIK cloud via API calls 
    + Read data from Kinesis Stream using AWS SDK  


+ AWS Healthcheck 
+ AWS Rekognition


+ Build & deploy - Docker image built for Java project on AWS ECS with Jenkins pipeline  


+ AWS SES sender email 

+ AWS Cloudsearch client for uploading documents and querying your search domains written in Java


 
+ Generic e-mailer using Amazon SES SMTP Java Interface

+ AWS data pipeline task runner



+ AWS ElasticSearch 
+ AWS Rekcognition




  build the image docker build -t build-deploy:latest -f Dockerfile .
  
  run the container docker run -it docker run -e "ECS_CLUSTER=cluster-test" -e "ACCESS_KEY=my_access_key" -e "SECRET_KEY=my_secret_key" -e "REGIAN=eu-west-1" -p 8080:8080 -p 5000:5000 -v jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock build-deploy:latest
  
  Test your commands inside the container docker exec -it CONTAINER_ID bash
  
  Configure your job on Jenkins localhost:8080
  
  NOTE
  This is a part of another open source project that I'm working on. A bootstrap project to work with Microservices using Spring cloud. here
  
  
 
  Tips
  Install "Select suggested plugin" in order to build a quick continuous deploy
  
  To run the job by HTTP call curl user:PASSWORD@localhost:8080/job/job-name/build?token=your-token
  
  To get the build number sh "echo ${BUILD_NUMBER}"
  
  Jenkins login - admin - admin
  
  
+ Deploy the web application : 

     
    
    
    
+ AWS EC2 instance -> deploy the application 




+ Deploy application commands 
     
    `aws cloudformation package --template-file sam.yaml --output-template-file output-sam.yaml --s3-bucket lambda-jar-upload`
    
    `aws cloudformation deploy --template-file output-sam.yaml --stack-name spring-petclinic --capabilities CAPABILITY_IAM` 
    
    `aws cloudformation describe-stacks --stack-name spring-petclinic | jq '.Stacks[0].Outputs[0].OutputValue'`
    
    
    











 

    
    
# Running the project
 
+ `mvn clean `

+ Run the example : `java -jar project.jar us-west-2`




# Run test class 

+ Run test class 





# A build deploy docker image to work with Java application and AWS



# Docker image build

`docker build -t devbhuwan/order-manager-image -f infrastructure/Dockerfile .`






https://github.com/waleedarafa/Ecommerce-Shopping
https://github.com/BhuwanUpadhyay/order-manager
https://github.com/1904labs/AWS-ElasticSearch-Spring-Boot-Starter-Project
https://github.com/varun1524/SurveyApe
https://github.com/SainathDutkar/StockWatch
https://github.com/iftekharkhan09/AWS/tree/master/src/main/java/com/amazonaws/ses/samples




Big project : 
https://github.com/Sandyarathi/eshop-microservice
https://github.com/petergu/aws-sqs-lambda-java

https://github.com/cganoo/codecamptweetsearcher
https://github.com/smoketurner/uploader



-----------------


