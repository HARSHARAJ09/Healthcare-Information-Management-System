**Hospital_Management_System**
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── in
│   │   │       └── HMS
│   │   │           ├── Config        # Application and security configurations
│   │   │           ├── DTO           # Data Transfer Objects
│   │   │           ├── Entity        # JPA entity classes
│   │   │           ├── Exception     # Custom exception handling
│   │   │           ├── IService      # Service interfaces
│   │   │           ├── IServiceImpl  # Service implementation classes
│   │   │           ├── Repository    # JPA repository interfaces
│   │   │           ├── Request       # Request payload classes
│   │   │           ├── Response      # Response wrapper classes
│   │   │           ├── Rest          # REST controllers
│   │   │           └── Utils         # Utility and helper classes
│   │   │
│   │   └── resources
│   │       ├── application.properties / application.yml
│   │       └── static / templates (if applicable)
│   │
│   └── test
│       └── java                     # Unit and integration test cases
│
├── target                            # Build-generated files
├── HELP.md                           # Spring Boot help documentation
├── mvnw                              # Maven wrapper script (Linux/Mac)
├── mvnw.cmd                          # Maven wrapper script (Windows)
├── pom.xml                           # Maven project configuration
└── README.md                         # Project documentation
