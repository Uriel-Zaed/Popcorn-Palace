package com.att.tdp.popcorn_palace.movie;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Movie {

    private @Id
    @GeneratedValue Integer id;
    private String title;
    private String genre;
    private Integer duration;
    private Float rating;
    private Integer release_year;

    public Movie() {}

    public Movie(String title, Integer duration, String genre, Float rating, Integer release_year) {
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.rating = rating;
        this.release_year = release_year;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", rating=" + rating +
                ", release_year=" + release_year +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public Float getRating() {
        return rating;
    }

    public Integer getRelease_year() {
        return release_year;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setRelease_year(Integer release_year) {
        this.release_year = release_year;
    }
}
