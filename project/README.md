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
    
    
    
   
    
    
    
# Running the project
 
+ `mvn clean `

+ Run the example : `java -jar project.jar us-west-2`




# Run test class 


 


--------- 












