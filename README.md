# Task Manager project

The **Task Manager API** is a RESTful backend service built with Java and Spring Boot, designed to manage personal tasks
efficiently. It allows users to create, retrieve, update, and delete Task items, as well as filter them by status. 
Authentication and user management are also supported. This project was made just for the sake of curiosity and practice.

## 📌 Features

- ✅ User Sign Up and Authentication (JWT)
- 📝 Create, View, Edit and Delete Task items
- 🔍 Filter Tasks by status (`PENDING`, `IN_PROGRESS`, `DONE`)
- 🔐 Protected routes using authentication middleware
- 📄 Swagger UI for API documentation

## 🛠️ Technologies

- Java 17+
- Spring Boot
- Spring Security
- JPA (Hibernate)
- PostgreSQL (Configurable)
- Swagger (SpringDoc OpenAPI)

## 📦 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- IntelliJ IDEA or other preferred IDE

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/marcosmatasousa/TaskManagerJava.git
   cd TaskManagerJava
   
2. Install dependencies and run:
    ```bash
   mvn spring-boot:run
   
3. Access the API at
    ```bash
    http://localhost:8080

4. For documentation and testing access: 
   ```bash
   http://localhost:8080/swagger-ui/index.html/

# 📄 License
This project is licensed under the MIT License.

Feel free to adapt the contents depending on your implementation (e.g. database used, endpoint details).