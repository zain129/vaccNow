# Project: VaccNow
###### *An application for scheduling and record keeping for Covid-19 vaccination. Email is sent once a vaccination a scheduled and a vaccination certificate is generated when payment is made.*

## Table of Content
- [Technologies](#technologies)
- [ER Diagram](#er-diagram)
- [Running the Application](#running-the-application)
- [Sample APIs](#sample-apis)

## Technologies
* Java 8
* Springboot 2.4.1
* PosgtreSQL Database
* iTextPdf for PDF generation

## ER Diagram

![ERDiagram](https://github.com/zain129/vaccNow/blob/master/erd.png)

## Running the application
- Import the project as maven project or clone from github.
- Go to project root folder and open command prompt (cmd).
- Run any of the following commands to run the application:
    - **mvn clean install spring-boot:run**
    - **java -jar target/vaccnow-1.0-SNAPSHOT.jar**
- Application will be running on port 8081.
- Sample APIs are provided at the end. 
- H2 console link - http://127.0.0.1:8081/api/v1/h2-console/

### REST APIs

* [API Documentation](https://documenter.getpostman.com/view/15062221/TzCFgqZY)


#### Notes
 * Email is sent when vaccination is scheduled.
    - For the time being, the email is printed on console.
 * Vaccination Certificate is generated when payment is made (Payment method API).
    - Please check the project root directory for the PDF document.
