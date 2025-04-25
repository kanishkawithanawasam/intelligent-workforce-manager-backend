# Intelligent Workforce Management System - Backend

This is the backend for the **Intelligent Workforce Management System**, designed to dynamically generate and adjust employee schedules based on real-time demand using AI algorithms like fuzzy genetic algorithms and simulated annealing.

The backend is structured into two main Maven modules:
- `rest-api`: Exposes RESTful endpoints, handles authentication, and integrates with external services.
- `schedule-engine`: Contains the core scheduling logic and optimisation algorithms.

## Project Structure

```
intelligent-workforce-management-system/
├── rest-api/
├── schedule-engine/
└── pom.xml
```

## 🔧 Setup Instructions

### Requirements
- Java 17
- Maven 3.6+
- MySQL running and accessible
- **Demand Predictor** and **Transaction Service** must be running before starting this application

### Configuration
Update MySQL connection properties in `rest-api/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/iwm_db
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### Build

```bash
mvn clean install
```

### Run the Backend Application

```bash
cd rest-api
mvn spring-boot:run
```

## Important Runtime Notes
- **Demand Predictor** and **Transaction Service** must be running before starting this backend.
- The `/dashboard` endpoint only provides data during **configured business hours**. These hours can be adjusted in the service classes inside the `rest-api` module.
- If the default domain addresses and ports were changed, they needs to updated in relevant class files.

## Authentication
- Users authenticate using credentials and receive a **JWT token**.
- Admin users needa to be created by sending credentials to `/api/v1/auth/signup`
- ```
  {
  "email": "user@user.com",
  "password": "user123",
  "role":"USER",
  "employeeId":2
  }
  ```

## API Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

## Schedule Engine Overview
The `schedule-engine` module provides:
- Weekly schedule generation according to constraints and preferences
- Real-time schedule updates based on demand forecasts
- Optimization using fuzzy genetic algorithms and simulated annealing

This module is used internally by the REST API.

## Testing

Run tests using:

```bash
mvn test
```

## Key Dependencies
- Spring Boot 3.4.4 (Web, JPA, Security)
- Lombok
- MySQL Connector/J
- JSON Web Token (JJWT)
- SpringDoc OpenAPI (Swagger)
