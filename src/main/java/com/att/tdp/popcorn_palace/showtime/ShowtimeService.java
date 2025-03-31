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

/**
 * Service class that manages showtime-related business operations.
 * Handles CRUD operations for Showtime entities with appropriate validations.
 */
@Service
public class ShowtimeService {
    private static final Logger logger = LoggerFactory.getLogger(ShowtimeService.class);

    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;

    public ShowtimeService(ShowtimeRepository showtimeRepository, MovieRepository movieRepository) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
    }

    /**
     * Retrieve an optional value of showtime by id
     *
     * @param id
     * @return optional value of showtime
     * @throws ResourceNotFoundException if no showtime found with the specific id
     */
    public Optional<Showtime> getShowtimeById(Long id) {
        logger.info("Getting showtime by id: {}", id);
        Optional<Showtime> showtime = showtimeRepository.findById(id);
        if (showtime.isEmpty()) {
            throw new ResourceNotFoundException("Showtime " + id + " not found");
        }
        return showtime;
    }

    /**
     * Adds a new showtime to the database
     *
     * @param requestDTO a formatted class of the showtime request
     * @return new showtime object
     * @throws ShowtimesOverlappingException if 2 showtimes are in the same theater in the same time
     * @throws ResourceNotFoundException if there is no movie id correspond to the request
     */
    public Showtime addShowtime(ShowtimeRequestDTO requestDTO) {
        validateShowtime(requestDTO);

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

    /**
     * Updates a showtime by id
     *
     * @param id
     * @param requestDTO
     * @throws ResourceNotFoundException if the id doesn't exist
     * @throws ShowtimesOverlappingException if the new time overlap other showtime
     * @throws ResourceNotFoundException if the new movie not found
     */
    @Transactional
    public void updateShowtime(Long id, ShowtimeRequestDTO requestDTO) {
        logger.info("Updating the showtime id: {}", id);

        validateShowtime(requestDTO);

        Optional<Showtime> existingShowtime = showtimeRepository.findById(id);
        if (existingShowtime.isEmpty()) {
            logger.error("Showtime was not found with id: {}", id);
            throw new ResourceNotFoundException("Showtime was not found with id: " + id);
        }
        if (hasOverlappingShowtimes(requestDTO.getStartTime(), requestDTO.getEndTime(), requestDTO.getTheater())) {
            throw new ShowtimesOverlappingException("Overlapping showtime");
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

    /**
     * Deletes a showtime by its id.
     *
     * @param id The id of the showtime to delete
     * @throws ResourceNotFoundException if the showtime to delete doesn't exist
     */
    @Transactional
    public void deleteShowtime(Long id) {
        logger.info("Deleting showtime with id: {}", id);

        if (showtimeRepository.findById(id).isEmpty()) {
            logger.error("Cannot delete. showtime not found with id: {}", id);
            throw new ResourceNotFoundException("Showtime not found with id: " + id);
        }

        showtimeRepository.deleteById(id);
        logger.info("Showtime deleted successfully: {}", id);
    }

    /**
     * Check if for a specific showtime has an overlapping with another showtime at the same theater and time
     *
     * @param startTime
     * @param endTime
     * @param theater
     * @return boolean if the showtime overlap or not
     */
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

    private void validateShowtime(ShowtimeRequestDTO requestDTO) {
        if (requestDTO.getEndTime().isBefore(requestDTO.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }
    }
}
