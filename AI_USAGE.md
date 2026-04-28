### AI Usage Log

#### `**feat: setup openapi generator and generate client models**`

1. **Changes Implemented**:
    *   **OpenAPI Generator Configuration**: Added `org.openapi.generator` plugin and configured `openApiGenerate` task in `build.gradle`.
    *   **Dependency Management**: Added `jackson-databind-nullable`, `swagger-annotations`, and `spring-boot-starter-validation` to `build.gradle`.
    *   **Spec Fix**: Corrected `doc/openapi.yml` by changing path parameters from `in: query` to `in: path` where appropriate (e.g., `/elections/{electionId}/result`).
    *   **Code Generation**: Generated API interfaces and models into `build/generated`.

2. **Verification**:
    *   Successfully ran `./gradlew openApiGenerate`.
    *   Verified generated files exist using `find build/generated -name "*.java"`.

3. **Specification**:
    *   **Model version**: gemini-2.0-flash-exp
    *   **Agent version**: Junie 2024.1
    *   **Date**: 2026-04-27

#### `**fix: enable CORS in spring security configuration**`

1. **Changes Implemented**:
    *   **Security Configuration**: Updated `SecurityConfig.java` to enable CORS using `.cors(Customizer.withDefaults())`. This ensures that Spring Security uses the `CorsConfigurationSource` bean defined in `CorsConfig.java`.
2. **Verification**:
    *   **Integration Testing**: Created `CorsIT.java` to verify that CORS headers (like `Access-Control-Allow-Origin` and `Access-Control-Allow-Credentials`) are correctly returned for preflight `OPTIONS` requests.
    *   **Test Execution**: Successfully ran `./gradlew test --tests code.mafoaka.fidio.CorsIT` and then the full test suite.
3. **Specification**:
    *   **Model version**: gemini-2.0-flash-exp
    *   **Agent version**: Jules 2024.1
    *   **Date**: 2026-04-28

#### `**feat: implement get all elections endpoint**`

1. **Changes Implemented**:
    *   **API Specification**: Added `GET /elections` to `doc/openapi.yml`.
    *   **Service Layer**: Implemented `getAllElections()` in `ElectionService.java`.
    *   **Controller Layer**: Implemented `getElections()` in `ElectionController.java`.
    *   **Test Environment**: Added H2 database dependency and configured `src/test/resources/application.properties` for integration testing.

2. **Verification**:
    *   **Integration Testing**: Created `ElectionIT.java` to verify the `GET /elections` endpoint.
    *   **Test Execution**: Successfully ran `./gradlew test`.

3. **Specification**:
    *   **Model version**: gemini-2.0-flash-exp
    *   **Agent version**: Jules 2024.1
    *   **Date**: 2026-04-28

#### `**feat: implement admin account bootstrap on startup**`

1. **Changes Implemented**:
    *   **Admin Bootstrap Logic**: Created `AdminAccountBootstrap` implementing `CommandLineRunner` to automatically create or update an admin account on application startup.
    *   **Configuration**: Added support for configuring admin credentials via environment variables: `ADMIN_GID`, `ADMIN_PASSWORD`, `ADMIN_FIRST_NAME`, and `ADMIN_LAST_NAME`.
    *   **Persistence**: Uses `CitizenService` for new admin creation to ensure password hashing and `CitizenRepository` to update existing users to `ADMIN` role if necessary.

2. **Verification**:
    *   **Unit Testing**: Created `AdminAccountBootstrapTest` to verify all bootstrap scenarios (creation, role update, skip if missing config, skip if already exists).
    *   **Test Execution**: Successfully ran `./gradlew test --tests code.mafoaka.fidio.AdminAccountBootstrapTest`.

3. **Specification**:
    *   **Model version**: gemini-2.0-flash-exp
    *   **Agent version**: Jules 2024.1
    *   **Date**: 2026-05-20

#### `**refactor: link candidate to citizen by gid**`

1. **Changes Implemented**:
    *   **Database Migration**: Added `V0_3__Link_candidate_to_citizen.sql` to add a foreign key constraint from `candidate(gid)` to `citizen(gid)`.
    *   **Persistence Layer**: Updated `CandidateEntity` to replace the `gid` String with a `@ManyToOne` relationship to `CitizenEntity`, joined on the `gid` column.
    *   **Service Layer**: Updated `ElectionService` to resolve `CitizenEntity` by GID when creating candidates.
    *   **Repository Layer**: Updated `VoteRepository` JPQL query to use the new relationship path `v.candidate.citizen.gid`.
    *   **Controller Layer**: Updated `ElectionController` to handle the entity-to-DTO conversion with the new relationship.

2. **Verification**:
    *   Successfully ran `./gradlew compileJava`.
    *   Verified the creation of the migration file and the code changes in `CandidateEntity`, `ElectionService`, `ElectionController`, and `VoteRepository`.

3. **Specification**:
    *   **Model version**: gemini-2.0-flash-exp
    *   **Agent version**: Jules 2024.1
    *   **Date**: 2026-05-20

#### `**feat: implement request logging and global exception handling**`

