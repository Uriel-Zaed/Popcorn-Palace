CREATE TABLE IF NOT EXISTS movie (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) UNIQUE,
    genre VARCHAR(100),
    duration INTEGER,
    rating NUMERIC(3,1),
    releaseYear INTEGER
);