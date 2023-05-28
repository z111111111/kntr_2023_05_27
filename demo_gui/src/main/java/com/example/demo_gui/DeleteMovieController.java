package com.example.demo_gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeleteMovieController {
    @FXML
    private TextField idTextField;

    private DeleteMovieCallback deleteMovieCallback;

    public void setDeleteMovieCallback(DeleteMovieCallback deleteMovieCallback) {
        this.deleteMovieCallback = deleteMovieCallback;
    }

    @FXML
    public void deleteMovie() {
        int movieId = Integer.parseInt(idTextField.getText());

        // Вызываем обработчик удаления фильма
        deleteMovieCallback.onDeleteMovie(movieId);

        // Закрываем диалоговое окно
        closeWindow();
    }

    @FXML
    public void cancel() {
        // Закрываем диалоговое окно
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) idTextField.getScene().getWindow();
        stage.close();
    }

    public interface DeleteMovieCallback {
        void onDeleteMovie(int movieId);
    }
}