# Customer Complaints Management System

<h2>Intuit Home Assignment</h2>

An application that creates and retrieves complaints.

## Technologies

- Java 21
- Spring Boot [web, cloud, webflux, aop, jpa]
- Maven
- Lombok
- Mockito
- Junit
- MySQL
- Eureka
- Api-Gateway

## Features

Users can create and retrieve complaints

### Prerequisites

* Java 21</br>
* MySQL Server</br>
* MySQL Workbench</br>

## Getting Started

in application.yaml, make sure to configure my sql</br>
```
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/complaint-service?createDatabaseIfNotExist=true
    username: {your_username}
    password: {your_password}
```


<h4> run Eureka server,
Api-gateway Server and then Complaint service,
wait about 30 sec for service to be registered at Eureka,
then start invoking endpoints.</h4>

## API
1. <b>createComplaint</b></br>
    - Post: http://localhost:8080/api/complaint
    - Body:
     ```
          {
          "userId": "a93adc57-4d59-4a9d-85c6-b5d48d99101d",
          "complaint": "The seller never sent my item!",
          "subject": "I made a purchase and the item hasn't shipped. It's been over a week. Please help!",
          "purchaseId": "f256c996-6dcb-40cf-8dce-a11fa9bcab6b"
          }
    ```
    - Response - Status 201(Created):
   ```
       {
          "complaintId": "577e7ad5-64a1-43de-8fe5-3bc0c1f67339",
          "userId": "a93adc57-4d59-4a9d-85c6-b5d48d99101d",
          "purchaseId": "f256c996-6dcb-40cf-8dce-a11fa9bcab6b",
          "subject": "I made a purchase and the item hasn't shipped. It's been over a week. Please help!",
          "content": "The seller never sent my item!"
          }
   ```
2. <b>getComplaint</b></br>
    - Get: http://localhost:8080/api/complaint/577e7ad5-64a1-43de-8fe5-3bc0c1f67339 </br>
   You might have to change userId path param according to what DB have generated.
    - Response - Status 200(OK):
   ```
   {
    "complaintId": "577e7ad5-64a1-43de-8fe5-3bc0c1f67339",
    "subject": "I made a purchase and the item hasn't shipped. It's been over a week. Please help!",
    "content": "The seller never sent my item!",
    "purchaseResponse": {
        "id": "f256c996-6dcb-40cf-8dce-a11fa9bcab6b",
        "userId": "a93adc57-4d59-4a9d-85c6-b5d48d99101d",
        "productId": "4ac9da0b-66eb-415c-9153-a59ec0b3c3fe",
        "productName": "Book",
        "pricePaidAmount": 13.2,
        "priceCurrency": "USD",
        "discountPercent": 0.1,
        "merchantId": "71e234d2-dc65-41ff-bada-9d08d82fc6d1"
    },
    "userManagementResponse": {
        "id": "a93adc57-4d59-4a9d-85c6-b5d48d99101d",
        "fullName": "John Doe",
        "emailAddress": "johndoe@test.com",
        "physicalAddress": "Test Lane 1 Los Angeles"
    }
}

## Next Steps:
- Implementing security 
- Transition from using RestTemplate to the more modern and reactive approach of WebClient for improved performance and flexibility.
- Routing Purchase and User Management HTTP Calls through API Gateway
- Explore Adding Sleuth and Zipkin for better logging and tracing
- Consider Adding a Queue for asynchronous communication between components