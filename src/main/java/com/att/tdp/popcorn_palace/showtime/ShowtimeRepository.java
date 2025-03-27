package com.att.tdp.popcorn_palace.showtime;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    Optional<Showtime> findById(Long id);
    void deleteById(Long id);
}
