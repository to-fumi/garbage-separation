# Garbage Separation API

A Kotlin/Ktor REST API for managing garbage separation and disposal information. This application helps users properly categorize and dispose of different types of waste with multi-language support.

## Overview

This API provides endpoints for browsing garbage disposal information and administrative functions for managing the garbage database. It supports multiple languages (Japanese and English) and includes role-based authentication for administrative operations.

## Features

- **Multi-language Support**: Japanese (ja) and English (en) language support
- **User Authentication**: JWT-based authentication with role-based access control
- **Garbage Management**: Comprehensive CRUD operations for garbage items
- **Category System**: Support for 8 different garbage categories
- **Admin Panel**: Administrative endpoints for managing users and garbage data
- **Database Integration**: PostgreSQL with Exposed ORM and Flyway migrations
- **Dependency Injection**: Koin for clean dependency management

## Garbage Categories

The system supports the following garbage categories:

- **Burnable** (`burnable`) - Items that can be incinerated
- **Non-burnable** (`non-burnable`) - Items that cannot be burned
- **Oversized** (`oversized`) - Large items requiring special disposal
- **Recyclable** (`recyclable`) - Items that can be recycled
- **Hazardous** (`hazardous`) - Dangerous materials requiring special handling
- **Not Accepted** (`not-accepted`) - Items not accepted by regular waste collection
- **Home Appliance Recycling** (`home-appliance-recycling`) - Electronics and appliances
- **Direct Delivery** (`direct-delivery`) - Items requiring direct delivery to facilities

## Building & Running

To build or run the project, use one of the following tasks:

| Task                                    | Description                                                          |
| -----------------------------------------|---------------------------------------------------------------------- |
| `./gradlew test`                        | Run the tests                                                        |
| `./gradlew build`                       | Build everything                                                     |
| `./gradlew buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `./gradlew buildImage`                  | Build the docker image to use with the fat JAR                       |
| `./gradlew publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `./gradlew run`                         | Run the server                                                       |
| `./gradlew runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```


## API Endpoints

### Public Endpoints

#### Get Garbage Items
```
GET /api/garbages?lang={language}&limit={limit}&offset={offset}
```
- **Description**: Retrieve a list of garbage items
- **Parameters**:
  - `lang` (optional): Language code (`ja` or `en`, defaults to `ja`)
  - `limit` (optional): Number of items to return
  - `offset` (optional): Number of items to skip
- **Response**: Array of garbage items with disposal information

### Authentication Endpoints

#### Login
```
POST /auth/login
```
- **Description**: Authenticate user and receive JWT token
- **Body**: `LoginRequest` with credentials
- **Response**: JWT token for authenticated requests

#### Get Current User
```
GET /auth/me
```
- **Description**: Get current authenticated user information
- **Headers**: `Authorization: Bearer {token}`
- **Response**: Current user details

### Administrative Endpoints

All administrative endpoints require JWT authentication and admin role.

#### Register New User
```
POST /auth/register
```
- **Description**: Register a new user (admin only)
- **Headers**: `Authorization: Bearer {token}`
- **Body**: `RegisterRequest` with user details
- **Response**: 201 Created

#### Get Garbage Items (Admin)
```
GET /admin/garbages?lang={language}&category={category}&limit={limit}&offset={offset}
```
- **Description**: Retrieve garbage items with optional category filtering
- **Headers**: `Authorization: Bearer {token}`
- **Parameters**:
  - `lang` (optional): Language code
  - `category` (optional): Filter by garbage category
  - `limit` (optional): Number of items to return
  - `offset` (optional): Number of items to skip

#### Get Specific Garbage Item
```
GET /admin/garbages/{id}?lang={language}
```
- **Description**: Retrieve a specific garbage item by ID
- **Headers**: `Authorization: Bearer {token}`
- **Parameters**:
  - `id`: Garbage item ID
  - `lang` (optional): Language code

#### Create Garbage Item
```
POST /admin/garbages
```
- **Description**: Create a new garbage item
- **Headers**: `Authorization: Bearer {token}`
- **Body**: `UpsertGarbageRequest` with item details
- **Response**: 204 No Content

