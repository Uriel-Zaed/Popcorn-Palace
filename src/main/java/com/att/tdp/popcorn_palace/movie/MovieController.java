package com.att.tdp.popcorn_palace.movie;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class MovieController {

    private final MovieRepository repository;

    public MovieController(MovieRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/movie")
    public List<Movie> all() {
        return repository.findAll();
    }

    @PostMapping("/movie")
    public Movie newEmployee(@RequestBody Movie newMovie) {
        return repository.save(newMovie);
    }

    @PutMapping("/movie/{id}")
    public Movie replaceMovie(@RequestBody Movie newMovie, @PathVariable Integer id) {

        return repository.findById(id)
                .map(movie -> {
                    movie.setTitle(newMovie.getTitle());
                    movie.setGenre(newMovie.getGenre());
                    movie.setDuration(newMovie.getDuration());
                    movie.setRating(newMovie.getRating());
                    movie.setRelease_year(newMovie.getRelease_year());
                    return repository.save(movie);
                })
                .orElseGet(() -> {
                    return repository.save(newMovie);
                });
    }

    @DeleteMapping("/movie/{id}")
    public void deleteMovie(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
