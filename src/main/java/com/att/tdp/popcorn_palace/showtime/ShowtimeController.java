package com.att.tdp.popcorn_palace.showtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.att.tdp.popcorn_palace.exception.ShowtimesOverlappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {
    private static final Logger logger = LoggerFactory.getLogger(ShowtimeService.class);

    private final ShowtimeService showtimeService;

    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    @GetMapping("/{showtimeId}")
    public ResponseEntity<Optional<Showtime>> getShowtimeById(@PathVariable Long showtimeId) {
        logger.info("REST request to get showtime by id: {}", showtimeId);
        Optional<Showtime> showtime = showtimeService.getShowtimeById(showtimeId);
        return ResponseEntity.ok(showtime);
    }

    @PostMapping
    public ResponseEntity<Showtime> addShowtime(@RequestBody ShowtimeRequestDTO requestDTO) {
        logger.info("REST request to add showtime");
        Showtime newShowtime = showtimeService.addShowtime(requestDTO);
        return ResponseEntity.ok(newShowtime);
    }

    @PostMapping("/update/{showtimeId}")
    public void updateShowtime(@PathVariable Long showtimeId, @RequestBody ShowtimeRequestDTO requestDTO) {
        logger.info("REST request to update showtime by id: {}", showtimeId);
        showtimeService.updateShowtime(showtimeId, requestDTO);
    }

    @DeleteMapping("/{showtimeId}")
    public void deleteShowtime(@PathVariable Long showtimeId) {
        logger.info("REST request to delete showtime by id: {}", showtimeId);
        showtimeService.deleteShowtime(showtimeId);
    }
}
