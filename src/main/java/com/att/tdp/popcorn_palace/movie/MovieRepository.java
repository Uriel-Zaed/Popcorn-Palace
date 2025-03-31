package com.att.tdp.popcorn_palace.movie;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

/**
 * Repository interface for Movie entity operations.
 * Provides methods to create, read, update, and delete movie records.
 */
public interface MovieRepository extends ListCrudRepository<Movie,Long> {

    Optional<Movie> findByTitle(String title);
    void deleteByTitle(String title);

}
