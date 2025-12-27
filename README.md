# ("Website is under development")🚧🚨

# Hospital Management System (HMS) 

A Spring Boot–based Hospital Management System developed to manage essential hospital operations such as patient registration, appointment scheduling, doctor allocation, and medical records management. The application exposes secure RESTful APIs, implements JWT-based authentication with Spring Security, and provides role-based access for **Admin**, **Doctor**, and **Patient**. API documentation and testing are enabled using **Swagger**.

---

## Features

* Patient Registration and Profile Management
* Appointment Scheduling and Management
* Doctor Allocation to Patients
* Medical Records Management
* Role-Based Access Control (Admin, Doctor, Patient)
* JWT-Based Authentication and Authorization
* Secure RESTful APIs
* Swagger API Documentation and Testing

---

## Technology Stack

* **Backend:** Java 17, Spring Boot
* **Security:** Spring Security, JWT (JSON Web Token)
* **API:** RESTful Web Services
* **Documentation:** Swagger (OpenAPI)
* **Database:** MySQL
* **ORM:** Spring Data JPA / Hibernate
* **Build Tool:** Maven

---

## Project Structure

```
Hospital_Management_System
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── in
│   │   │       └── HMS
│   │   │           ├── Config        # Security, JWT, Swagger configuration
│   │   │           ├── DTO           # Data Transfer Objects
│   │   │           ├── Entity        # JPA entity classes
│   │   │           ├── Exception     # Global & custom exception handling
│   │   │           ├── IService      # Service interfaces
│   │   │           ├── IServiceImpl  # Service implementation classes
│   │   │           ├── Repository    # JPA repository interfaces
│   │   │           ├── Request       # Request payload models
│   │   │           ├── Response      # Response payload models
│   │   │           ├── Rest          # REST controllers
│   │   │           └── Utils         # JWT utilities and helper classes
│   │   │
│   │   └── resources
│   │       ├── application.properties
│   │       └── static / templates
│   │
│   └── test
│       └── java                     # Unit and integration tests
│
├── target
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

## Getting Started

### Prerequisites

* Java 17 or above
* Maven
* MySQL
* IDE (IntelliJ IDEA / Eclipse / STS)

---

### Steps to Run

1. Clone the repository

   ```bash
   git clone https://github.com/your-username/hospital-management-system.git
   ```

2. Configure MySQL details in `application.properties`

3. Build the project

   ```bash
   mvn clean install
   ```

4. Run the application

   ```bash
   mvn spring-boot:run
   ```

5. Access the APIs using Postman or Swagger UI

---

## Swagger API Documentation

Once the application is running, access Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

Swagger provides interactive API documentation for testing secured and unsecured endpoints.

---

## Security Overview

* Authentication implemented using **JWT (JSON Web Tokens)**
* Authorization handled via **Spring Security**
* Role-based access control for:

  * ADMIN
  * DOCTOR
  * PATIENT
* JWT token required for accessing secured APIs

---
