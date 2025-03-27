package com.att.tdp.popcorn_palace.showtime;

import com.att.tdp.popcorn_palace.exception.ShowtimesOverlappingException;
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
    public Showtime addShowtime(@RequestBody Showtime showtime) {
        // Check for overlapping showtimes before saving
        if (showtimeService.hasOverlappingShowtimes(showtime.getStartTime(), showtime.getEndTime(), showtime.getTheater())) {
            throw new ShowtimesOverlappingException("Overlapping showtimes"); // Return an error if there are overlapping showtimes
        }
        return showtimeService.addShowtime(showtime);
    }

    @PostMapping("/update/{showtimeId}")
    public Showtime updateShowtime(@PathVariable Long showtimeId, @RequestBody Showtime updatedShowtime) {
        if (showtimeService.hasOverlappingShowtimes(updatedShowtime.getStartTime(), updatedShowtime.getEndTime(), updatedShowtime.getTheater())) {
            throw new ShowtimesOverlappingException("Overlapping showtimes");  // Return an error if there are overlapping showtimes
        }
        return showtimeService.updateShowtime(showtimeId, updatedShowtime);
    }

    @DeleteMapping("/{showtimeId}")
    public void deleteShowtime(@PathVariable Long showtimeId) {
        showtimeService.deleteShowtime(showtimeId);
    }
}
