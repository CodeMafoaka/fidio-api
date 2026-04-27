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

#### `**feat: implement jwt security and all api endpoints**`

1. **Changes Implemented**:
    *   **Persistence Layer**: Added `spring-boot-starter-data-jpa` and implemented entities (`CitizenEntity`, `ElectionEntity`, `CandidateEntity`, `VoteEntity`) and repositories.
    *   **Database Migrations**: Created Flyway migrations for all entities.
    *   **JWT Security**: Implemented `JwtTokenUtil`, `JwtRequestFilter`, and `SecurityConfig` using `jjwt`. Protected all endpoints.
    *   **Password Hashing**: Implemented `BCryptPasswordEncoder` to hash passwords before storage.
    *   **Auth Endpoints**: Added `/auth/login`, `/auth/register`, and `/auth/me` to OpenAPI spec and implemented them in `AuthController`.
    *   **Service Layer**: Implemented business logic for `Citizen`, `Election`, `Vote`, and `Auth` management. Optimized vote result calculation using JPQL count queries.
    *   **REST Controllers**: Implemented `CitizenController`, `ElectionController`, `VoteController`, and `AuthController`. Fixed `updateCitizens` to perform partial updates correctly. Removed `password` field from `Citizen` DTO to avoid exposure.

2. **Verification**:
    *   Confirmed the project compiles successfully with `./gradlew compileJava`.
    *   Verified that all source files were created in the correct packages.

3. **Specification**:
    *   **Model version**: gemini-2.0-flash-exp
    *   **Agent version**: Junie 2024.1
    *   **Date**: 2026-04-27

#### `**Split Monolithic Database Migration**`

1. **Changes Implemented**:
    *   **Migration Splitting**: Refactored the monolithic `V1_create_all_table.sql` into eight individual Flyway migration files (`V1` to `V8`).
    *   **Standardized Naming**: Applied Flyway naming conventions using double underscores (`V<Version>__<Description>.sql`).
    *   **Granular Commits**: Each table creation was committed individually to maintain a clean and traceable history.

2. **Verification**:
    *   Verified the presence of all individual migration files and the removal of the original monolithic file using `ls`.
    *   Attempted to run tests using `./gradlew test`; confirmed that existing failures are environment-related and not introduced by these changes.

3. **Specification**:
    *   **Model version**: gemini-2.0-flash-exp
    *   **Agent version**: Junie 2024.1
    *   **Date**: 2026-04-27
