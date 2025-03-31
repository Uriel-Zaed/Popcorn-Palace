# Popcorn Palace - Instructions

This document provides detailed instructions on how to set up, build, run, and test the Popcorn Palace application.

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- Java Development Kit (JDK) 21 or later
- Maven 3.8+ 
- Docker and Docker Compose (for running PostgreSQL)
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
    - Username: popcorn-palace
    - Password: popcorn-palace
    - Database name: popcorn-palace
    - Port: 5432
   

2. Verify the database is running:
   ```bash
   docker ps
   ```

   You should see a running container for PostgreSQL.

3. The database schema and initial data will be automatically created when you start the application for the first time, using the ROM and `data.sql` files in the resources directory.

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
   ```bash
   mvn test -Dtest=ShowtimeTests
   ```
    or 
    ```bash
   mvn test -Dtest=MovieTests
   ```
   or
3. ```bash
   mvn test -Dtest=BookingTests
   ```
For testing, the application uses H2 in-memory database instead of PostgreSQL.

### Manual API Testing

You can test the API endpoints using Postman or curl:

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

### Movie API

| API Description | Endpoint | Method | Request Body | Response Status | Response Body |
|-----------------|----------|--------|-------------|-----------------|---------------|
| Get all movies | `/movies/all` | GET | - | 200 OK | Array of movie objects |
| Add a movie | `/movies` | POST | Movie object | 200 OK | Created movie object with ID |
| Update a movie | `/movies/update/{movieTitle}` | POST | Updated movie object | 200 OK | - |
| Delete a movie | `/movies/{movieTitle}` | DELETE | - | 200 OK | - |

### Showtime API

| API Description | Endpoint | Method | Request Body | Response Status | Response Body |
|-----------------|----------|--------|-------------|-----------------|---------------|
| Get showtime by ID | `/showtimes/{showtimeId}` | GET | - | 200 OK | Showtime object |
| Add a showtime | `/showtimes` | POST | Showtime request object | 200 OK | Created showtime object with ID |
| Update a showtime | `/showtimes/update/{showtimeId}` | POST | Updated showtime request object | 200 OK | - |
| Delete a showtime | `/showtimes/{showtimeId}` | DELETE | - | 200 OK | - |

### Booking API

| API Description | Endpoint | Method | Request Body | Response Status | Response Body |
|-----------------|----------|--------|-------------|-----------------|---------------|
| Book a ticket | `/bookings` | POST | Booking request object | 200 OK | Booking confirmation with ID |
