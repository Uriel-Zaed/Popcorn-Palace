package com.att.tdp.popcorn_palace.movie;

import jakarta.persistence.*;

@Entity
@Table(name = "Movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated ID
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    private String genre;
    private Integer duration;
    private Float rating;

    @Column(name = "release_year")
    private Integer releaseYear;

    public Movie() {}

    public Movie(String title, Integer duration, String genre, Float rating, Integer releaseYear) {
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.rating = rating;
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", rating=" + rating +
                ", releaseYear=" + releaseYear +
                '}';
    }

    // Getters and Setters
    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Float getRating() { return rating; }
    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }
}
