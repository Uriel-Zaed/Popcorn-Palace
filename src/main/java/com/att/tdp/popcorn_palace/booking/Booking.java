package com.att.tdp.popcorn_palace.booking;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID bookingId;

    @JoinColumn(name = "showtime_id", referencedColumnName = "id")
    private Long showtimeId;

    private int seatNumber;
    private UUID userId;

    // Constructors, Getters, Setters
    public Booking() {}

    public Booking(Long showtimeId, int seatNumber, UUID userId) {
        this.showtimeId = showtimeId;
        this.seatNumber = seatNumber;
        this.userId = userId;
        this.bookingId = UUID.randomUUID();
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
