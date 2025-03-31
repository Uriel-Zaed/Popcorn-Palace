package com.att.tdp.popcorn_palace.showtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for managing Showtime resources.
 */
@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {
    private static final Logger logger = LoggerFactory.getLogger(ShowtimeService.class);

    private final ShowtimeService showtimeService;

    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    /**
     * Get /showtimes/{showtimeId} get showtime by id
     *
     * @param showtimeId
     * @return the ResponseEntity with status 200 (OK) and the showtime in the body
     */
    @GetMapping("/{showtimeId}")
    public ResponseEntity<Optional<Showtime>> getShowtimeById(@PathVariable Long showtimeId) {
        logger.info("REST request to get showtime by id: {}", showtimeId);
        Optional<Showtime> showtime = showtimeService.getShowtimeById(showtimeId);
        return ResponseEntity.ok(showtime);
    }

    /**
     * POST /showtimes : Create a new showtime
     *
     * @requestDTO ShowtimeRequestDTO to represent the request
     * @return the ResponseEntity with status 200 and the new showtime in the body
     */
    @PostMapping
    public ResponseEntity<Showtime> addShowtime(@RequestBody ShowtimeRequestDTO requestDTO) {
        logger.info("REST request to add showtime");
        Showtime newShowtime = showtimeService.addShowtime(requestDTO);
        return ResponseEntity.ok(newShowtime);
    }

    /**
     * POST /showtimes/update/{showtimeID} update a showtime by id
     *
     * @param showtimeId
     * @param requestDTO ShowtimeRequestDTO to represent the request
     */
    @PostMapping("/update/{showtimeId}")
    public void updateShowtime(@PathVariable Long showtimeId, @RequestBody ShowtimeRequestDTO requestDTO) {
        logger.info("REST request to update showtime by id: {}", showtimeId);
        showtimeService.updateShowtime(showtimeId, requestDTO);
    }

    /**
     * DELETE /showtimes/{showtimeID} deletes a showtime by id
     *
     * @param showtimeId
     */
    @DeleteMapping("/{showtimeId}")
    public void deleteShowtime(@PathVariable Long showtimeId) {
        logger.info("REST request to delete showtime by id: {}", showtimeId);
        showtimeService.deleteShowtime(showtimeId);
    }
}
