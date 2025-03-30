package com.att.tdp.popcorn_palace.booking;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Bookings")
public class Booking {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID bookingId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Long showtimeId;

    @Column(nullable = false)
    private Integer seatNumber;

    // Constructors
    public Booking() {}

    public Booking(UUID userId, Long showtimeId, Integer seatNumber) {
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seatNumber = seatNumber;
    }

    // Getters and Setters
    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Long getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Long showtimeId) {
        this.showtimeId = showtimeId;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }
}
