# DEV OPS WITH AWS 

## The cloud and the Dev Ops revolution 
+ Cloud and not infrastructure  
+ Adopt DevOps culture 
+ Deploy AWS 
    + Deploy hardware versus in the cloud 
    + Builds a day are pushed to the production 
    + Roll back production env 
    + Code coverage 
    + Alerts 
    + Application performance 
    + Mean Time to Resolution 
+ DevOps with AWS 
    + SSH 
    + Config service 
    + SDKs, API 
    + EC2 - service to create virtual servers, scale infrastructure - Auto Scaling Group - Scale pools on EC2 instances 
    + Container : Docker 
    + Serverless function Lambda
    + Services : S3, object store - Code Build 
    + CodeDeploy : deploy to EC2 instances 
    + CodePipeline : orchestrate how code is built, tested, deployed across envs 
    + Monitor - CloudWatch 
    + ElasticSearch / Kibana - collect, index , visualize metrics, logs 
    + Stream data to services, AWS Kinesis 
    + SNS alert, service 
## Deploy web application 
    + Create and configure account 
        + Enable multi-factor auth on the root account 
        + Create user in IAM 
        + Install and configure CLI 
        + Configure CLI 
    + Spin up web server
        + Launch EC2 instance 
        + AMI ID 
            + Amazon Linux AMI 
        + Instance type 
            + Virtual hardware to use for the virtual server 
        + Security group 
        + SSH key - pair 
