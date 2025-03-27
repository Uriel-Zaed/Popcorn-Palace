package com.att.tdp.popcorn_palace.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.showtimeId = :showtimeId AND b.seatNumber = :seatNumber AND b.isActive = true")
    int countActiveBookingsForShowtimeSeat(Long showtimeId, Integer seatNumber);
}