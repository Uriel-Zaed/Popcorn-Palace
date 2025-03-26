CREATE TABLE IF NOT EXISTS movie (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100) NOT NULL,
    duration INTEGER NOT NULL,
    rating FLOAT,
    release_year INTEGER NOT NULL
);