package com.att.tdp.popcorn_palace.showtime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;

    public ShowtimeService(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
    }

    public Optional<Showtime> getShowtimeById(Long id) {
        return showtimeRepository.findById(id);
    }

    @Transactional
    public Showtime addShowtime(Showtime showtime) {
        return showtimeRepository.save(showtime);
    }

    @Transactional
    public Showtime updateShowtime(Long id, Showtime updatedShowtime) {
        Optional<Showtime> existingShowtime = showtimeRepository.findById(id);
        if (existingShowtime.isPresent()) {
            Showtime showtime = existingShowtime.get();
            showtime.setMovieId(updatedShowtime.getMovieId());
            showtime.setTheater(updatedShowtime.getTheater());
            showtime.setStartTime(updatedShowtime.getStartTime());
            showtime.setEndTime(updatedShowtime.getEndTime());
            showtime.setPrice(updatedShowtime.getPrice());
            return showtimeRepository.save(showtime);
        }
        return null;
    }

    @Transactional
    public void deleteShowtime(Long id) {
        showtimeRepository.deleteById(id);
    }

    // Additional method to check if there are overlapping showtimes in the same theater
    public boolean hasOverlappingShowtimes(LocalDateTime startTime, LocalDateTime endTime, String theater) {
        List<Showtime> showtimes = showtimeRepository.findAll();
        for (Showtime showtime : showtimes) {
            if (showtime.getTheater().equals(theater)) {
                if ((startTime.isBefore(showtime.getEndTime()) && startTime.isAfter(showtime.getStartTime())) ||
                        (endTime.isBefore(showtime.getEndTime()) && endTime.isAfter(showtime.getStartTime()))) {
                    return true;
                }
            }
        }
        return false;
    }
}
