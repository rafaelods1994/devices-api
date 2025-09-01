# Devices API

![All Tests Pass](https://github.com/rafaelods1994/devices-api/actions/workflows/ci.yml/badge.svg?label=All%20Tests%20Pass)
![Coverage](docs/coverage-badge.svg)
![Mutation Score](docs/mutation-badge.svg)

REST API for managing devices, built with **Spring Boot 3** and **Java 21**.

---

## 🛠 Tech Stack
- Spring Boot 3.5.5
- Java 21
- Gradle 8.14.3 (via Gradle Wrapper)
- JUnit 5 (unit + integration tests)
- JaCoCo (code coverage)
- PIT Mutation Testing (test robustness)
- GitHub Actions (CI/CD with badges)
- **springdoc-openapi** (Swagger UI for API docs)
- Docker + Docker Compose (local + dev environments)
- PostgreSQL 17 (local DB + containerized DB)
- Flyway (database migrations)

---

## 🏁 Quick Start

### Prerequisites
- Java 21
- Docker & Docker Compose
- Gradle (or use the included `./gradlew` wrapper)

### Clone & Run (Local)
```bash
git clone https://github.com/rafaelods1994/devices-api.git
cd devices-api
./gradlew bootRun --args='--spring.profiles.active=dev'
```

# Run with Docker Compose
## Start API + DB stack
docker compose up --build

## Stop and remove containers
docker compose down --remove-orphans

## Stop, remove, and clear volumes (reset DB)
docker compose down -v --remove-orphans

Swagger UI: http://localhost:8080/swagger-ui.html 
OpenAPI JSON: http://localhost:8080/v3/api-docs

# 📡 Example API Calls

## Create a new device
```bash
curl -X POST http://localhost:8080/devices \
  -H "Content-Type: application/json" \
  -d '{"name":"Lamp","brand":"Philips","state":"AVAILABLE"}'
```

## Get all devices
```bash
curl http://localhost:8080/devices
```
## Get a device by ID
```bash
curl http://localhost:8080/devices/1
```


SPRING_PROFILES_ACTIVE	dev	Active Spring profile
DB_HOST	localhost	Database host
DB_PORT	5432	Database port
DB_NAME	devices	Database name
DB_USER	devices	Database username
DB_PASSWORD	devices	Database password

## Project Structure
```
src/
├── main/java/...                          # Application source code
├── main/resources/application-docker.yml  # Docker Compose profile config
├── test/java/...                          # Unit tests
└── integrationTest/java/...               # Integration tests

gradle/
├── dependencies.gradle                     # Shared dependencies configuration
└── test/
├── coverage.gradle                     # JaCoCo config (XML + HTML reports)
├── mutation.gradle                     # PIT mutation testing config
└── integration.gradle                  # Integration test source set + task

docs/
├── coverage-badge.svg                      # Generated in CI
└── mutation-badge.svg                      # Generated in CI

.github/workflows/
└── ci.yml                                  # CI pipeline (tests, PIT, coverage, badges)

docker-compose.yml                           # Runs API + PostgreSQL together
```

# What's Configured
## Continuous Integration
```
GitHub Actions workflow (ci.yml) runs on every push to main branch:

Builds with Gradle 8.14.3

Runs unit tests only by default (integration tests can be run manually)

Executes PIT mutation tests

Generates JaCoCo coverage and mutation score reports

Creates and commits badges (coverage + mutation) to docs/
```
## Testing
```
Unit tests in src/test/java

Integration tests in src/integrationTest/java:

Defined in gradle/test/integration.gradle as a separate source set

No longer run by default during build to keep builds fast

Run explicitly with ./gradlew integrationTest

Use Testcontainers (PostgreSQL 17) + shared Spring context for speed
```

## Code Quality & Reporting
```
JaCoCo HTML + XML coverage reports

PIT mutation testing reports

Badges generated and committed automatically via CI

```

## Shared Dependencies
```
Centralized in gradle/dependencies.gradle
```

## Docker / Profiles

```
application-docker.yml profile points API container to devices-db service in Compose network

Compose stack:

devices-db: PostgreSQL 17

devices-api: Spring Boot app with Swagger UI
```
  
# Commands
```bash
# Full build: clean, unit tests, PIT, coverage
./gradlew clean build

# Only unit tests
./gradlew test

# Only integration tests
./gradlew integrationTest

# JaCoCo report (HTML + XML)
./gradlew jacocoTestReport

```

# 📌 Planned Improvements / TODO
Environments
Add application-prod.yml and application-staging.yml

Parameterize DB credentials via environment variables

Set logging levels per profile

Security
Add authentication/authorization (Spring Security + JWT)

Restrict Swagger UI in non‑dev profiles

Database
Add indexes and stricter validation

CI/CD
Add separate jobs for integration tests and unit tests

Docker image build + push

Testing
Add filter/sort/pagination integration tests

Performance testing in staging