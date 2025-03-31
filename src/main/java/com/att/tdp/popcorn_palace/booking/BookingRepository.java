package com.att.tdp.popcorn_palace.booking;

import com.att.tdp.popcorn_palace.showtime.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    Optional<Booking> findBySeatNumberAndShowtime(int seatNumber, Showtime showtime);
}