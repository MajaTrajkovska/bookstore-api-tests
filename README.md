### Bookstore API Test Automation 📚

This project contains automated API tests for the Bookstore API using REST Assured, JUnit 5, and Allure reporting.

### 📋 Table of Contents
1. Project Overview
2. Project Structure
3. Getting Started
4. Running Tests (Local Execution)
5. Docker Execution
6. Allure Reporting
7. Example Workflows
8. Troubleshooting

### 1. Project Overview
This framework tests the REST APIs for a Bookstore application, focusing on:

- Authors API endpoints (GET, POST, PUT, DELETE)
- Books API endpoints (GET, POST, PUT, DELETE)

Tests are organized by resource and HTTP method with appropriate test cases for validation.

### 2. Project Structure
bookstore-api-tests/
├── src/
│   ├── main/java/com/example/bookstoreapi/
│   │   ├── api/                    # API client classes
│   │   │   ├── AuthorsApis.java    # Authors API methods
│   │   │   └── BooksApis.java      # Books API methods
│   │   ├── config/
│   │   │   └── ConfigReader.java   # Configuration handler
│   │   ├── data/                   # Test data factories
│   │   │   ├── AuthorDataFactory.java
│   │   │   └── BookDataFactory.java
│   │   └── model/                  # POJO classes
│   │       ├── Author.java
│   │       └── Book.java
│   └── test/
│       ├── java/bookstore/
│       │   ├── authors/            # Author API tests
│       │   └── books/              # Book API tests
│       └── resources/
│           └── config.properties   # Test configuration
├── target/                         # Build artifacts and test reports
├── Dockerfile                      # Container definition
└── pom.xml                         # Project dependencies and build config


### 3. 🚀 Getting Started
# Prerequisites
- Java 17 or higher
- Maven 3.8+
- Docker (for containerized execution)
- Allure CLI (for viewing test reports)

# Local Setup
- Clone the repository
- Install prerequisites


### 4. ▶️ Running Tests 
# Local Execution

*Run all tests:
```sh
mvn clean test
```

*Run only tests with a specific JUnit 5 tag (e.g., "positive"):
```sh
   mvn clean test -Dgroups="positive"   
   mvn clean test -Dgroups="negative"   
   mvn clean test -Dgroups="edge"
   ```

*Generate and view the Allure report:
```sh
allure serve target/allure-results
```
*(This will open the report in your browser. Make sure Allure CLI is installed.)*


### 5. Docker Execution 

Build the Docker image:
```sh
docker build -t bookstore-api-tests .
```

Run all tests in the container:
```sh
docker run --name bookstore-tests bookstore-api-tests
```

Run tests in the container with specific tag:
```sh
docker run --name bookstore-tests -e TAGS=negative bookstore-api-tests
```

If you like to see the results in allure report after you executed them in a Docker container then follow these steps:

Make sure that the local target/allure-results folder is empty then copy Allure results from container:
```sh
docker cp bookstore-tests:/app/target/allure-results ./target/
```

Once you have the results locally, generate and view the report:
```sh
allure serve target/allure-results
```

Note: There is a posibility to run tests with a custom base URL: 
```sh
docker run --name bookstore-tests -e BASE_URL=https://your-api-url.com bookstore-api-tests
```
In our case the BASE_URL has a fixed value: https://fakerestapi.azurewebsites.net


### 6. 📊 Allure Reporting
The report includes:

- Test execution results
- API request/response details
- Test durations and trends


### 7. 📝 Example Workflows
Complete Test Cycle with Docker

# Build the Docker image
docker build -t bookstore-api-tests .

# Run negative tests
docker run --name bookstore-tests -e TAGS=negative bookstore-api-tests

# Copy the results
docker cp bookstore-tests:/app/target/allure-results ./target/

# Generate and view the report
allure serve target/allure-results

# Clean up
docker rm bookstore-tests


### 8. 🔧 Troubleshooting
If you encounter any issues while running the tests, please refer to the following common solutions:

Ensure Docker service is running when attempting to build or run containers
Check network connectivity if tests fail with connection errors
Make sure the container is still running when using docker cp to copy results



