# FetchRewardCodingExercise
Hello, welcome to the FetchRewards coding exercise.

To run this project, you will need:

Maven 3.6.3 ==> https://maven.apache.org/download.cgi

Java 8 ==> https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html

Don't forget to make sure the paths are configured.

Download and unzip the project. Go to the project folder and run the following command: 
>mvn clean install

The app will download all the required dependencies. In case of errors, make sure your firewall and certificates are not blocking the download. 

Then to start the app, use the following command:
>mvn tomcat7:run

The last lines in your console should look like this:

![screenshot.PNG](https://github.com/TheoRiv/FetchRewardCodingExercise/blob/main/screenshot.PNG)

The application webservice is now running and working. You will find in the project the following postman collection:
>FetchRewards.postman_collection

This collection contains 3 calls: 

GetBalancePoints: return the list of payers and their point balance (GET: http://localhost:8090/fetchrewards/getbalancepoints)

AddTransaction: add a transaction to an existing payer. If the payer doesn't have enough points, or don't exist, return null 
(POST: http://localhost:8090/fetchrewards/addtransaction)

SpendPoints: spend points using the payers' balance. If the payers don't have enough points, return empty list. 
(POST: http://localhost:8090/fetchrewards/spendpoints)
