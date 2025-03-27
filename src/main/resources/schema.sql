CREATE TABLE IF NOT EXISTS movie (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) UNIQUE,
    genre VARCHAR(100),
    duration INTEGER,
    rating NUMERIC(3,1),
    releaseYear INTEGER
);

CREATE TABLE IF NOT EXISTS showtime (
    id SERIAL PRIMARY KEY,
    movie_id INTEGER NOT NULL,
    theater VARCHAR(255) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    price NUMERIC(5,2) NOT NULL,
    CONSTRAINT fk_movie FOREIGN KEY (movie_id) REFERENCES movie(id) ON DELETE CASCADE
);

ALTER TABLE showtime
ADD CONSTRAINT unique_showtime UNIQUE (movie_id, start_time, theater);