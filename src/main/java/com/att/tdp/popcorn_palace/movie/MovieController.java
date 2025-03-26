package com.att.tdp.popcorn_palace.movie;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    private final MovieRepository repository;

    public MovieController(MovieRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/movies/all")
    public List<Movie> all() {
        return repository.findAll();
    }

    @PostMapping("/movies")
    public Movie newEmployee(@RequestBody Movie newMovie) {
        return repository.save(newMovie);
    }

    @PostMapping("/movies/update/{movieTitle}")
    public Movie replaceMovie(@RequestBody Movie newMovie, @PathVariable String movieTitle) {
        System.out.println(movieTitle);
        return repository.findByTitle(movieTitle)
                .map(movie -> {
                    movie.setGenre(newMovie.getGenre());
                    movie.setDuration(newMovie.getDuration());
                    movie.setRating(newMovie.getRating());
                    movie.setReleaseYear(newMovie.getReleaseYear());
                    return repository.save(movie);
                })
                .orElseGet(() -> repository.save(newMovie));  // Save newMovie if not found
    }

    @DeleteMapping("/movies/{movieTitle}")
    @Transactional
    public void deleteMovie(@PathVariable String movieTitle) {
        repository.deleteByTitle(movieTitle);
    }
}