#### Update Garbage Item
```
PUT /admin/garbages/{id}
```
- **Description**: Update an existing garbage item
- **Headers**: `Authorization: Bearer {token}`
- **Parameters**: `id` - Garbage item ID
- **Body**: `UpsertGarbageRequest` with updated details
- **Response**: 200 OK

#### Delete Garbage Item
```
DELETE /admin/garbages/{id}
```
- **Description**: Delete a garbage item
- **Headers**: `Authorization: Bearer {token}`
- **Parameters**: `id` - Garbage item ID
- **Response**: 204 No Content

## Technology Stack

- **Language**: Kotlin
- **Framework**: Ktor
- **Server**: Netty
- **Database**: PostgreSQL
- **ORM**: Exposed
- **Migrations**: Flyway
- **Authentication**: JWT
- **Dependency Injection**: Koin
- **Serialization**: Kotlinx Serialization
- **Password Hashing**: BCrypt
- **Connection Pooling**: HikariCP

## Project Structure

```
src/main/kotlin/com/toyokawa/
├── Application.kt                 # Main application entry point
├── plugins/                       # Ktor plugins configuration
│   ├── Authentication.kt          # JWT authentication setup
│   ├── ContentNegotiation.kt      # JSON serialization
│   ├── CORS.kt                    # Cross-origin resource sharing
│   ├── Databases.kt               # Database configuration
│   ├── Koin.kt                    # Dependency injection
│   ├── RequestValidation.kt       # Request validation
│   ├── Routing.kt                 # Route configuration
│   └── StatusPages.kt             # Error handling
├── routes/                        # API route definitions
│   ├── AuthRoutes.kt              # Authentication endpoints
│   ├── GarbageRoutes.kt           # Garbage management endpoints
│   ├── extensions/                # Route helper extensions
│   ├── requests/                  # Request DTOs
│   └── responses/                 # Response DTOs
└── data/                          # Data layer
    ├── domain/
    │   ├── dto/                   # Data transfer objects
    │   └── tables/                # Database table definitions
    ├── repositories/              # Data access layer
    └── exceptions/                # Custom exceptions
```

## Setup and Configuration

### Prerequisites

- Java 11 or higher
- PostgreSQL database
- Gradle (wrapper included)

### Environment Variables

Create a `.env` file in the project root with the following variables:

```env
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/garbage_db
DB_USER=your_db_user
DB_PASSWORD=your_db_password

# JWT Configuration
JWT_SECRET=your_jwt_secret_key
JWT_ISSUER=garbage-api
JWT_AUDIENCE=garbage-api-users
JWT_REALM=garbage-api

# Server Configuration
PORT=8080
```

### Database Setup

1. Create a PostgreSQL database
2. Update the database connection details in your `.env` file
3. Run the application - Flyway will automatically handle database migrations

## Building & Running

### Development

```bash
# Run the application in development mode
./gradlew run

# Run tests
./gradlew test

# Build the project
./gradlew build
```

### Production

```bash
# Build executable JAR
./gradlew buildFatJar

# Build Docker image
./gradlew buildImage

# Run with Docker
./gradlew runDocker
```

### Gradle Tasks

| Task                                    | Description                                                          |
| -----------------------------------------|---------------------------------------------------------------------- |
| `./gradlew test`                        | Run the tests                                                        |
| `./gradlew build`                       | Build everything                                                     |
| `./gradlew buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `./gradlew buildImage`                  | Build the docker image to use with the fat JAR                       |
| `./gradlew publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `./gradlew run`                         | Run the server                                                       |
| `./gradlew runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

## Usage Examples

### Get All Garbage Items (Public)

```bash
curl -X GET "http://localhost:8080/api/garbages?lang=en&limit=10&offset=0"
```

### Login

```bash
curl -X POST "http://localhost:8080/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "password"}'
```

### Create Garbage Item (Admin)

```bash
curl -X POST "http://localhost:8080/admin/garbages" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "name": "Plastic Bottle",
    "disposalNotes": "Remove cap and label before disposal",
    "category": "recyclable",
    "languageCode": "en"
  }'
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Resources

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Exposed Documentation](https://github.com/JetBrains/Exposed)
- [Koin Documentation](https://insert-koin.io/)
- [Kotlin Documentation](https://kotlinlang.org/docs/)