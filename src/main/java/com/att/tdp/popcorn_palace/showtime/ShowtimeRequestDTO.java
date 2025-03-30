package com.att.tdp.popcorn_palace.showtime;

import java.time.LocalDateTime;

public class ShowtimeRequestDTO {
    private Long movieId;
    private String theater;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double price;

    public ShowtimeRequestDTO() { }

    public ShowtimeRequestDTO(Long movieId, String theater, LocalDateTime startTime, LocalDateTime endTime, Double price) {
        this.movieId = movieId;
        this.theater = theater;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getTheater() { return theater; }
    public void setTheater(String theater) { this.theater = theater; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}