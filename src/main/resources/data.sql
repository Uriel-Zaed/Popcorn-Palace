INSERT INTO movie (title, genre, duration, rating, release_year)
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

INSERT INTO booking (booking_id, showtime_id, seat_number, user_id, is_active) VALUES
('d1a6423b-4469-4b00-8c5f-e3cfc42eacae', 1, 15, '84438967-f68f-4fa0-b620-0f08217e76af', true),
('bb6f9e4a-f9f7-4f3e-bc7f-0f5b7c66f28d', 2, 10, 'f19a62b9-9c32-4666-8b27-dbd8727d91f3', true),
('c7a8d3f2-1e5b-4b0e-9a3c-2f5e7d8b6a4c', 3, 22, '5f3a8d2e-4b6c-41e7-9f5d-1b3a6c8d2e4f', true),
('e9b1f5a2-3d6c-47b8-9e1f-4a2d7c5b8f3e', 4, 7, '2c6a3d8f-5b1e-42d9-7f3a-9e4b6c2d8f5a', true)
ON CONFLICT (booking_id) DO NOTHING;