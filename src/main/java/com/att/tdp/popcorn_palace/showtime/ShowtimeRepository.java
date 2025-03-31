package com.att.tdp.popcorn_palace.showtime;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Movie entity operations.
 * Provides methods to create, read, update, and delete movie records.
 */
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    Optional<Showtime> findById(Long id);
    void deleteById(Long id);
    List<Showtime> findByTheater(String theater);

}
