# AWS Security 

## Host multi-tier web application 
+ Control restriction with VPC 
+ Public and private subnets i VPC 


+ Disaster recovery 
    + Backup data to DR site 
    + Use VPC to host EC2 instances with EBS volume & store data in S3 buckets 
    + Scale EC2 
+ Extended corporate network in AWS Cloud '
    + Host VCP behind firewall of networ 
    + Move resources to the cloud 
    + Create VPC - VPC with Private Subnet Only and Hardware VPN access 
    + Connect VPC to data center using VPN / AWS connect service 
+ Host web apps in AWS that connected to data center 
    + Create IPsec hardware VPN connection betwee VPC and network 
    + Host web apps on AWS in VPC and sync data with databases in data center through VPN tunnel 
    + Create VPC with public and private subnets and hardware VPN access
+ Host multi tier web app 
    + 1 public subnet contains web server and app server 
    + 2 instances need to have inbound and outbound access for internet traffic 
    + Public subnet also has one NAT instance to route traffic for database instance in the private subnet 
    + Private subnet hold instances to communicate with instances in public subnet 
    + ACLs = firewall for subnet with security group access 

    

## Create VPC 