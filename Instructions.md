# Popcorn Palace - Instructions

This document provides detailed instructions on how to set up, build, run, and test the Popcorn Palace application.

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- Java Development Kit (JDK) 21 or later
- Maven 3.8+ 
- Docker (for running PostgreSQL)
- Git (for version control)

## Project Setup

Clone the repository:
   ```bash
   git clone "https://github.com/Uriel-Zaed/Popcorn-Palace"
   cd popcorn-palace
   ```

## Database Setup

The application uses PostgreSQL as its database, which can be easily set up using Docker:

1. Start the PostgreSQL database using Docker Compose:
   ```bash
   docker-compose up -d
   ```

   This will start a PostgreSQL instance with the following configuration:
   - **Username**: `popcorn-palace`
   - **Password**: `popcorn-palace`
   - **Database name**: `popcorn-palace`
   - **Port**: `5432`
   

2. Verify the database is running:
   ```bash
   docker ps
   ```

   You should see a running container for PostgreSQL.

3. The database schema and initial data will be automatically created when you start the application for the first time, using the ORM and `data.sql` files in the resources directory.

## Building the Application

1. Build the application using Maven:
   ```bash
   mvn clean install
   ```

    This will compile the code, run the tests, and package the application into a JAR file located in the `target/` directory.

## Running the Application

There are several ways to run the application:

### Method 1: Using Maven

```bash
mvn spring-boot:run
```

### Method 2: Using your IDE

Open the project in your IDE (e.g., IntelliJ IDEA) and run the `PopcornPalaceApplication.java` file.

Once started, the application will be available at: http://localhost:8080

## Testing the Application

### Automated Tests

The project includes automated tests to verify its functionality:

1. Run all tests using Maven:
   ```bash
   mvn test
   ```

2. Run specific test classes:
   <br>
   <br>
   For test Showtimes API:
   ```bash
   mvn test -Dtest=ShowtimeTests
   ```
   For test Movies API:
    ```bash
   mvn test -Dtest=MovieTests
   ```
   For test Bookings API:
   ```bash
   mvn test -Dtest=BookingTests
   ```
Note that for testing, the application uses H2 in-memory database instead of PostgreSQL.

### Manual API Testing

1. You can use swagger at: http://localhost:8080/swagger-ui/index.html#/
For test API endpoints and examine the API's schemas

2. You can test the API endpoints using Postman or curl:

#### Movie API Examples:

1. Get all movies:
   ```bash
   curl -X GET http://localhost:8080/movies/all
   ```

2. Add a movie:
   ```bash
   curl -X POST http://localhost:8080/movies -H "Content-Type: application/json" -d '{
     "title": "New Movie",
     "genre": "Action",
     "duration": 120,
     "rating": 8.5,
     "releaseYear": 2025
   }'
   ```

3. Update a movie:
   ```bash
   curl -X POST http://localhost:8080/movies/update/New%20Movie -H "Content-Type: application/json" -d '{
     "title": "New Movie",
     "genre": "Action/Adventure",
     "duration": 135,
     "rating": 8.7,
     "releaseYear": 2025
   }'
   ```

4. Delete a movie:
   ```bash
   curl -X DELETE http://localhost:8080/movies/New%20Movie
   ```

#### Showtime API Examples:

1. Get showtime by ID:
   ```bash
   curl -X GET http://localhost:8080/showtimes/1
   ```

2. Add a showtime:
   ```bash
   curl -X POST http://localhost:8080/showtimes -H "Content-Type: application/json" -d '{
     "movieId": 1,
     "theater": "Theater 10",
     "startTime": "2025-04-15T18:00:00",
     "endTime": "2025-04-15T20:00:00",
     "price": 12.99
   }'
   ```

3. Update a showtime:
   ```bash
   curl -X POST http://localhost:8080/showtimes/update/1 -H "Content-Type: application/json" -d '{
     "movieId": 1,
     "theater": "Theater 10 Updated",
     "startTime": "2025-04-15T18:30:00",
     "endTime": "2025-04-15T20:30:00",
     "price": 14.99
   }'
   ```

