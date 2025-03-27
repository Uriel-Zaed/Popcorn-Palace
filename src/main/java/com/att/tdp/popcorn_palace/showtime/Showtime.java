package com.att.tdp.popcorn_palace.showtime;

import com.att.tdp.popcorn_palace.movie.Movie;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;  // Correct field name in your entity

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Long movieId;

    private String theater;
    private Double price;

    public Showtime() {}

    public Showtime(Long movieId, String theater, LocalDateTime startTime, LocalDateTime endTime, Double price) {
        this.movieId = movieId;
        this.theater = theater;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public String getTheater() {
        return theater;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
