package com.att.tdp.popcorn_palace.booking;

import com.att.tdp.popcorn_palace.exception.SeatAlreadyBookedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    public BookingResponse bookTicket(BookingRequest bookingRequest) {
        // Check if seat is already booked
        int existingBookings = bookingRepository.countActiveBookingsForShowtimeSeat(
                bookingRequest.getShowtimeId(),
                bookingRequest.getSeatNumber()
        );

        if (existingBookings > 0) {
            throw new SeatAlreadyBookedException(String.format(
                    "Seat %d is already booked for showtime %d",
                    bookingRequest.getSeatNumber(),
                    bookingRequest.getShowtimeId()
            ));
        }

        // Create and save booking
        Booking booking = new Booking(
                bookingRequest.getUserId(),
                bookingRequest.getShowtimeId(),
                bookingRequest.getSeatNumber()
        );

        Booking savedBooking = bookingRepository.save(booking);

        // Return booking response
        return new BookingResponse(savedBooking.getBookingId());
    }
}