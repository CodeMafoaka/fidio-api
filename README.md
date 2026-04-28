# Fidio API - Madagascar Voting System

## Description
Fidio API is a secure backend for a mobile electronic voting system designed for Madagascar. It provides robust features for citizen registration, election management, and secure, anonymous voting. The system incorporates Role-Based Access Control (RBAC) and biometric data support (FACE_ID) to ensure the integrity and security of the voting process.

## Tech Stack
- **Language:** Java 21
- **Framework:** Spring Boot 3.2.5
- **Database:** PostgreSQL (Production), H2 (Testing)
- **Migrations:** Flyway
- **Security:** Spring Security + JWT (JSON Web Tokens)
- **API Documentation:** OpenAPI 3.0 (Swagger)
- **Build Tool:** Gradle 8.7
- **Containerization:** Docker

## Features
- **Citizen Management:** Registration, profile updates, and secure authentication.
- **Role-Based Access Control (RBAC):** Distinct permissions for `ADMIN` and `USER` roles.
- **Election Management:** Admins can create and manage elections and candidates.
- **Secure Voting:** Ensures one vote per citizen per election.
- **Real-time Results:** Dynamic calculation of election results.
- **Admin Bootstrapping:** Automatic creation of an initial admin account via environment variables.
- **Request Logging:** Comprehensive logging of all API requests and responses.
- **Global Error Handling:** Consistent API error responses.

## Prerequisites
- Java 21
- PostgreSQL
- Docker (optional)

## Installation & Setup

### 1. Clone the repository
```bash
git clone <repository-url>
cd fidio-api
```

### 2. Environment Variables
The application requires several environment variables to run correctly. You can set these in your shell or via a `.env` file (if using a plugin).

| Variable | Description | Example |
|----------|-------------|---------|
| `DATABASE_URL` | PostgreSQL connection URL | `jdbc:postgresql://localhost:5432/fidio` |
| `DATABASE_USERNAME` | Database username | `postgres` |
| `DATABASE_PASSWORD` | Database password | `password` |
| `JWT_SECRET` | Secret key for JWT generation | `your_very_long_and_secure_jwt_secret_key` |
| `ADMIN_GID` | (Optional) Admin Global ID for bootstrap | `000000000000` |
| `ADMIN_PASSWORD` | (Optional) Admin password for bootstrap | `admin_pass` |
| `ADMIN_FIRST_NAME` | (Optional) Admin first name | `Admin` |
| `ADMIN_LAST_NAME` | (Optional) Admin last name | `SuperUser` |

### 3. Build the Application
```bash
./gradlew build
```
This will also trigger the `openApiGenerate` task to generate the API interfaces and models from the OpenAPI specification.

### 4. Run Locally
```bash
./gradlew bootRun
```

### 5. Run with Docker
```bash
docker build -t fidio-api .
docker run -p 8080:8080 -e DATABASE_URL=... -e JWT_SECRET=... fidio-api
```

## API Documentation
The API is documented using OpenAPI. The specification file can be found at `doc/openapi.yml`.
When the application is running, you can typically access the Swagger UI (if enabled) or use tools like Postman to interact with the endpoints.

## Testing
To run the full test suite, including integration tests (using an in-memory H2 database):
```bash
./gradlew test
```

## AI Usage
This project was developed with the assistance of AI tools. For a detailed log of AI contributions and tools used, please refer to [AI_USAGE.md](./AI_USAGE.md).

## License
This project is for educational/demo purposes.
