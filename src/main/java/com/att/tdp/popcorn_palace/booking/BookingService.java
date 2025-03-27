package com.att.tdp.popcorn_palace.booking;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(Long showtimeId, int seatNumber, UUID userId) {
        Booking booking = new Booking(showtimeId, seatNumber, userId);
        return bookingRepository.save(booking);
    }
}
