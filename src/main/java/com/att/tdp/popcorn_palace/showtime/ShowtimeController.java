package com.att.tdp.popcorn_palace.showtime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    @GetMapping("/{showtimeId}")
    public Optional<Showtime> getShowtimeById(@PathVariable Long showtimeId) {
        return showtimeService.getShowtimeById(showtimeId);
    }

    @PostMapping
    public ResponseEntity<Showtime> addShowtime(@RequestBody Showtime showtime) {
        // Check for overlapping showtimes before saving
        if (showtimeService.hasOverlappingShowtimes(showtime.getMovieId(), showtime.getStartTime(), showtime.getEndTime(), showtime.getTheater())) {
            return ResponseEntity.badRequest().body(null); // Return an error if there are overlapping showtimes
        }
        Showtime createdShowtime = showtimeService.addShowtime(showtime);
        return ResponseEntity.ok(createdShowtime);
    }

    @PostMapping("/update/{showtimeId}")
    public ResponseEntity<Showtime> updateShowtime(@PathVariable Long showtimeId, @RequestBody Showtime updatedShowtime) {
        if (showtimeService.hasOverlappingShowtimes(updatedShowtime.getMovieId(), updatedShowtime.getStartTime(), updatedShowtime.getEndTime(), updatedShowtime.getTheater())) {
            return ResponseEntity.badRequest().body(null); // Return an error if there are overlapping showtimes
        }
        Showtime updated = showtimeService.updateShowtime(showtimeId, updatedShowtime);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{showtimeId}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long showtimeId) {
        showtimeService.deleteShowtime(showtimeId);
        return ResponseEntity.noContent().build();
    }
}
