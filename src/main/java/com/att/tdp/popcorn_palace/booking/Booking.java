package com.att.tdp.popcorn_palace.booking;

import com.att.tdp.popcorn_palace.movie.Movie;
import com.att.tdp.popcorn_palace.showtime.Showtime;
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

    @JoinColumn(name = "showtime_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_showtime"), nullable = false)
    @ManyToOne
    private Showtime showtime;

    @Column(nullable = false)
    private Integer seatNumber;

    // Constructors
    public Booking() {}

    public Booking(UUID userId, Showtime showtime, Integer seatNumber) {
        this.userId = userId;
        this.showtime = showtime;
        this.seatNumber = seatNumber;
    }

    // Getters and Setters
    public UUID getBookingId() { return bookingId; }
    public void setBookingId(UUID bookingId) { this.bookingId = bookingId; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public Showtime getShowtime() { return showtime; }
    public void setShowtime(Showtime showtime) { this.showtime = showtime; }

    public Long getShowtimeId() { return showtime.getId(); }

    public Integer getSeatNumber() { return seatNumber; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }

    @Override
    public String toString() {
        return "{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", showtime=" + showtime +
                ", seatNumber=" + seatNumber +
                '}';
    }
}
