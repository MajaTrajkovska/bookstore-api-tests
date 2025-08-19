# ---------- STAGE 1: Build & cache dependencies ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only pom.xml first to leverage Docker cache for dependencies
COPY pom.xml ./

# Download dependencies (will be cached unless pom.xml changes)
RUN mvn dependency:go-offline

# Copy the rest of the project
COPY src ./src
COPY src/test/resources ./src/test/resources

# Package tests without running them (we'll run them in final container)
RUN mvn clean package -DskipTests

# ---------- STAGE 2: Run tests ----------
FROM maven:3.9.6-eclipse-temurin-17 AS runner

WORKDIR /app

# Copy compiled code & dependencies from build stage
COPY --from=build /root/.m2 /root/.m2
COPY --from=build /app /app

# Environment variables (can be overridden at runtime)
ENV BASE_URL=https://fakerestapi.azurewebsites.net
ENV TAGS=

# Run tests with env var substitution (shell needed for $BASE_URL and $TAGS to expand)
CMD sh -c "mvn clean test -Dbase.url=$BASE_URL -Dgroups=$TAGS -Dallure.results.directory=target/allure-results"