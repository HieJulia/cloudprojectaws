FROM openjdk:7
MAINTAINER Hien Nguyen <hienminhnguyen711@gmail.com>
# COPY . /usr/src/taskrunner

WORKDIR /usr/src/taskrunner


# Run application on aws data pipeline - Task Runner 
RUN curl -O http://s3.amazonaws.com/datapipeline-us-east-1/us-east-1/software/latest/TaskRunner/TaskRunner-1.0.jar 

RUN curl -O http://s3.amazonaws.com/datapipeline-prod-us-east-1/software/latest/TaskRunner/mysql-connector-java-bin.jar

CMD ["/usr/src/taskrunner/run.sh"]


VOLUME /usr/src/taskrunner



