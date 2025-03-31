package com.att.tdp.popcorn_palace.booking;

import com.att.tdp.popcorn_palace.exception.ResourceNotFoundException;
import com.att.tdp.popcorn_palace.exception.SeatAlreadyBookedException;
import com.att.tdp.popcorn_palace.showtime.Showtime;
import com.att.tdp.popcorn_palace.showtime.ShowtimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class that manages booking-related business operations.
 * Handles CRUD operations for Booking entities with appropriate validations.
 */
@Service
public class BookingService {
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final ShowtimeRepository showtimeRepository;

    public BookingService(BookingRepository bookingRepository, ShowtimeRepository showtimeRepository) {
        this.bookingRepository = bookingRepository;
        this.showtimeRepository = showtimeRepository;
    }

    /**
     *  Book a ticket by validating and adding to the database
     *
     * @param bookingRequest BookingRequest to represent the request
     * @return BookingResponse that represent the response
     * @throws ResourceNotFoundException if showtime wasn't found
     * @throws IllegalArgumentException if the seat number was not positive number
     * @throws SeatAlreadyBookedException if the seat already taken
     */
    @Transactional
    public BookingResponse bookTicket(BookingRequest bookingRequest) {
        logger.info("Booking request: {}", bookingRequest);

        Optional<Showtime> showtime = showtimeRepository.findById(bookingRequest.getShowtimeId());
        if (showtime.isEmpty()) {
            logger.error("Showtime {} not found", bookingRequest.getShowtimeId());
            throw new ResourceNotFoundException("Showtime " + bookingRequest.getShowtimeId() + " not found");
        }

        if (bookingRequest.getSeatNumber() <= 0) {
            logger.error("Seat number {} not valid", bookingRequest.getSeatNumber());
            throw new IllegalArgumentException("Seat number " + bookingRequest.getSeatNumber() + " not valid");
        }

        Optional<Booking> booking = bookingRepository.findBySeatNumberAndShowtime(
                bookingRequest.getSeatNumber(), showtime.get());

        if (booking.isPresent()) {
            logger.error("Seat {} is already booked for showtime {}", bookingRequest.getSeatNumber(),
                    bookingRequest.getShowtimeId());
            throw new SeatAlreadyBookedException(String.format(
                    "Seat %d is already booked for showtime %d", bookingRequest.getSeatNumber(),
                    bookingRequest.getShowtimeId()
            ));
        }

        Booking newBooking = new Booking(
                bookingRequest.getUserId(),
                showtime.get(),
                bookingRequest.getSeatNumber()
        );

        Booking savedBooking = bookingRepository.save(newBooking);
        logger.info("Booking saved: {}", savedBooking);
        return new BookingResponse(savedBooking.getBookingId());
    }
}