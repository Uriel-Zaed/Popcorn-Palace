package com.att.tdp.popcorn_palace;

import com.att.tdp.popcorn_palace.movie.MovieRepository;
import com.att.tdp.popcorn_palace.showtime.Showtime;
import com.att.tdp.popcorn_palace.showtime.ShowtimeRepository;
import com.att.tdp.popcorn_palace.showtime.ShowtimeRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShowtimeTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void testGetShowtimeById() throws Exception {
        // First create a showtime to retrieve
        ShowtimeRequestDTO newShowtime = new ShowtimeRequestDTO(1L, "Theater 1",
                LocalDateTime.of(2025, 4, 15, 18, 0),
                LocalDateTime.of(2025, 4, 15, 20, 0), 12.99);

        String responseContent = mockMvc.perform(post("/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newShowtime)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Showtime savedShowtime = objectMapper.readValue(responseContent, Showtime.class);
        Long showtimeId = savedShowtime.getId();

        // Now retrieve it and verify
        mockMvc.perform(get("/showtimes/" + showtimeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(showtimeId.intValue())))
                .andExpect(jsonPath("$.movieId", is(1)))
                .andExpect(jsonPath("$.theater", is("Theater 1")));
    }

    @Test
    public void testAddShowtime() throws Exception {
        ShowtimeRequestDTO newShowtime = new ShowtimeRequestDTO(2L, "Theater 2",
                LocalDateTime.of(2025, 4, 16, 14, 0),
                LocalDateTime.of(2025, 4, 16, 16, 0), 14.99);

        mockMvc.perform(post("/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newShowtime)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", is(2)))
                .andExpect(jsonPath("$.theater", is("Theater 2")))
                .andExpect(jsonPath("$.price", is(14.99)));

        // Verify the showtime was saved in the database
        boolean found = showtimeRepository.findAll().stream()
                .anyMatch(s -> s.getTheater().equals("Theater 2") &&
                        s.getMovieId().equals(2L));
        assertTrue(found);
    }

    @Test
    public void testUpdateShowtime() throws Exception {
        // First create a showtime to update
        ShowtimeRequestDTO newShowtime = new ShowtimeRequestDTO(2L, "Theater 3",
                LocalDateTime.of(2025, 4, 17, 10, 0),
                LocalDateTime.of(2025, 4, 17, 12, 0), 9.99);

        String responseContent = mockMvc.perform(post("/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newShowtime)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Showtime savedShowtime = objectMapper.readValue(responseContent, Showtime.class);
        Long showtimeId = savedShowtime.getId();

        // Update the showtime
        ShowtimeRequestDTO updatedShowtime = new ShowtimeRequestDTO(2L, "Theater 3 Updated",
                LocalDateTime.of(2025, 4, 17, 11, 0),
                LocalDateTime.of(2025, 4, 17, 13, 0), 10.99);

        mockMvc.perform(post("/showtimes/update/" + showtimeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedShowtime)))
                .andExpect(status().isOk());

        // Verify the showtime was updated
        Optional<Showtime> updated = showtimeRepository.findById(showtimeId);
        assertTrue(updated.isPresent());
        assertEquals("Theater 3 Updated", updated.get().getTheater());
        assertEquals(10.99, updated.get().getPrice());
    }

    @Test
    public void testDeleteShowtime() throws Exception {
        // First create a showtime to delete
        ShowtimeRequestDTO newShowtime = new ShowtimeRequestDTO(1L, "Theater 4",
                LocalDateTime.of(2025, 4, 18, 20, 0),
                LocalDateTime.of(2025, 4, 18, 22, 0), 15.99);

        String responseContent = mockMvc.perform(post("/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newShowtime)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Showtime savedShowtime = objectMapper.readValue(responseContent, Showtime.class);
        Long showtimeId = savedShowtime.getId();

        // Delete the showtime
        mockMvc.perform(delete("/showtimes/" + showtimeId))
                .andExpect(status().isOk());

        // Verify the showtime was deleted
        assertFalse(showtimeRepository.findById(showtimeId).isPresent());
    }

    @Test
    public void testOverlappingShowtimes() throws Exception {
        // First add a showtime
        ShowtimeRequestDTO firstShowtime = new ShowtimeRequestDTO(1L, "Theater 5",
                LocalDateTime.of(2025, 4, 19, 15, 0),
                LocalDateTime.of(2025, 4, 19, 17, 0), 11.99);

        mockMvc.perform(post("/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firstShowtime)))
                .andExpect(status().isOk());

        // Try to add an overlapping showtime
        ShowtimeRequestDTO overlappingShowtime = new ShowtimeRequestDTO(2L, "Theater 5",
                LocalDateTime.of(2025, 4, 19, 16, 0), // Overlaps with first showtime
                LocalDateTime.of(2025, 4, 19, 18, 0), 11.99);

        mockMvc.perform(post("/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(overlappingShowtime)))
                .andExpect(status().isConflict()); // Expecting conflict response
    }

    @Test
    public void testNonOverlappingShowtimesInDifferentTheaters() throws Exception {
        // First add a showtime in Theater 6
        ShowtimeRequestDTO firstShowtime = new ShowtimeRequestDTO(2L, "Theater 6",
                LocalDateTime.of(2025, 4, 20, 15, 0),
                LocalDateTime.of(2025, 4, 20, 17, 0), 11.99);

        mockMvc.perform(post("/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firstShowtime)))
                .andExpect(status().isOk());

        // Add a showtime at the same time but in a different theater
        ShowtimeRequestDTO secondShowtime = new ShowtimeRequestDTO(2L, "Theater 7",
                LocalDateTime.of(2025, 4, 20, 15, 0),
                LocalDateTime.of(2025, 4, 20, 17, 0), 11.99);

        // This should succeed because it's in a different theater
        mockMvc.perform(post("/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(secondShowtime)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetNonExistentShowtime() throws Exception {
        // Try to get a showtime that doesn't exist
        mockMvc.perform(get("/showtimes/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateNonExistentShowtime() throws Exception {
        // Try to update a showtime that doesn't exist
        ShowtimeRequestDTO nonExistentShowtime = new ShowtimeRequestDTO(3L, "Theater 7",
                LocalDateTime.of(2025, 4, 21, 15, 0),
                LocalDateTime.of(2025, 4, 21, 17, 0), 11.99);

        mockMvc.perform(post("/showtimes/update/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nonExistentShowtime)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testInvalidShowtimeData() throws Exception {
        // Test with end time before start time
        ShowtimeRequestDTO invalidTimeShowtime = new ShowtimeRequestDTO(1L, "Theater 9",
                LocalDateTime.of(2025, 4, 23, 17, 0),
                LocalDateTime.of(2025, 4, 23, 15, 0), // End before start
                11.99);

        mockMvc.perform(post("/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTimeShowtime)))
                .andExpect(status().isBadRequest());
    }
}