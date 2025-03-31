package com.att.tdp.popcorn_palace.exception;

/**
 * Exception thrown when trying to create or update showtime when overlapping with another showtime
 * This exception results in an HTTP 409 CONFLICT response.
 */
public class ShowtimesOverlappingException extends RuntimeException {
    public ShowtimesOverlappingException(String message) {
        super(message);
    }
}