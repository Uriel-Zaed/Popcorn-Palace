package com.att.tdp.popcorn_palace.booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Booking resources.
 */
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * POST /bookings : book a ticket
     *
     * @param bookingRequest BookingRequest represent the booking request
     * @return the ResponseEntity with status 200 and the new movie in the body
     */
    @PostMapping
    public ResponseEntity<BookingResponse> bookTicket(@RequestBody BookingRequest bookingRequest) {
        logger.info("REST request to book a ticket: {}", bookingRequest);
        BookingResponse result = bookingService.bookTicket(bookingRequest);
        return ResponseEntity.ok(result);
    }
}