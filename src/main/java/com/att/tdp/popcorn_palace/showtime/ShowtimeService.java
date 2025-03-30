package com.att.tdp.popcorn_palace.showtime;

import com.att.tdp.popcorn_palace.exception.ResourceNotFoundException;
import com.att.tdp.popcorn_palace.exception.ShowtimesOverlappingException;
import com.att.tdp.popcorn_palace.movie.Movie;
import com.att.tdp.popcorn_palace.movie.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShowtimeService {
    private static final Logger logger = LoggerFactory.getLogger(ShowtimeService.class);

    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;

    public ShowtimeService(ShowtimeRepository showtimeRepository, MovieRepository movieRepository) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
    }

    public Optional<Showtime> getShowtimeById(Long id) {
        logger.info("Getting showtime by id: {}", id);
        return showtimeRepository.findById(id);
    }

    @Transactional
    public Showtime addShowtime(ShowtimeRequestDTO requestDTO) {
        if (hasOverlappingShowtimes(requestDTO.getStartTime(), requestDTO.getEndTime(), requestDTO.getTheater())) {
            throw new ShowtimesOverlappingException("Overlapping showtime");  // Return an error if there are overlapping showtimes
        }
        Optional<Movie> movie = movieRepository.findById(requestDTO.getMovieId());
        if (movie.isEmpty()) {
            logger.error("Movie {} not found", requestDTO.getMovieId());
            throw new ResourceNotFoundException("Movie " + requestDTO.getMovieId() + " not found");
        }
        Showtime showtime = new Showtime(movie.get(), requestDTO.getTheater(),
                requestDTO.getStartTime(), requestDTO.getEndTime(), requestDTO.getPrice());
        Showtime showtimeResult = showtimeRepository.save(showtime);
        logger.info("Adding showtime id: {}", showtimeResult.getId());
        return showtimeResult;
    }

    @Transactional
    public void updateShowtime(Long id, ShowtimeRequestDTO requestDTO) {
        logger.info("Updating the showtime id: {}", id);

        Optional<Showtime> existingShowtime = showtimeRepository.findById(id);
        if (existingShowtime.isEmpty()) {
            logger.error("Showtime was not found with id: {}", id);
            throw new ResourceNotFoundException("Showtime was not found with id: " + id);
        }
        if (hasOverlappingShowtimes(requestDTO.getStartTime(), requestDTO.getEndTime(), requestDTO.getTheater())) {
            throw new ShowtimesOverlappingException("Overlapping showtime");  // Return an error if there are overlapping showtimes
        }
        Optional<Movie> movie = movieRepository.findById(requestDTO.getMovieId());
        if (movie.isEmpty()) {
            logger.error("Movie {} not found", requestDTO.getMovieId());
            throw new ResourceNotFoundException("Movie " + requestDTO.getMovieId() + " not found");
        }

        Showtime updatedShowtime = existingShowtime.get();

        updatedShowtime.setMovie(movie.get());
        updatedShowtime.setTheater(requestDTO.getTheater());
        updatedShowtime.setStartTime(requestDTO.getStartTime());
        updatedShowtime.setEndTime(requestDTO.getEndTime());
        updatedShowtime.setPrice(requestDTO.getPrice());

        Showtime savedShowtime = showtimeRepository.save(updatedShowtime);
        logger.info("Showtime {} updated", savedShowtime.getId());
    }

    @Transactional
    public void deleteShowtime(Long id) {
        logger.info("Deleting showtime with id: {}", id);

        // Verify movie exists before attempting to delete
        if (showtimeRepository.findById(id).isEmpty()) {
            logger.error("Cannot delete. showtime not found with id: {}", id);
            throw new ResourceNotFoundException("Showtime not found with id: " + id);
        }
        showtimeRepository.deleteById(id);
        logger.info("Showtime deleted successfully: {}", id);
    }

    public boolean hasOverlappingShowtimes(LocalDateTime startTime, LocalDateTime endTime, String theater) {
        logger.info("Checking if there is an overlap in theater: {}at start time: {} end time: {}", theater, startTime.toString(), endTime.toString());
        List<Showtime> showtimes = showtimeRepository.findByTheater(theater);
        for (Showtime showtime : showtimes) {
            // Check for all possible overlap scenarios:
            // 1. New showtime starts during existing showtime
            // 2. New showtime ends during existing showtime
            // 3. New showtime completely contains existing showtime
            // 4. New showtime is completely contained by existing showtime
            if ((startTime.isBefore(showtime.getEndTime()) && startTime.isAfter(showtime.getStartTime())) ||
                    (endTime.isAfter(showtime.getStartTime()) && endTime.isBefore(showtime.getEndTime())) ||
                    (startTime.isBefore(showtime.getStartTime()) && endTime.isAfter(showtime.getEndTime())) ||
                    (startTime.isEqual(showtime.getStartTime()) || endTime.isEqual(showtime.getEndTime()))) {
                logger.error("Found an overlap in theater: {} at start time: {} end time: {}", theater, startTime.toString(), endTime.toString());
                return true;
            }
        }
        logger.info("There is no overlap in theater: {}", theater);
        return false;
    }
}
