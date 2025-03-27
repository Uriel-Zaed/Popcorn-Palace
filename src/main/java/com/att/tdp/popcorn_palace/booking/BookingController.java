package com.att.tdp.popcorn_palace.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> bookTicket(@RequestBody BookingRequest bookingRequest) {
        // Create booking
        Booking booking = bookingService.createBooking(
                bookingRequest.getShowtimeId(),
                bookingRequest.getSeatNumber(),
                UUID.fromString(bookingRequest.getUserId())
        );

        // Return booking ID as response
        BookingResponse bookingResponse = new BookingResponse(booking.getBookingId().toString());
        return ResponseEntity.ok(bookingResponse);
    }

    // Booking request body
    private static class BookingRequest {
        private Long showtimeId;
        private int seatNumber;
        private String userId;

        // Getters and Setters
        public Long getShowtimeId() {
            return showtimeId;
        }

        public void setShowtimeId(Long showtimeId) {
            this.showtimeId = showtimeId;
        }

        public int getSeatNumber() {
            return seatNumber;
        }

        public void setSeatNumber(int seatNumber) {
            this.seatNumber = seatNumber;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }


    // Booking response body
    private static class BookingResponse {
        private String bookingId;

        public BookingResponse(String bookingId) {
            this.bookingId = bookingId;
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }
    }
}
