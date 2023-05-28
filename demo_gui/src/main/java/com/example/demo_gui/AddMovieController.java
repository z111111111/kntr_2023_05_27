package com.example.demo_gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddMovieController {

    @FXML
    private TextField titleTextField;
    @FXML
    private ComboBox genreComboBox;
    @FXML
    private TextField durationTextField;
    @FXML
    private TextField yearTextField;

    private AddMovieCallback addMovieCallback;

    public void setAddMovieCallback(AddMovieCallback callback) {
        this.addMovieCallback = callback;
    }

    @FXML
    public void addMovie() {
        String title = titleTextField.getText();
        String genre = (String) genreComboBox.getSelectionModel().getSelectedItem();
        int duration = Integer.parseInt(durationTextField.getText());
        int year = Integer.parseInt(yearTextField.getText());

        // Создаем новый объект Movie
        MovieToSend movie = new MovieToSend(title, genre, duration, year);

        // Вызываем обработчик добавления фильма
        addMovieCallback.onAddMovie(movie);
    }

    @FXML
    public void goBack() {
        // Закрываем окно добавления фильма
        Stage stage = (Stage) titleTextField.getScene().getWindow();
        stage.close();
    }

    public interface AddMovieCallback {
        void onAddMovie(MovieToSend movie);
    }
}