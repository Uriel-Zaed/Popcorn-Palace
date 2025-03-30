package com.att.tdp.popcorn_palace.movie;

import com.att.tdp.popcorn_palace.exception.ResourceAlreadyExistsException;
import com.att.tdp.popcorn_palace.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public List<Movie> getAllMovies() {
        logger.info("Fetching all movies");
        return repository.findAll();
    }

    public Movie addMovie(Movie newMovie) {
        logger.info("Adding new movie: {}", newMovie.getTitle());

        Optional<Movie> existingMovie = repository.findByTitle(newMovie.getTitle());
        if (existingMovie.isPresent()) {
            logger.error("Movie with title '{}' already exists", newMovie.getTitle());
            throw new ResourceAlreadyExistsException("Movie with title '" + newMovie.getTitle() + "' already exists");
        }
        Movie savedMovie = repository.save(newMovie);
        logger.info("Movie added successfully with ID: {}", savedMovie.getId());
        return savedMovie;
    }

    public void updateMovie(String movieTitle, Movie newMovie) {
        logger.info("Updating movie with title: {}", movieTitle);

        Optional<Movie> existingMovie = repository.findByTitle(movieTitle);
        if (existingMovie.isEmpty()) {
            logger.error("Movie not found with title: {}", movieTitle);
            throw new ResourceNotFoundException("Movie not found with title: " + movieTitle);
        }
        if (!movieTitle.equals(newMovie.getTitle())) {
            existingMovie = repository.findByTitle(newMovie.getTitle());
            if (existingMovie.isPresent()) {
                logger.error("Cannot update movie. Title '{}' already in use", newMovie.getTitle());
                throw new ResourceAlreadyExistsException("Movie with title '" + newMovie.getTitle() + "' already exists");
            }
        }

        Movie movie = existingMovie.get();
        movie.setGenre(newMovie.getGenre());
        movie.setDuration(newMovie.getDuration());
        movie.setRating(newMovie.getRating());
        movie.setReleaseYear(newMovie.getReleaseYear());

        Movie updatedMovie = repository.save(movie);
        logger.info("Movie updated successfully: {}", updatedMovie.getTitle());
    }

    @Transactional
    public void deleteMovie(String movieTitle) {
        logger.info("Deleting movie with title: {}", movieTitle);

        // Verify movie exists before attempting to delete
        if (repository.findByTitle(movieTitle).isEmpty()) {
            logger.error("Cannot delete. Movie not found with title: {}", movieTitle);
            throw new ResourceNotFoundException("Movie not found with title: " + movieTitle);
        }
        repository.deleteByTitle(movieTitle);
        logger.info("Movie deleted successfully: {}", movieTitle);
    }
}
