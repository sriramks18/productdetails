# Retail

## Introduction
This is my try with myRetail application that has two APIs
  1. Get Product with Pricing
  2. Update a Product pricing

## Technical Details
  1. Ratpack
  2. Guice
  3. Cassandra
  4. Spock
  
## To Run the application
  1. Download the code
  2. [Download](https://portal.datastax.com/downloads.php?dsedownload=tar/enterprise/dse-5.1.tar.gz) Datastax Server Enterprise 
  3. Unzip the file to a local folder
  4. In Command line go to the root folder where you unzipped dse/bin and run sudo ./dse cassandra -R
  5. Open another terminal navigate to code root and then run ./gradlew run
  
  The application should start
  
  ## Source files
  1. The application apart from the soruce files has Integration and service specs written in Spock
