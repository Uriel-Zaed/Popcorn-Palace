CREATE TABLE IF NOT EXISTS movie (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) UNIQUE,
    genre VARCHAR(100),
    duration INTEGER,
    rating NUMERIC(3,1),
    release_year INTEGER
);

CREATE TABLE IF NOT EXISTS showtime (
    id SERIAL PRIMARY KEY,
    movie_id INTEGER NOT NULL,
    theater VARCHAR(255) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    price NUMERIC(5,2) NOT NULL,
    CONSTRAINT fk_movie FOREIGN KEY (movie_id) REFERENCES movie(id) ON DELETE CASCADE,
    CONSTRAINT unique_showtime_index UNIQUE (start_time, theater)
);

CREATE TABLE IF NOT EXISTS booking (
    booking_id UUID PRIMARY KEY,
    showtime_id INTEGER NOT NULL,
    seat_number INT NOT NULL,
    user_id UUID NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_showtime FOREIGN KEY (showtime_id) REFERENCES showtime (id) ON DELETE CASCADE,
    CONSTRAINT unique_showtime_seat UNIQUE (showtime_id, seat_number)
);