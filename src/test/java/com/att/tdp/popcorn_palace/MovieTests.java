package com.att.tdp.popcorn_palace;

import com.att.tdp.popcorn_palace.movie.Movie;
import com.att.tdp.popcorn_palace.movie.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class MovieTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MovieRepository movieRepository;


    @Test
    public void testGetAllMovies() throws Exception {
        // Execute and verify
        mockMvc.perform(get("/movies/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    public void testAddAndDeleteMovie() throws Exception {
        // Add a new movie
        Movie newMovie = new Movie("Test Integration Movie", 125, "Thriller", 8.2f, 2023);

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMovie)))
                .andExpect(status().isOk());

        // Verify movie was added to database
        List<Movie> movies = movieRepository.findAll();
        assertTrue(movies.stream().anyMatch(m -> m.getTitle().equals("Test Integration Movie")));

        // Delete the movie
        mockMvc.perform(delete("/movies/Test Integration Movie"))
                .andExpect(status().isOk());

        // Verify movie was deleted from database
        assertFalse(movieRepository.findByTitle("Test Integration Movie").isPresent());
    }

    @Test
    public void testUpdateMovie() throws Exception {
        // Update an existing movie
        Movie updatedMovie = new Movie("Inception", 148, "Sci-Fi/Thriller", 9.0f, 2010);

        mockMvc.perform(post("/movies/update/Inception")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedMovie)))
                .andExpect(status().isOk());

        // Verify movie was updated in database
        Movie movie = movieRepository.findByTitle("Inception").orElseThrow();
        assertEquals("Sci-Fi/Thriller", movie.getGenre());
        assertEquals(9.0f, movie.getRating());
    }

    @Test
    public void testAddDuplicateMovie() throws Exception {
        // Try to add a movie with existing title
        Movie duplicateMovie = new Movie("Inception", 130, "Sci-Fi", 8.5f, 2010);

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateMovie)))
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.message", containsString("already exists")));
    }

    @Test
    public void testUpdateNonExistentMovie() throws Exception {
        // Try to update a movie that doesn't exist
        Movie nonExistentMovie = new Movie("Non Existent Movie", 120, "Drama", 7.0f, 2023);

        mockMvc.perform(post("/movies/update/Non Existent Movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nonExistentMovie)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteNonExistentMovie() throws Exception {
        // Try to delete a movie that doesn't exist
        mockMvc.perform(delete("/movies/Non Existent Movie"))
                .andExpect(status().isNotFound());
    }
}