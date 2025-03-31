package com.att.tdp.popcorn_palace.movie;

import com.att.tdp.popcorn_palace.exception.ResourceAlreadyExistsException;
import com.att.tdp.popcorn_palace.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class that manages movie-related business operations.
 * Handles CRUD operations for Movie entities with appropriate validations.
 */
@Service
public class MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all movies from the database.
     *
     * @return List of all movies
     */
    public List<Movie> getAllMovies() {
        logger.info("Fetching all movies");
        return repository.findAll();
    }

    /**
     * Adds a new movie to the database.
     *
     * @param newMovie The movie to add
     * @return The saved movie with generated ID
     * @throws ResourceAlreadyExistsException if a movie with the same title already exists
     * @throws IllegalArgumentException if a field of movie not valid
     */
    public Movie addMovie(Movie newMovie) {
        logger.info("Adding new movie: {}", newMovie.getTitle());

        movieValidation(newMovie);

        Optional<Movie> existingMovie = repository.findByTitle(newMovie.getTitle());
        if (existingMovie.isPresent()) {
            logger.error("Movie with title '{}' already exists", newMovie.getTitle());
            throw new ResourceAlreadyExistsException("Movie with title '" + newMovie.getTitle() + "' already exists");
        }
        Movie savedMovie = repository.save(newMovie);
        logger.info("Movie added successfully with ID: {}", savedMovie.getId());
        return savedMovie;
    }

    /**
     * Updates an existing movie.
     *
     * @param movieTitle The title of the movie to update
     * @param newMovie The new movie details
     * @throws ResourceNotFoundException if the movie to update doesn't exist
     * @throws ResourceAlreadyExistsException if trying to update title to one that already exists
     * @throws IllegalArgumentException if a field of movie not valid
     */
    public void updateMovie(String movieTitle, Movie newMovie) {
        logger.info("Updating movie with title: {}", movieTitle);

        movieValidation(newMovie);

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

    /**
     * Deletes a movie by its title.
     *
     * @param movieTitle The title of the movie to delete
     * @throws ResourceNotFoundException if the movie to delete doesn't exist
     */
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

    /**
     * validate movie details
     *
     * @param movie
     * @throws IllegalArgumentException if any of the fields not valid
     */
    public void movieValidation(Movie movie) {
        if (movie.getTitle() == null || movie.getTitle().isEmpty()) {
            logger.error("Movie title is empty");
            throw new IllegalArgumentException("Movie title is empty");
        }
        if (movie.getDuration() < 1) {
            logger.error("Movie duration has to be greater than 0");
            throw new IllegalArgumentException("Movie duration has to be greater than 0");
        }
        if (movie.getRating() > 10 || movie.getRating() < 0) {
            logger.error("Movie rating has to be between 0 and 10");
            throw new IllegalArgumentException("Movie rating has to be between 0 and 10");
        }
    }
}
