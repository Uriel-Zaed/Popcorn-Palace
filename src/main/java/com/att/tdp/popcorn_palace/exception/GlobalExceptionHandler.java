package com.att.tdp.popcorn_palace.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for all controllers
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions related to conflicts
     *
     * @param ex The exception that was thrown
     * @return a ResponseEntity with status CONFLICT (409) and detailed error message.
     */
    @ExceptionHandler(exception = {SeatAlreadyBookedException.class, ShowtimesOverlappingException.class,
            ResourceAlreadyExistsException.class})
    public ResponseEntity<Object> handleConflict(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", ex.getClass());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    /**
     * Handles exceptions related to resource not found
     *
     * @param ex The exception that was thrown
     * @return a ResponseEntity with status NOT_FOUND (404) and detailed error message.
     */
    @ExceptionHandler(exception = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", ex.getClass());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions related to Data Integrity Violation
     *
     * @param ex The exception that was thrown
     * @return a ResponseEntity with status BAD_REQUEST (400) and detailed error message.
     */
    @ExceptionHandler(exception = {DataIntegrityViolationException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> handleBadRequest(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", ex.getClass());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}