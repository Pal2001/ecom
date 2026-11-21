# E-Commerce Backend API

A backend application built using **Java, Spring Boot, Spring Security, and Spring Data JPA**. This project focuses on building REST APIs for e-commerce functionality, including user authentication, product management, inventory, cart/order processing, and user account management.

The goal is to build a secure, maintainable backend while gaining practical experience with backend development, testing, and eventually cloud deployment.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot 3.5 | Backend framework |
| Spring Security | Authentication and authorization |
| Spring Data JPA | Database operations |
| Hibernate | ORM |
| JWT | Token-based authentication |
| BCrypt | Password encryption |
| Maven | Dependency management and build |
| Microsoft SQL Server | Development database |
| H2 Database | Testing |
| JUnit | Unit and integration testing |
| MockMvc | REST API testing |
| GreenMail | Email testing |
| Postman | API testing |

## Features

### Authentication and User Management
- User registration with input validation.
- Password encryption using BCrypt.
- JWT-based authentication.
- Protected API endpoints.
- Email verification using verification tokens.
- Resend email verification functionality.
- Forgot password functionality.
- Password reset using time-limited JWT tokens.

### Product and Inventory
- Product-related REST API functionality.
- Inventory management.
- Stock and quantity handling.

### Cart and Order Management
- Order creation and management.
- Cart and order quantity handling.
- User-specific order access.

### Address Management
- Retrieve user addresses.
- Add a new address.
- Update an existing address.
- Authorization checks to restrict access to the authenticated user's data.

### Testing and Security
- Authentication and authorization testing.
- JWT signature and issuer validation.
- Registration and validation testing.
- Password reset testing.
- Email functionality testing.
- Integration testing using MockMvc and H2.

## Project Structure

The application follows a layered backend architecture.

```text
src/
├── main/
│   ├── java/
│   │   └── .../
│   │       ├── controllers/
│   │       ├── services/
│   │       ├── dao/
│   │       ├── entities/
│   │       ├── security/
│   │       ├── exceptions/
│   │       └── configuration/
│   │
│   └── resources/
│       └── application.properties/
│
└── test/
    ├── java/
    │   └── .../
    └── resources/
        └── application.properties
```

*The structure above illustrates the logical layers; actual package names may differ.*

## Getting Started

### Prerequisites

Make sure you have installed:

- Java 17 or later
- Maven
- Microsoft SQL Server
- Git
- Postman (optional, for API testing)

### 1. Clone the repository

```bash
git clone https://github.com/Pal2001/ecom.git
```

### 2. Navigate to the project

```bash
cd ecom
```

### 3. Configure the database

Update your `application.properties` with your database configuration.

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
```

Configure the remaining database properties according to your local environment.

**Important:** Keep database credentials, JWT secrets, and email credentials outside source control. Use environment variables or a local configuration file that is excluded through `.gitignore`.

### 4. Configure email settings

The application uses email functionality for account verification and password reset.

Configure your SMTP settings using your email provider or local email testing environment.

Do not commit real email credentials.

### 5. Build the application

```bash
mvn clean package
```

### 6. Run the application

```bash
mvn spring-boot:run
```

The application will start on the configured Spring Boot port (8080 by default).

## API Overview

The following are the main API areas currently covered by the project.

| Module | Endpoint | Purpose |
|---|---|---|
| Authentication | `/auth/register` | Register a user |
| Authentication | `/auth/login` | Authenticate a user |
| Authentication | `/auth/me` | Retrieve authenticated user details |
| Password | `/auth/forgot` | Request a password reset |
| Password | `/auth/reset` | Reset password |
| User Address | `/user/{userId}/address` | Retrieve or add addresses |
| User Address | `/user/{userId}/address/{addressId}` | Update an address |
| Product | `/product` | Product API |
| Order | `/order` | Order API |

Refer to the controller implementations for the exact HTTP methods, request parameters, request bodies, and response formats.

## Running Tests

Execute the test suite using:

```bash
mvn test
```

The project uses an H2 in-memory database for testing, keeping test execution separate from the development database.

## Security Considerations

- Passwords are stored using BCrypt hashing.
- JWT tokens are validated using signature and issuer verification.
- Protected endpoints require authentication.
- User-specific operations include authorization checks.
- Sensitive configuration values should be supplied through environment variables.

## Future Improvements

This project is actively being developed. Planned improvements include:

- [ ] Dockerize the Spring Boot application.
- [ ] Run the application and database using Docker Compose.
- [ ] Set up CI/CD using GitHub Actions.
- [ ] Deploy the application on AWS EC2.
- [ ] Configure a managed database using AWS RDS.
- [ ] Configure Nginx and HTTPS.
- [ ] Explore AWS ECR and ECS for container deployment.
- [ ] Improve automated test coverage.
- [ ] Add API documentation using Swagger/OpenAPI.

## What I'm Learning

Through this project, I'm focusing on:

- Designing and developing RESTful APIs.
- Implementing authentication and authorization.
- Working with relational databases and JPA.
- Writing unit and integration tests.
- Handling validation and application exceptions.
- Understanding application deployment and cloud infrastructure.

## Author

**Avinash Pal**

Java | Spring Boot | Backend Development

- GitHub: https://github.com/Pal2001
- LinkedIn: https://www.linkedin.com/in/palavinash2001/

---

*This project is built as a practical learning journey, with a focus on writing better backend code and understanding how applications are built, tested, secured, and deployed.*
