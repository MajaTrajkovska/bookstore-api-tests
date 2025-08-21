# Bookstore API Test Automation 📚

This project contains automated API tests for the Bookstore API using REST Assured, JUnit 5, Maven, Docker, and Allure Reporting.

It provides both local execution and CI/CD pipelines via GitHub Actions with published Allure reports on GitHub Pages.

# 📋 Table of Contents
1. Project Overview
2. Project Structure
3. Getting Started
4. Running Tests (Local Execution)
5. Docker Execution
6. Allure Reporting
7. CI/CD Pipeline (GitHub Actions)
8. Example Workflows
9. Troubleshooting
10. Test Results

## 1. Project Overview
This framework tests the REST APIs for a Bookstore application, focusing on:

- Authors API endpoints (GET, POST, PUT, DELETE)
- Books API endpoints (GET, POST, PUT, DELETE)

Tests are organized by resource and HTTP method with appropriate test cases for validation.

## 2. Project Structure
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


## 3. 🚀 Getting Started
### Prerequisites
- Java 17+
- Maven 3.8+
- Docker (for containerized execution)
- Allure CLI (for viewing reports)

### Local Setup

**Clone the repository**
```sh
git clone https://github.com/MajaTrajkovska/bookstore-api-tests.git
cd bookstore-api-tests
mvn clean install
```
**Install prerequisites**


## 4. ▶️ Running Tests 
### Local Execution 

**Run all tests:**
```sh
mvn clean test
```

**Run only tests with a specific JUnit 5 tag (e.g., "positive"):**
```sh
   mvn clean test -Dgroups="positive"   
   mvn clean test -Dgroups="negative"   
   mvn clean test -Dgroups="edge"

   ```
**Generate and view the Allure report:**
```sh
allure serve target/allure-results
```
*(This will open the report in your browser. Make sure Allure CLI is installed.)*


## 5. Docker Execution 

**Build the Docker image:**
```sh
docker build -t bookstore-api-tests .
```

**Run all tests in the container:**
```sh
docker run --name bookstore-tests bookstore-api-tests
```
In the example bookstore-tests is the name of the container

**Run tests in the container with specific tag:**
```sh
docker run --name bookstore-tests -e TAGS=positive bookstore-api-tests
```

**Open the Allure report to see the results**
If you like to see the results in allure report after you executed them in a Docker container then follow these steps:

1. Make sure that the local target/allure-results folder is empty
```sh
Remove-Item -Recurse -Force .\target\allure-results\*      --->     (for Windows powershell)
rm -rf ./target/allure-results/*                           --->     (for MacOS/Linux schell)
```

2. Then copy Allure results from container:
```sh
docker cp bookstore-tests:/app/target/allure-results ./target/
```
3. Once you have the results locally, generate and view the report:
```sh
allure serve target/allure-results
```

**Delete the container:**
```sh
docker rm -f bookstore-tests
```


Note: There is a posibility to run tests with a custom base URL: 
```sh
docker run --name bookstore-tests -e BASE_URL=https://your-api-url.com bookstore-api-tests
```
In our case the BASE_URL has a fixed value: https://fakerestapi.azurewebsites.net


## 6. 📊 Allure Reporting
The report includes:

- Test execution results
- API request/response details
- Test durations and trends

## 7. ⚡ CI/CD Pipeline (GitHub Actions)

**Trigger Types:**
- Push/PR to main → Runs full test suite, deploys report to /all-tests/.
- Manual Dispatch → Run tests with specific tags (e.g., positive, negative, edge) and deploys under /tagged-tests/<tag>/. 
  (Use the "Run worflow" option in GitHub Actions to manually trigger specific group of tests)
- Nightly Job (2 AM UTC) → Runs full suite automatically and deploys under /nightly/.


**Example Report Links:**
All Tests (latest push/PR):
https://MajaTrajkovska.github.io/bookstore-api-tests/all-tests/

Tagged Tests (manual run, e.g., positive):
https://MajaTrajkovska.github.io/bookstore-api-tests/tagged-tests/positive/

Nightly Tests (daily 2 AM UTC):
https://MajaTrajkovska.github.io/bookstore-api-tests/nightly/

**What is GitHub Pages?**
GitHub Pages is a static site hosting service built into GitHub.
In this project, it is used to host Allure HTML reports online for easy access and sharing.


## 8. 📝 Example Workflows
Complete Test Cycle with Docker

**-Build the Docker image:**
docker build -t bookstore-api-tests .

**-Run positive tests:**
docker run --name bookstore-tests -e TAGS=positive bookstore-api-tests

**-Delete the old results:**
Remove-Item -Recurse -Force .\target\allure-results\* (for Windows powershell)
rm -rf ./target/allure-results/* (for MacOS/Linux schell)

**-Copy the results:**
docker cp bookstore-tests:/app/target/allure-results ./target/

**-Generate and view the report:**
allure serve target/allure-results

**-Clean up the container:**
docker rm bookstore-tests


## 9. 🔧 Troubleshooting
If you encounter any issues while running the tests, please refer to the following common solutions:

Ensure Docker service is running when attempting to build or run containers
Check network connectivity if tests fail with connection errors
Make sure the container is still running when using docker cp to copy results

## 10. Test Results
Currently few tests are failing but that is expected. In the files with the tests is given a detailed explanation why those tests are expected to fail. 
The issue is not into the test code, but those are because of some limitations/defects with tehe fake API. 
For example: 
There is no logic to be able to delete an author with non-existing ID, but the fake API is returning 200OK. This test case is EXPECTED TO FAIL because the API should not allow deletion of an author that does not exist in the DB.

