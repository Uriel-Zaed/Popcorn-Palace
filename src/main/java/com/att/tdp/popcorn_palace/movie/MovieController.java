package com.att.tdp.popcorn_palace.movie;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/all")
    public List<Movie> all() {
        return movieService.getAllMovies();
    }

    @PostMapping
    public Movie newMovie(@RequestBody Movie newMovie) {
        return movieService.addMovie(newMovie);
    }

    @PostMapping("/update/{movieTitle}")
    public Movie replaceMovie(@RequestBody Movie newMovie, @PathVariable String movieTitle) {
        return movieService.updateMovie(movieTitle, newMovie);
    }

    @DeleteMapping("/{movieTitle}")
    public void deleteMovie(@PathVariable String movieTitle) {
        movieService.deleteMovie(movieTitle);
    }
}
