package com.att.tdp.popcorn_palace.exception;

public class ShowtimesOverlappingException extends RuntimeException {
    public ShowtimesOverlappingException(String message) {
        super(message);
    }
}