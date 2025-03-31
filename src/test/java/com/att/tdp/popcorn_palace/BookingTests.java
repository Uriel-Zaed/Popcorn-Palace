package com.att.tdp.popcorn_palace;

import com.att.tdp.popcorn_palace.booking.Booking;
import com.att.tdp.popcorn_palace.booking.BookingRepository;
import com.att.tdp.popcorn_palace.booking.BookingRequest;
import com.att.tdp.popcorn_palace.booking.BookingResponse;
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
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Test
    public void testBookTicket() throws Exception {
        // First create a showtime
        ShowtimeRequestDTO showtimeDTO = new ShowtimeRequestDTO(1L, "Theater 1",
                LocalDateTime.of(2025, 5, 15, 18, 0),
                LocalDateTime.of(2025, 5, 15, 20, 0), 12.99);

        String showtimeResponseContent = mockMvc.perform(post("/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(showtimeDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Showtime savedShowtime = objectMapper.readValue(showtimeResponseContent, Showtime.class);
        Long showtimeId = savedShowtime.getId();

        // Create a booking request
        UUID userId = UUID.randomUUID();
        BookingRequest bookingRequest = new BookingRequest(showtimeId, 15, userId);

        // Book a ticket
        String bookingResponseContent = mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId", notNullValue()))
                .andReturn().getResponse().getContentAsString();

        BookingResponse bookingResponse = objectMapper.readValue(bookingResponseContent, BookingResponse.class);
        UUID bookingId = bookingResponse.getBookingId();

        // Verify booking exists in database
        Optional<Booking> savedBooking = bookingRepository.findById(bookingId);
        assertTrue(savedBooking.isPresent());
        assertEquals(showtimeId, savedBooking.get().getShowtimeId());
        assertEquals(15, savedBooking.get().getSeatNumber());
        assertEquals(userId, savedBooking.get().getUserId());
    }

    @Test
    public void testAttemptToBookAlreadyBookedSeat() throws Exception {
        // First create a showtime
        ShowtimeRequestDTO showtimeDTO = new ShowtimeRequestDTO(2L, "Theater 2",
                LocalDateTime.of(2025, 5, 16, 14, 0),
                LocalDateTime.of(2025, 5, 16, 16, 0), 14.99);

        String showtimeResponseContent = mockMvc.perform(post("/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(showtimeDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Showtime savedShowtime = objectMapper.readValue(showtimeResponseContent, Showtime.class);
        Long showtimeId = savedShowtime.getId();

        // Create a booking request
        UUID userId1 = UUID.randomUUID();
        BookingRequest bookingRequest1 = new BookingRequest(showtimeId, 20, userId1);

        // Book a ticket
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest1)))
                .andExpect(status().isOk());

        // Try to book the same seat
        UUID userId2 = UUID.randomUUID();
        BookingRequest bookingRequest2 = new BookingRequest(showtimeId, 20, userId2);

        // This should fail
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest2)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testBookTicketWithNonExistentShowtime() throws Exception {
        // Create a booking request with non-existent showtime
        UUID userId = UUID.randomUUID();
        BookingRequest bookingRequest = new BookingRequest(9999L, 25, userId);

        // This should fail
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testBookTicketWithInvalidSeatNumber() throws Exception {
        // First create a showtime
        ShowtimeRequestDTO showtimeDTO = new ShowtimeRequestDTO(3L, "Theater 3",
                LocalDateTime.of(2025, 5, 17, 10, 0),
                LocalDateTime.of(2025, 5, 17, 12, 0), 9.99);

        String showtimeResponseContent = mockMvc.perform(post("/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(showtimeDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Showtime savedShowtime = objectMapper.readValue(showtimeResponseContent, Showtime.class);
        Long showtimeId = savedShowtime.getId();

        // Create a booking request with invalid (negative) seat number
        UUID userId = UUID.randomUUID();
        BookingRequest bookingRequest = new BookingRequest(showtimeId, -5, userId);

        // This should fail
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBookTicketWithMissingUserId() throws Exception {
        // First create a showtime
        ShowtimeRequestDTO showtimeDTO = new ShowtimeRequestDTO(4L, "Theater 4",
                LocalDateTime.of(2025, 5, 18, 20, 0),
                LocalDateTime.of(2025, 5, 18, 22, 0), 15.99);

        String showtimeResponseContent = mockMvc.perform(post("/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(showtimeDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Showtime savedShowtime = objectMapper.readValue(showtimeResponseContent, Showtime.class);
        Long showtimeId = savedShowtime.getId();

        // Create a booking request with null userId
        BookingRequest bookingRequest = new BookingRequest(showtimeId, 30, null);

        // This should fail
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBookMultipleSeatsForSameUser() throws Exception {
        // First create a showtime
        ShowtimeRequestDTO showtimeDTO = new ShowtimeRequestDTO(5L, "Theater 5",
                LocalDateTime.of(2025, 5, 19, 15, 0),
                LocalDateTime.of(2025, 5, 19, 17, 0), 11.99);

        String showtimeResponseContent = mockMvc.perform(post("/showtimes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(showtimeDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Showtime savedShowtime = objectMapper.readValue(showtimeResponseContent, Showtime.class);
        Long showtimeId = savedShowtime.getId();

        // Create booking requests for multiple seats
        UUID userId = UUID.randomUUID();
        BookingRequest bookingRequest1 = new BookingRequest(showtimeId, 1, userId);
        BookingRequest bookingRequest2 = new BookingRequest(showtimeId, 2, userId);

        // Book first seat
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest1)))
                .andExpect(status().isOk());

        // Book second seat
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest2)))
                .andExpect(status().isOk());

        // Verify both bookings exist
        long bookingsCount = bookingRepository.findAll().stream()
                .filter(b -> b.getUserId().equals(userId) && b.getShowtimeId().equals(showtimeId))
                .count();

        assertEquals(2, bookingsCount);
    }

    @Test
    public void testBookTicketWithMalformedRequest() throws Exception {
        // Send a malformed JSON request
        String malformedJson = "{\"showtimeId\": \"not-a-number\", \"seatNumber\": 5, \"userId\": \"not-a-uuid\"}";

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isBadRequest());
    }
}