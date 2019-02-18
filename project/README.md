# Project description
 
Online shopping application developed with AWS 

 

# Feature 

+ Cart API with AWS API gateway and AWS Lambda function 

+ Web crawler to crawl data
    + URL 
    + Send request to queue 
    + The batch server pick up the request and update the record to IN_PROGRESS 

    
 



# Stack

 
+ Java - Spring framework

+ Apache Maven 

+ Reactive streams - Async queues 

+ AWS 
    + AWS Lambda functions  
    + AWS API gateway
    + Database : AWS DynamoDB
    + SQS endpoint 
    
     
+ Jenkins 

+ JMS  

+ Test : Wiremocks 

+ Jsoup


 


# Step  


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



# Run test class 


 




