package com.example.demo_gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MovieToSend {
    private final SimpleStringProperty name;
    private final SimpleStringProperty genre;
    private final SimpleIntegerProperty duration;
    private final SimpleIntegerProperty year;

    public MovieToSend( String name, String genre, int duration, int year) {
        this.name = new SimpleStringProperty(name);
        this.genre = new SimpleStringProperty(genre);
        this.duration = new SimpleIntegerProperty(duration);
        this.year = new SimpleIntegerProperty(year);
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

    public int getDuration() {
        return duration.get();
    }

    public SimpleIntegerProperty durationProperty() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration.set(duration);
    }

    public int getYear() {
        return year.get();
    }

    public SimpleIntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }


}