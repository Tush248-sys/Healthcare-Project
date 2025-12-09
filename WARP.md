# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Project overview

This is a small Spring Boot 3.x application that exposes a simple patient registration flow backed by an in‑memory H2 database and Thymeleaf templates. The user fills out a registration form at `/register`, and on submit the app currently displays a success page without persisting data (persistence is planned via a TODO in the controller).

## Common commands

All commands are intended to be run from the project root where `pom.xml` lives.

### Run the application in development

```bash
mvn spring-boot:run
```

Then open `http://localhost:8080/register` in a browser to access the registration form (from `README.md`).

### Build the application JAR

```bash
mvn clean package
```

The runnable JAR will be created at:

```text
target/healthcare-registration-portal-0.0.1-SNAPSHOT.jar
```

You can run it with:

```bash
java -jar target/healthcare-registration-portal-0.0.1-SNAPSHOT.jar
```

### Run tests

There are currently no test classes in the repo, but once tests exist under `src/test/java`, use:

```bash
mvn test
```

To run a single test class:

```bash
mvn -Dtest=FullyQualifiedTestClassName test
```

For example, if you add `RegistrationControllerTest` under `com.example.healthcare`:

```bash
mvn -Dtest=com.example.healthcare.RegistrationControllerTest test
```

## High-level architecture

### Technology stack

- **Framework:** Spring Boot (parent `spring-boot-starter-parent` 3.3.0) with Java 17 (`pom.xml`).
- **Starters:**
  - `spring-boot-starter-web` for MVC controllers and embedded server.
  - `spring-boot-starter-thymeleaf` for server-side HTML templates.
  - `spring-boot-starter-data-jpa` plus `h2` for in-memory relational storage (currently configured but not fully used in code).
- **Build system:** Maven, with `spring-boot-maven-plugin` configured for packaging and running.

### Application entry point

- `src/main/java/com/example/healthcare/HealthcareRegistrationPortalApplication.java`
  - Annotated with `@SpringBootApplication` and contains the `main` method.
  - Bootstraps the Spring context, embedded server, and auto-configuration.

### Web / MVC layer

- `src/main/java/com/example/healthcare/RegistrationController.java`
  - Annotated with `@Controller`, using Spring MVC and Thymeleaf model binding.
  - **GET `/register`** (`showRegistrationForm`):
    - Adds a new `PatientRegistrationForm` instance to the model as `"patient"`.
    - Returns the logical view name `"register"`, rendered by `templates/register.html`.
  - **POST `/register`** (`submitRegistration`):
    - Binds incoming form fields to `@ModelAttribute("patient") PatientRegistrationForm`.
    - Currently only adds a success message to the model and returns `"register-success"`.
    - Contains a `// TODO: Persist registration details to the database` comment where future persistence logic should go.

### Form / view model

- `src/main/java/com/example/healthcare/PatientRegistrationForm.java`
  - Simple Java bean used as the **form-backing object** for Thymeleaf binding.
  - Fields: `firstName`, `lastName`, `email`, `phone`, `dateOfBirth`, `insuranceNumber`.
  - Standard getters and setters for each field.
  - The property names must stay in sync with the `th:field="*{...}` bindings in `register.html`.

### Views (Thymeleaf templates)

- `src/main/resources/templates/register.html`
  - HTML form bound to `patient` via `th:object="${patient}"`.
  - Uses `th:field="*{...}` for each `PatientRegistrationForm` field, and posts to `@{/register}`.
  - This template drives the shape of `PatientRegistrationForm`; adding/removing fields requires updating both the Java class and this template.

- `src/main/resources/templates/register-success.html`
  - Displays a success message from the model attribute `message` (`th:text="${message}"`).
  - Provides a link back to `/register` for registering another patient.

### Persistence and configuration

- `src/main/resources/application.properties`
  - Sets the application name and port (`spring.application.name`, `server.port=8080`).
  - Configures an in-memory H2 database and JPA/Hibernate defaults:
    - `spring.datasource.url=jdbc:h2:mem:healthcare_registration_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
    - `spring.jpa.hibernate.ddl-auto=update` (auto-creates/updates schema based on JPA entities when they are added).
    - SQL logging enabled via `spring.jpa.show-sql=true` and formatted via `spring.jpa.properties.hibernate.format_sql=true`.
  - Intended H2 console configuration:
    - There is a `dspring.h2.console.enabled=true` line which appears to be a typo; to fully enable the H2 console at the configured path (`spring.h2.console.path=/h2-console`), this likely should be `spring.h2.console.enabled=true`.

Currently the codebase does **not** define any JPA entities or repositories; only the infrastructure is configured. When adding persistence, expect to introduce entity classes, repositories, and possibly a service layer in `src/main/java/com/example/healthcare`.

## Agent-specific guidance for changes

- **Adding or modifying registration fields**
  - Update `PatientRegistrationForm` with new fields and corresponding getters/setters.
  - Keep `register.html` in sync:
    - Add or update `<input>` elements with matching `th:field="*{fieldName}"`.
  - Ensure any future persistence layer (entity classes and repositories) remains consistent with the form model.

- **Implementing persistence for registrations**
  - The natural place to integrate persistence is the `submitRegistration` method in `RegistrationController` where the TODO is.
  - Introduce a JPA entity (e.g., `PatientRegistration`) and a Spring Data repository, and call that repository from the controller (or from a new service layer) to save the submitted form.

- **Working with the H2 console**
  - Once the `spring.h2.console.enabled` property is corrected, the console will be accessible at `/h2-console` (useful for inspecting the in-memory DB during development).
  - With the current configuration, H2 is in-memory only; data will not persist across application restarts.