4. Delete a showtime:
   ```bash
   curl -X DELETE http://localhost:8080/showtimes/1
   ```

#### Booking API Examples:

1. Book a ticket:
   ```bash
   curl -X POST http://localhost:8080/bookings -H "Content-Type: application/json" -d '{
     "showtimeId": 1,
     "seatNumber": 20,
     "userId": "84438967-f68f-4fa0-b620-0f08217e76af"
   }'
   ```

## API Documentation

### Movies  APIs

| API Description           | Endpoint               | Request Body                          | Response Status | Response Body |
|---------------------------|------------------------|---------------------------------------|-----------------|---------------|
| Get all movies | GET /movies/all | | 200 OK | [ { "id": 12345, "title": "Sample Movie Title 1", "genre": "Action", "duration": 120, "rating": 8.7, "releaseYear": 2025 }, { "id": 67890, "title": "Sample Movie Title 2", "genre": "Comedy", "duration": 90, "rating": 7.5, "releaseYear": 2024 } ] |
| Add a movie | POST /movies | { "title": "Sample Movie Title", "genre": "Action", "duration": 120, "rating": 8.7, "releaseYear": 2025 } | 200 OK | { "id": 1, "title": "Sample Movie Title", "genre": "Action", "duration": 120, "rating": 8.7, "releaseYear": 2025 }|
| Update a movie | POST /movies/update/{movieTitle} | { "title": "Sample Movie Title", "genre": "Action", "duration": 120, "rating": 8.7, "releaseYear": 2025 } | 200 OK | |
| DELETE /movies/{movieTitle} | | 200 OK | |

### Showtimes APIs

| API Description            | Endpoint                           | Request Body                                                                                                                                      | Response Status | Response Body                                                                                                                                                                                                                                                                   |
|----------------------------|------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|-----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Get showtime by ID | GET /showtimes/{showtimeId} |                                                                                                                                                   | 200 OK | { "id": 1, "price":50.2, "movieId": 1, "theater": "Sample Theater", "startTime": "2025-02-14T11:47:46.125405Z", "endTime": "2025-02-14T14:47:46.125405Z" }                                                                                                                      | | Delete a restaurant        | DELETE /restaurants/{id}           |                                                                              | 204 No Content  |                                                                                                        |
| Add a showtime | POST /showtimes | { "movieId": 1, "price":20.2, "theater": "Sample Theater", "startTime": "2025-02-14T11:47:46.125405Z", "endTime": "2025-02-14T14:47:46.125405Z" } | 200 OK | { "id": 1, "price":50.2,"movieId": 1, "theater": "Sample Theater", "startTime": "2025-02-14T11:47:46.125405Z", "endTime": "2025-02-14T14:47:46.125405Z" }                                                                                                                                    |
| Update a showtime | POST /showtimes/update/{showtimeId}| { "movieId": 1, "price":50.2, "theater": "Sample Theater", "startTime": "2025-02-14T11:47:46.125405Z", "endTime": "2025-02-14T14:47:46.125405Z" } | 200 OK |                                                                                                                                                                                                                                                                                 |
| Delete a showtime | DELETE /showtimes/{showtimeId} |                                                                                                                                                   | 200 OK |                                                                                                                                                                                                                                                                                 |





### bookings APIs

| API Description           | Endpoint       | Request Body                                     | Response Status | Response Body                                                                                                                                          |
|---------------------------|----------------|--------------------------------------------------|-----------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| Book a ticket | POST /bookings | { "showtimeId": 1, "seatNumber": 15 , userId:"84438967-f68f-4fa0-b620-0f08217e76af"} | 200 OK | { "bookingId":"d1a6423b-4469-4b00-8c5f-e3cfc42eacae" }                                                                                                 |

