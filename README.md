# 🏥 Health Information Management System (HMS)

A Spring Boot–based Hospital Management System developed to efficiently manage essential hospital operations such as patient registration, appointment scheduling, doctor allocation, and medical records management. The application implements secure role-based access control for Admin, Doctor, and Patient using Spring Security, ensuring controlled and reliable access to system functionalities through RESTful web services.

---

## 📌 Features

*Patient Registration and Profile Management
*Appointment Scheduling and Management
*Doctor Allocation to Patients
*Secure Medical Records Management
*Role-Based Access Control (Admin, Doctor, Patient)
*RESTful APIs for all operations
*Authentication and Authorization using Spring Security

---

## 🛠 Tech Stack

* **Backend:** Java 17, Spring Boot
* **Database:** MySQL
* **ORM:** Spring Data JPA / Hibernate
* **Build Tool:** Maven
* **API:** RESTful Web Services
* **Tools:** Swagger or Postman, Git, GitHub

---

## 📁 Project Structure

```
Hospital_Management_System
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── in
│   │   │       └── HMS
│   │   │           ├── Config        # Configuration classes
│   │   │           ├── DTO           # Data Transfer Objects
│   │   │           ├── Entity        # JPA entity classes
│   │   │           ├── Exception     # Custom exception handling
│   │   │           ├── IService      # Service interfaces
│   │   │           ├── IServiceImpl  # Service implementations
│   │   │           ├── Repository    # JPA repositories
│   │   │           ├── Request       # Request payload classes
│   │   │           ├── Response      # Response payload classes
│   │   │           ├── Rest          # REST controllers
│   │   │           └── Utils         # Utility classes
│   │   │
│   │   └── resources
│   │       ├── application.properties
│   │       └── static / templates
│   │
│   └── test
│       └── java
│
├── target
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

* Java 17+
* Maven
* MySQL
* IDE (IntelliJ / Eclipse / STS)

### Steps to Run

1. Clone the repository

   ```bash
   git clone https://github.com/your-username/hospital-management-system.git
   ```
2. Configure database in `application.properties`
3. Build the project

   ```bash
   mvn clean install
   ```
4. Run the application

   ```bash
   mvn spring-boot:run
   ```
5. Access APIs via Postman or Swagger

---
