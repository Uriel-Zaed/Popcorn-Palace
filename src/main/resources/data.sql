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