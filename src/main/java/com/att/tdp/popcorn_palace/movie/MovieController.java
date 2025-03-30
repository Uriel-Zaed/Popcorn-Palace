package com.att.tdp.popcorn_palace.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        logger.info("REST request to get all movies");
        List<Movie> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie newMovie) {
        logger.info("REST request to add a new movie: {}", newMovie.getTitle());
        Movie result = movieService.addMovie(newMovie);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update/{movieTitle}")
    public void updateMovie(@RequestBody Movie newMovie, @PathVariable String movieTitle) {
        logger.info("REST request to update movie: {}", movieTitle);
        movieService.updateMovie(movieTitle, newMovie);
    }

    @DeleteMapping("/{movieTitle}")
    public void deleteMovie(@PathVariable String movieTitle) {
        logger.info("REST request to delete movie: {}", movieTitle);
        movieService.deleteMovie(movieTitle);
    }
}
