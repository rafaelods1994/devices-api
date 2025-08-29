# Devices API

![All Tests Pass](https://github.com/rafaelods1994/devices-api/actions/workflows/ci.yml/badge.svg?label=All%20Tests%20Pass)
![Coverage](docs/coverage-badge.svg)
![Mutation Score](docs/mutation-badge.svg)

# Devices API

REST API for managing devices, built with **Spring Boot 3** and **Java 21**.

## 🛠 Tech Stack
- Spring Boot 3.5.5
- Java 21
- Gradle 8.14.3 (via Gradle Wrapper)
- JUnit 5 (unit + integration tests)
- JaCoCo (code coverage)
- PIT Mutation Testing (test robustness)
- GitHub Actions (CI/CD with badges)

## 📂 Project Structure
src/
├── main/java/...               # Application source code
├── test/java/...               # Unit tests
└── integrationTest/java/...    # Integration tests
gradle/
├── dependencies.gradle         # Shared dependencies configuration
└── test/
├── coverage.gradle         # JaCoCo config (XML + HTML reports)
├── mutation.gradle         # PIT mutation testing config
└── integration.gradle      # Integration test source set + task
docs/
├── coverage-badge.svg          # Generated in CI
└── mutation-badge.svg          # Generated in CI
.github/workflows/
└── ci.yml                      # CI pipeline (tests, PIT, coverage, badges)

## ✅ What’s Configured

### Continuous Integration
- GitHub Actions workflow (`ci.yml`) runs on every push and PR:
    - Builds with Gradle 8.14.3
    - Runs unit tests and integration tests
    - Executes PIT mutation tests
    - Generates JaCoCo coverage (instruction coverage from report-level summary)
    - Creates and commits badges (coverage + mutation) to `docs/`

### Testing
- Unit tests in `src/test/java`
- Integration tests in `src/integrationTest/java`
    - Defined in `gradle/test/integration.gradle` as a separate source set
    - Task `integrationTest` runs after `test` and is part of `check`
    - Inherits dependencies from `testImplementation`

### Code Quality & Reporting
- JaCoCo:
    - Instruction coverage derived from the report-level `<counter type="INSTRUCTION">`
    - HTML: `build/reports/jacoco/test/html/index.html`
    - XML: `build/reports/jacoco/test/jacocoTestReport.xml`
- PIT:
    - Mutation score and full HTML report at `build/reports/pitest/index.html`

### Shared Dependencies
- Centralized in `gradle/dependencies.gradle` and applied from the root `build.gradle`

## 🚀 Commands
```bash
# Full build: clean, unit + integration tests, mutation testing, reports
./gradlew clean build

# Only unit tests
./gradlew test

# Only integration tests
./gradlew integrationTest

# JaCoCo report (HTML + XML)
./gradlew jacocoTestReport
