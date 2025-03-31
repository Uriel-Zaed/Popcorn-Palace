package com.att.tdp.popcorn_palace.booking;

import com.att.tdp.popcorn_palace.showtime.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for booking entity operations.
 * Provides methods to create and read booking records.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    Optional<Booking> findBySeatNumberAndShowtime(int seatNumber, Showtime showtime);

}