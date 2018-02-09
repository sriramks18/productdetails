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
  5. Gradle
  6. Groovy
  
## Setup

  1. [Download](https://portal.datastax.com/downloads.php?dsedownload=tar/enterprise/dse-5.1.tar.gz) Datastax Server Enterprise 
  2. Unzip the file to a local folder
  3. In Command line go to the root folder where you unzipped dse/bin and run 
        
        `$sudo ./dse cassandra -R`
  4. Code
             In Terminal Window
             
             `$ cd <workspace>`  
             `$ git clone https://github.com/sriramks85/productdetails.git`  
             `$ cd <workspace>/productdetails`
             `$ ./gradlew run`
  
  The application should start
  
  ## Source files
  
  1. The application apart from the soruce files has Integration and service specs written in Spock

The goal for this exercise is to create an end-to-end Proof-of-Concept for a products API which will aggregate product data from multiple sources and return it as JSON to the caller.  


Scenarios
=============
Use Postman

Scenario GET request
-----------------------
URL: http://localhost:5050/product/13860428 
method: GET  
JSON Response returned  
`{  
	"id": 13860428  
	"name": "The Big Lebowski [Blu-ray]"  
	"current_price": {  
		"id": 13860428  
		"value": 13.49  
		"currency_code": "USD"  
	}  
}`  

Scenario POST request
------------------------
URL: http://localhost:5050/product/13860428  
Methode: PUT  
Content-Type: application/json  
Set raw payload to:  
`{  
	"value": "99.99",  
	"currency_code": "USD"  
}`  
Click button: SEND  
It will return the updated Price with Product Details  


Scenario GET Request on unknown product
------------------------------------------
URL: http://localhost:5050/product/1380001  
METHOD: GET  

JSON Response returned  
`{
 "errorMessage": "My Retail Error for product id=1380001 while getting Product Data "
 }`  

Scenario GET Request on product with no pricing
--------------------------------------------------
URL: http://localhost:5050/product/52782530 
METHOD: GET  
Click button: SEND  
JSON Response returned  
`{
     "errorMessage": "My Retail Error for product id=52782530 while getting Price Data"
 }`  

