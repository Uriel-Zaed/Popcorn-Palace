-- Movies table inserts
INSERT INTO Movies (title, genre, duration, rating, release_year)
SELECT 'Inception', 'Sci-Fi', 148, 8.8, 2010 WHERE NOT EXISTS (SELECT 1 FROM Movies WHERE title = 'Inception');
INSERT INTO Movies (title, genre, duration, rating, release_year)
SELECT 'The Dark Knight', 'Action', 152, 9.0, 2008 WHERE NOT EXISTS (SELECT 1 FROM Movies WHERE title = 'The Dark Knight');
INSERT INTO Movies (title, genre, duration, rating, release_year)
SELECT 'Interstellar', 'Sci-Fi', 169, 8.6, 2014 WHERE NOT EXISTS (SELECT 1 FROM Movies WHERE title = 'Interstellar');
INSERT INTO Movies (title, genre, duration, rating, release_year)
SELECT 'The Matrix', 'Sci-Fi', 136, 8.7, 1999 WHERE NOT EXISTS (SELECT 1 FROM Movies WHERE title = 'The Matrix');
INSERT INTO Movies (title, genre, duration, rating, release_year)
SELECT 'The Godfather', 'Crime', 175, 9.2, 1972 WHERE NOT EXISTS (SELECT 1 FROM Movies WHERE title = 'The Godfather');
INSERT INTO Movies (title, genre, duration, rating, release_year)
SELECT 'Pulp Fiction', 'Crime', 154, 8.9, 1994 WHERE NOT EXISTS (SELECT 1 FROM Movies WHERE title = 'Pulp Fiction');
INSERT INTO Movies (title, genre, duration, rating, release_year)
SELECT 'Forrest Gump', 'Drama', 142, 8.8, 1994 WHERE NOT EXISTS (SELECT 1 FROM Movies WHERE title = 'Forrest Gump');
INSERT INTO Movies (title, genre, duration, rating, release_year)
SELECT 'The Shawshank Redemption', 'Drama', 142, 9.3, 1994 WHERE NOT EXISTS (SELECT 1 FROM Movies WHERE title = 'The Shawshank Redemption');
INSERT INTO Movies (title, genre, duration, rating, release_year)
SELECT 'Fight Club', 'Drama', 139, 8.8, 1999 WHERE NOT EXISTS (SELECT 1 FROM Movies WHERE title = 'Fight Club');
INSERT INTO Movies (title, genre, duration, rating, release_year)
SELECT 'The Avengers', 'Action', 143, 8.0, 2012 WHERE NOT EXISTS (SELECT 1 FROM Movies WHERE title = 'The Avengers');

