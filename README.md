# Project-2-Group-4
This is Group 4's project 2 repository.  Members are Mason, Jacob, Garrison, and Anthony

## Theme
### <Theme Goes Here>
####  <Theme Description Goes Here>

## Usage
> mvn clean packge
### to run sub modules:
> mvn exec:java -pl [module artifactId] -Dexec.mainClass=[module mainclass]

## Data Pipeline

### Java jar
#### Pulls data from csv/json on S3
#### Defines Spark SQL Actions on S3 data
#### Submits spark job to a cluster

### Apache Spark Cluster
#### Amazon EMR 
#### stores results on S3 as logs from submitted spark jobs

### Java jar 2
#### Reads query results from S3 logs
#### Stores data on SQL RDBMS hosted on AWS EC2 with Docker


