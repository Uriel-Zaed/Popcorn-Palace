package com.att.tdp.popcorn_palace.showtime;

import com.att.tdp.popcorn_palace.movie.Movie;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a showtime in the Popcorn Palace system.
 * Contains all relevant information about a showtime.
 */
@Entity
@Table(name = "Showtimes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"start_time", "theater"}))
@JsonPropertyOrder({"id", "movieId", "theater", "startTime", "endTime", "price"})
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;  // Correct field name in your entity

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "movie_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_movie"), nullable = false)
    private Movie movie;

    @Column(nullable = false)
    private String theater;

    private Double price;

    public Showtime() {}

    public Showtime(Movie movie, String theater, LocalDateTime startTime, LocalDateTime endTime, Double price) {
        this.movie = movie;
        this.theater = theater;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }
    public void setMovie (Movie movie) { this.movie = movie; }

    @JsonProperty("movieId")
    public Long getMovieId() { return movie != null ? movie.getId() : null; }

    public String getTheater() {
        return theater;
    }
    public void setTheater(String theater) {
        this.theater = theater;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Showtime{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", movie=" + movie +
                ", theater='" + theater + '\'' +
                ", price=" + price +
                '}';
    }
}