1. **Changes Implemented**:
    *   **Global Exception Handling**: Created `GlobalExceptionHandler` with `@RestControllerAdvice` to handle exceptions and return a standard JSON response with a `message` field and appropriate HTTP status codes (404 for `NoSuchElementException`, 400 for `IllegalArgumentException`, 500 for others).
    *   **Request Logging**: Implemented `RequestLoggerFilter` as a `OncePerRequestFilter` to log the HTTP method, request URI, and response status for every incoming request.

2. **Verification**:
    *   Verified the creation and content of the new classes.
    *   Project compiles and tests run (as confirmed in subsequent steps).

3. **Specification**:
    *   **Model version**: gemini-2.0-flash-exp
    *   **Agent version**: Junie 2024.1
    *   **Date**: 2026-04-27

#### `**feat: implement jwt security and all api endpoints**`

1. **Changes Implemented**:
    *   **Persistence Layer**: Added `spring-boot-starter-data-jpa` and implemented entities (`CitizenEntity`, `ElectionEntity`, `CandidateEntity`, `VoteEntity`) and repositories.
    *   **Database Migrations**: Created Flyway migrations for all entities.
    *   **JWT Security**: Implemented `JwtTokenUtil`, `JwtRequestFilter`, and `SecurityConfig` using `jjwt`. Protected all endpoints.
    *   **Password Hashing**: Implemented `BCryptPasswordEncoder` to hash passwords before storage.
    *   **Auth Endpoints**: Added `/auth/login`, `/auth/register`, and `/auth/me` to OpenAPI spec and implemented them in `AuthController`.
    *   **Service Layer**: Implemented business logic for `Citizen`, `Election`, `Vote`, and `Auth` management. Optimized vote result calculation using JPQL count queries.
    *   **REST Controllers**: Implemented `CitizenController`, `ElectionController`, `VoteController`, and `AuthController`. Fixed `updateCitizens` to perform partial updates correctly. Removed `password` field from `Citizen` DTO to avoid exposure.

#### `**feat: add ADMIN/USER role and implement RBAC**`

1. **Changes Implemented**:
    *   **Database Migration**: Added `role` column to `citizen` table with default value 'USER' via Flyway.
    *   **API Specification**: Updated `openapi.yml` to include `Role` enum and added `role` field to `Citizen` model (omitted from `CreateCitizen` per user request).
    *   **Persistence Layer**: Updated `CitizenEntity` and added `Role` enum.
    *   **Security & RBAC**:
        *   Enhanced JWT to include user roles as claims.
        *   Updated `JwtRequestFilter` to extract roles and set Spring Security authorities (with `ROLE_` prefix).
        *   Configured `SecurityConfig` to restrict access to `/citizens` and `/elections` management to `ADMIN` only.
    *   **Controller Updates**: Modified `CitizenController` and `AuthController` to default new citizens to `USER` role.

2. **Verification**:
    *   Verified successful code generation with `./gradlew openApiGenerate`.
    *   Confirmed compilation with `./gradlew compileJava`.
    *   Manually verified all modified files for correct implementation of role mapping and security logic.

3. **Specification**:
    *   **Model version**: gemini-2.0-flash-exp
    *   **Agent version**: Junie 2024.1
    *   **Date**: 2026-05-18

#### `**feat: replace voter identifier in vote by blind signatures**`

1. **Changes Implemented**:
    *   **API Specification**: Updated `openapi.yml` to include blind signature support.
        *   Added `BlindSignaturePublicKey`, `BlindSignatureRequest`, and `BlindSignatureResponse` schemas.
        *   Added `GET /elections/{electionId}/blind-signature/public-key` and `POST /elections/{electionId}/blind-signature/sign` endpoints.
    *   **Database Migration**: Added `V0_4__Add_blind_signature_support.sql` to replace `voter_id` in the `vote` table with `message` and `signature` fields, and added tables for RSA key pairs and tracking blind signature requests.
    *   **Persistence Layer**:
        *   Updated `VoteEntity` to use `message` and `signature` instead of `voter`.
        *   Added `RsaKeyPairEntity` and `BlindSignatureRequestEntity`.
        *   Added `RsaKeyPairRepository` and `BlindSignatureRequestRepository`.
    *   **Service Layer**:
        *   Implemented `BlindSignatureService` for RSA key generation, blind signing, and signature verification.
        *   Updated `VoteService` to verify blind signatures and prevent duplicate votes using the same message.
        *   Updated `ElectionService` to generate an RSA key pair for each new election.
    *   **Security & Authentication**:
        *   Implemented a new "Blind" authentication scheme (Authorization: Blind <electionId>:<message>:<signature>).
        *   Added `BlindAuthenticationToken` to hold anonymous voter credentials.
        *   Updated `JwtRequestFilter` to verify blind signatures and authorize anonymous voters for specific elections.
    *   **Controller Layer**:
        *   Updated `VoteController` to retrieve voter `message` and `signature` from the `BlindAuthenticationToken` and validate matching `electionId`.
        *   Updated `ElectionController` to implement the new blind signature endpoints.

2. **Verification**:
    *   Verified code generation with `./gradlew openApiGenerate`.
    *   Confirmed successful compilation with `./gradlew compileJava`.

3. **Specification**:
    *   **Model version**: gemini-2.0-flash-exp
    *   **Agent version**: Jules 2024.1
    *   **Date**: 2026-05-20
