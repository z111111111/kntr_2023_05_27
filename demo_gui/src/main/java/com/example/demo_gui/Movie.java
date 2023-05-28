package com.example.demo_gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Movie {
    private final SimpleLongProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty genre;
    private final SimpleLongProperty duration;
    private final SimpleLongProperty year;

    public Movie(long id, String name, String genre, long duration, long year) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.genre = new SimpleStringProperty(genre);
        this.duration = new SimpleLongProperty(duration);
        this.year = new SimpleLongProperty(year);
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getGenre() {
        return genre.get();
    }

    public SimpleStringProperty genreProperty() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public long getDuration() {
        return duration.get();
    }

    public SimpleLongProperty durationProperty() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration.set(duration);
    }

    public long getYear() {
        return year.get();
    }

    public SimpleLongProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }


}