-- Showtimes table inserts
INSERT INTO Showtimes (movie_id, theater, start_time, end_time, price)
SELECT 1, 'Theater 1', '2025-02-14 10:00:00', '2025-02-14 12:30:00', 15.50 WHERE NOT EXISTS (SELECT 1 FROM Showtimes WHERE movie_id = 1 AND theater = 'Theater 1' AND start_time = '2025-02-14 10:00:00');
INSERT INTO Showtimes (movie_id, theater, start_time, end_time, price)
SELECT 2, 'Theater 2', '2025-02-14 11:00:00', '2025-02-14 13:30:00', 18.00 WHERE NOT EXISTS (SELECT 1 FROM Showtimes WHERE movie_id = 2 AND theater = 'Theater 2' AND start_time = '2025-02-14 11:00:00');
INSERT INTO Showtimes (movie_id, theater, start_time, end_time, price)
SELECT 3, 'Theater 3', '2025-02-14 12:00:00', '2025-02-14 14:30:00', 16.00 WHERE NOT EXISTS (SELECT 1 FROM Showtimes WHERE movie_id = 3 AND theater = 'Theater 3' AND start_time = '2025-02-14 12:00:00');
INSERT INTO Showtimes (movie_id, theater, start_time, end_time, price)
SELECT 4, 'Theater 4', '2025-02-14 13:00:00', '2025-02-14 15:30:00', 14.00 WHERE NOT EXISTS (SELECT 1 FROM Showtimes WHERE movie_id = 4 AND theater = 'Theater 4' AND start_time = '2025-02-14 13:00:00');
INSERT INTO Showtimes (movie_id, theater, start_time, end_time, price)
SELECT 5, 'Theater 5', '2025-02-14 14:00:00', '2025-02-14 16:30:00', 17.00 WHERE NOT EXISTS (SELECT 1 FROM Showtimes WHERE movie_id = 5 AND theater = 'Theater 5' AND start_time = '2025-02-14 14:00:00');
INSERT INTO Showtimes (movie_id, theater, start_time, end_time, price)
SELECT 6, 'Theater 6', '2025-02-14 15:00:00', '2025-02-14 17:30:00', 19.00 WHERE NOT EXISTS (SELECT 1 FROM Showtimes WHERE movie_id = 6 AND theater = 'Theater 6' AND start_time = '2025-02-14 15:00:00');
INSERT INTO Showtimes (movie_id, theater, start_time, end_time, price)
SELECT 7, 'Theater 1', '2025-02-14 16:00:00', '2025-02-14 18:30:00', 12.50 WHERE NOT EXISTS (SELECT 1 FROM Showtimes WHERE movie_id = 7 AND theater = 'Theater 1' AND start_time = '2025-02-14 16:00:00');
INSERT INTO Showtimes (movie_id, theater, start_time, end_time, price)
SELECT 8, 'Theater 2', '2025-02-14 17:00:00', '2025-02-14 19:30:00', 13.50 WHERE NOT EXISTS (SELECT 1 FROM Showtimes WHERE movie_id = 8 AND theater = 'Theater 2' AND start_time = '2025-02-14 17:00:00');
INSERT INTO Showtimes (movie_id, theater, start_time, end_time, price)
SELECT 9, 'Theater 3', '2025-02-14 18:00:00', '2025-02-14 20:30:00', 15.00 WHERE NOT EXISTS (SELECT 1 FROM Showtimes WHERE movie_id = 9 AND theater = 'Theater 3' AND start_time = '2025-02-14 18:00:00');

-- Bookings table inserts
INSERT INTO Bookings (booking_id, showtime_id, seat_number, user_id)
SELECT 'd1a6423b-4469-4b00-8c5f-e3cfc42eacae', 1, 15, '84438967-f68f-4fa0-b620-0f08217e76af' WHERE NOT EXISTS (SELECT 1 FROM Bookings WHERE booking_id = 'd1a6423b-4469-4b00-8c5f-e3cfc42eacae');
INSERT INTO Bookings (booking_id, showtime_id, seat_number, user_id)
SELECT 'bb6f9e4a-f9f7-4f3e-bc7f-0f5b7c66f28d', 2, 10, 'f19a62b9-9c32-4666-8b27-dbd8727d91f3' WHERE NOT EXISTS (SELECT 1 FROM Bookings WHERE booking_id = 'bb6f9e4a-f9f7-4f3e-bc7f-0f5b7c66f28d');
INSERT INTO Bookings (booking_id, showtime_id, seat_number, user_id)
SELECT 'c7a8d3f2-1e5b-4b0e-9a3c-2f5e7d8b6a4c', 3, 22, '5f3a8d2e-4b6c-41e7-9f5d-1b3a6c8d2e4f' WHERE NOT EXISTS (SELECT 1 FROM Bookings WHERE booking_id = 'c7a8d3f2-1e5b-4b0e-9a3c-2f5e7d8b6a4c');
INSERT INTO Bookings (booking_id, showtime_id, seat_number, user_id)
SELECT 'e9b1f5a2-3d6c-47b8-9e1f-4a2d7c5b8f3e', 4, 7, '2c6a3d8f-5b1e-42d9-7f3a-9e4b6c2d8f5a' WHERE NOT EXISTS (SELECT 1 FROM Bookings WHERE booking_id = 'e9b1f5a2-3d6c-47b8-9e1f-4a2d7c5b8f3e');
