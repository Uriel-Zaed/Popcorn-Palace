package com.att.tdp.popcorn_palace.movie;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    public Movie addMovie(Movie newMovie) {
        return repository.save(newMovie);
    }

    public Movie updateMovie(String movieTitle, Movie newMovie) {
        Optional<Movie> existingMovie = repository.findByTitle(movieTitle);
        if (existingMovie.isPresent()) {
            Movie movie = existingMovie.get();
            movie.setGenre(newMovie.getGenre());
            movie.setDuration(newMovie.getDuration());
            movie.setRating(newMovie.getRating());
            movie.setReleaseYear(newMovie.getReleaseYear());
            return repository.save(movie);
        } else {
            return repository.save(newMovie); // If not found, save the new movie
        }
    }

    @Transactional
    public void deleteMovie(String movieTitle) {
        repository.deleteByTitle(movieTitle);
    }
}
