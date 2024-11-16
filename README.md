# TODO Application

## Description
This is a simple **REST API application for managing TODO tasks**, developed in Java using **Spring Boot** and **Gradle**. The application allows you to create, update, delete, and view tasks. It uses an in-memory database **H2** for data storage.

## Technologies
The project is built with the following technologies:
- Java 17
- Spring Boot 3.3.5
- Gradle
- H2 Database (in-memory)
- JUnit 5 and Mockito for testing

## Features
The application provides the following features:
- Create a new task (`POST /api/todos`)
- Retrieve all tasks (`GET /api/todos`)
- Retrieve a task by ID (`GET /api/todos/{id}`)
- Update a task (`PUT /api/todos/{id}`)
- Delete a task (`DELETE /api/todos/{id}`)

## Project Structure
```plaintext
src/main/java/com/example/todo_app
├── controller      # Controllers for handling HTTP requests
├── model           # Data model (Todo)
├── repository      # Repository for database interactions
├── service         # Business logic
└── exception       # Custom exceptions (if used)
```

## Installation

1. **Clone the repository to your local machine:**
    ```bash
    git clone https://github.com/Scroll-master/todo-app.git
    cd todo-app
    ```

2. **Build and test the project:**
    ```bash
    ./gradlew clean build
    ```

3. **Run the application:**
    ```bash
    ./gradlew bootRun
    ```

4. The application will be available at:
    ```
    http://localhost:8080
    ```

## Testing the API with Postman

**Examples of API requests using Postman:**

- **Create a new task**
    - **POST** `http://localhost:8080/api/todos`
    - Body (JSON):
      ```json
      {
        "text": "New Task"
      }
      ```

- **Retrieve all tasks**
    - **GET** `http://localhost:8080/api/todos`

- **Retrieve a task by ID**
    - **GET** `http://localhost:8080/api/todos/1`

- **Update a task**
    - **PUT** `http://localhost:8080/api/todos/1`
    - Body (JSON):
      ```json
      {
        "text": "Updated Task"
      }
      ```

- **Delete a task**
    - **DELETE** `http://localhost:8080/api/todos/1`

## Accessing the H2 Database Console
The application uses an embedded **H2 database**. The console is accessible at: `http://localhost:8080/h2-console`


Use the following credentials:
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`

## Running Tests
The project uses **JUnit 5** and **Mockito** for unit testing.

**To run the tests:**
```bash
./gradlew clean test


