package com.att.tdp.popcorn_palace.booking;


import java.util.UUID;

/**
 * A class representing a booking response
 */
public class BookingResponse {
    private UUID bookingId;

    public BookingResponse() {}

    public BookingResponse(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public UUID getBookingId() {
        return bookingId;
    }
    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public String toString() {
        return "BookingResponse{" +
                "bookingId=" + bookingId +
                '}';
    }
}