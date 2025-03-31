package com.att.tdp.popcorn_palace.movie;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

/**
 * Repository interface for Movie entity operations.
 * Provides methods to create, read, update, and delete movie records.
 */
public interface MovieRepository extends ListCrudRepository<Movie,Long> {

    /**
     * Finds a movie by its title.
     *
     * @param title The title of the movie to find
     * @return An Optional containing the movie if found, empty otherwise
     */
    Optional<Movie> findByTitle(String title);

    /**
     * Deletes a movie by its title.
     *
     * @param title The title of the movie to delete
     */
    void deleteByTitle(String title);
}
