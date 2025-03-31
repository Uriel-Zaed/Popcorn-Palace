package com.att.tdp.popcorn_palace.booking;

import java.util.UUID;

public class BookingRequest {
    private Long showtimeId;
    private Integer seatNumber;
    private UUID userId;

    public BookingRequest() {}

    public BookingRequest(Long showtimeId, Integer seatNumber, UUID userId) {
        this.showtimeId = showtimeId;
        this.seatNumber = seatNumber;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "{" +
                "showtimeId=" + showtimeId +
                ", seatNumber=" + seatNumber +
                ", userId=" + userId +
                '}';
    }
}