INSERT INTO movie (title, genre, duration, rating, releaseYear)
VALUES
    ('Inception', 'Sci-Fi', 148, 8.8, 2010),
    ('The Dark Knight', 'Action', 152, 9.0, 2008),
    ('Interstellar', 'Sci-Fi', 169, 8.6, 2014),
    ('The Matrix', 'Sci-Fi', 136, 8.7, 1999),
    ('The Godfather', 'Crime', 175, 9.2, 1972),
    ('Pulp Fiction', 'Crime', 154, 8.9, 1994),
    ('Forrest Gump', 'Drama', 142, 8.8, 1994),
    ('The Shawshank Redemption', 'Drama', 142, 9.3, 1994),
    ('Fight Club', 'Drama', 139, 8.8, 1999),
    ('The Avengers', 'Action', 143, 8.0, 2012)
ON CONFLICT (title) DO NOTHING;

INSERT INTO showtime (movie_id, theater, start_time, end_time, price)
VALUES
    (1, 'Cineplex 1', '2025-02-14 10:00:00', '2025-02-14 12:30:00', 15.50),
    (2, 'Cineplex 2', '2025-02-14 11:00:00', '2025-02-14 13:30:00', 18.00),
    (3, 'Cineplex 3', '2025-02-14 12:00:00', '2025-02-14 14:30:00', 16.00),
    (4, 'Cineplex 4', '2025-02-14 13:00:00', '2025-02-14 15:30:00', 14.00),
    (5, 'Cineplex 5', '2025-02-14 14:00:00', '2025-02-14 16:30:00', 17.00),
    (6, 'Cineplex 6', '2025-02-14 15:00:00', '2025-02-14 17:30:00', 19.00),
    (7, 'Cineplex 1', '2025-02-14 16:00:00', '2025-02-14 18:30:00', 12.50),
    (8, 'Cineplex 2', '2025-02-14 17:00:00', '2025-02-14 19:30:00', 13.50),
    (9, 'Cineplex 3', '2025-02-14 18:00:00', '2025-02-14 20:30:00', 15.00),
    (10, 'Cineplex 4', '2025-02-14 19:00:00', '2025-02-14 21:30:00', 16.50)
ON CONFLICT (movie_id, start_time, theater) DO NOTHING;