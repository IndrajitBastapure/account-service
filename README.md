**Account-Service API**
----------------------
*This is a Spring Boot-based Account Service that allows creating accounts for customers and automatically records an initial transaction if an initial credit is provided.
The service is backed by an in-memory H2 database and can be easily set up and tested locally.*

# Prerequisite - Environment Setup
Ensure the following prerequisites are installed and configured:

1. Java 17 or higher
2. Maven 3.8.x or higher
3. Git (for cloning the repository)
4. Postman (for API testing, collection is provided)

# Getting Started - Build & Run Application
Follow these steps to build and run the application:

1. Clone the Repository :
git clone https://github.com/IndrajitBastapure/account-service
2. Navigate to the project directory : cd account-service
3. Build the application using Maven :
mvn clean install
4. Run the account-service application using the Spring Boot command:
mvn spring-boot:run
5. Ensure that the application starts successfully on port 8090
6. You can access the in-memory H2 database console at : http://localhost:8090/h2-console/login.jsp
7. Use the following credentials to log in:
Parameter	Value
JDBC URL  =>   jdbc:h2:mem:assignmentdb
User Name =>   admin
Password  =>   admin
8. Before creating an account, you need a valid customerId.
   Execute the following query in the H2 console to retrieve existing customers:
SELECT * FROM customer;
9. Use POST request to create a new account for an existing customer.
endpoint POST http://localhost:8090/api/account-service/accounts/create
10. Sample Request Body :
{
"customerId": "b07afe37-1033-4f77-bf41-e5243aba13c2",
"initialCredit": 100.00
} 
11. Replace customerId with the actual value retrieved from the customer table.
12. Use GET request to fetch user account information. 
endpoint http://localhost:8090/api/account-service/accounts/user/8381cabb-89d0-4280-a18b-ebc2419c2512 
13. Replace customerId with the actual value retrieved from the customer table.
14. Postman Collection is provided to test the API endpoints easily. 
15. Access API Documentation Access the API specifications and documentation using Swagger at: http://localhost:8090/swagger-ui/index.html