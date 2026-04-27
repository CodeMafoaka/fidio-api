### AI Usage Log

#### `**Fixed Flyway Auto-Migration on Startup**`

1. **Changes Implemented**:
    *   **Spring Boot Downgrade**: Downgraded Spring Boot version in `build.gradle` from `4.0.6` (invalid/unreleased version) to `3.2.5`. This ensured that `FlywayAutoConfiguration` is properly loaded by Spring Boot's auto-configuration mechanism.
    *   **Flyway 10+ PostgreSQL Support**: Added the `flyway-database-postgresql` dependency which is required for PostgreSQL support in Flyway 10 and above.
    *   **Dependency Version Alignment**: Aligned `flyway-core` and `flyway-database-postgresql` versions to `10.11.0`. This prevents `AbstractMethodError` that can occur when Spring Boot's default dependency management provides an incompatible version of `flyway-core` while a newer database-specific module is added.

2. **Verification**:
    *   Confirmed that `./gradlew bootRun` successfully connects to the database, validates migrations, and starts the application without errors.

3. **Specification**:
    *   **Model version**: gemini-2.0-flash-exp
    *   **Agent version**: Junie 2024.1
    *   **Date**: 2026-04-27

#### `**Created AI Usage Logging Guideline**`

1. **Changes Implemented**:
    *   **Guideline Creation**: Created `.junie/guidelines/ai_usage_logging.md` to document the required format and content for `AI_USAGE.md` updates.
    *   **Version Update**: Updated model and agent versions to more specific identifiers as requested.

2. **Verification**:
    *   Verified file creation and content of `.junie/guidelines/ai_usage_logging.md`.

3. **Specification**:
    *   **Model version**: gemini-2.0-flash-exp
    *   **Agent version**: Junie 2024.1
    *   **Date**: 2026-04-27
