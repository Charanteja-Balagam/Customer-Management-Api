Customer Management Api

This Spring Boot application provides a RESTful API to manage customer data. It supports basic CRUD operations and dynamically calculates a customer's tier (Silver, Gold, or Platinum) based on their annual spend and last purchase date.
The application is designed following clean architecture principles, incorporates input validation, and is documented with OpenAPI.

**Build And Run:**
   ->git clone https://github.com/your-username/customer-management-service.git
   ->cd customer-management-service
   -> mvn clean install
   ->mvn spring-boot:run (Application will be deployed on Local host : 8080 , make sure there are no other applications running at that port or change the server.port in the application.properties file)

**API Endpoints**

1.POST	/customers -	Create a new customer
2.GET	/customers/{id} -	Get customer by ID
3.GET	/customers/email?email={email} -	Get by email
4.GET	/customers/name?name={name} -	Get by name
5.PUT	/customers/{id}	- Update existing customer
6.DELETE	/customers/{id} -	Delete customer

**Sample Requests:**

1. POST /customers
  {
    "name": "David Doe",
    "email": "David.doe@example.com",
    "annualSpend": 12000,
    "lastPurchaseDate": "2024-12-01"
  }

2. GET /customers/fd3a8a2e-71f4-455e-a213-35b9eab519ad
3. GET /customers/name?value=david doe
4. GET /customers/email?value=alice@example.com
5. PUT /customers/fd3a8a2e-71f4-455e-a213-35b9eab519ad
  Content-Type: application/json

  {
    "name": "David d",
    "email": "alice.j@example.com",
    "annualSpend": 6000,
    "lastPurchaseDate": "2025-01-01T00:00:00"
  }
6. DELETE /customers/fd3a8a2e-71f4-455e-a213-35b9eab519ad



**Accessing H2 Console:**
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa  (you can setup in your application properties file)
Password: -

**Swagger UI:**
URL: http://localhost:8080/swagger-ui.html

**Assumptions:**
Case-Insensitive Name Search:
I Assumed that the findByName functionality should support case-insensitive matching to improve usability and reduce potential mismatches due to capitalization differences in user input.
Used custom query to support case-insensitivity.

Tier Calculation Logic:
Based the tier logic on both annualSpend and lastPurchaseDate. For example:
Platinum: Spend >= 10,000 and purchase within 6 months
Gold: Spend >= 1,000 and purchase within 12 months
Silver: All others
The calculation is performed dynamically in the CustomerResponse constructor.

Test Coverage Approach:
Unit tests were written only for the CustomerServiceImpl class, assuming that testing the service layer thoroughly covers the business logic(as per the document).

No Authentication:
Assumed that this API is open and does not require authentication, as no auth-related constraints were mentioned.

No Data Check:
No uniqueness constraint on email was assumed or enforced at the DB level, unless explicitly required.


