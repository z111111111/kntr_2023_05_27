package com.example.demo_gui;

import javafx.application.Application;

import javafx.stage.Modality;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MovieClientApplication extends Application {

    private static final String BASE_URL = "http://localhost:8080/movies";

    private TableView<Movie> tableView;
    private ObservableList<Movie> movieList;

    public static void main(String[] args) {
        launch(args);
    }

    private void refreshMovies() {
        try {
            // Создание URL для запроса
            URL url = new URL(BASE_URL);
            //Gson gson = new Gson();

            // Создание соединения
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Чтение ответа от сервера
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String jsonResponse = reader.readLine();

            reader.close();
            connection.disconnect();

            System.out.println(jsonResponse);

            // Преобразование JSON-ответа в список фильмов
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(jsonResponse);

            List<Movie> movies = new ArrayList<>();
            for (Object obj : jsonArray) {
                JSONObject movieJson = (JSONObject) obj;
                // Преобразование каждого JSON-объекта в объект Movie
                Movie movie = new Movie((Long) movieJson.get("id"), (String) movieJson.get("name"), (String) movieJson.get("genre"), (Long) movieJson.get("duration"), (Long) movieJson.get("year"));
                movies.add(movie);
            }

            // Обновление списка фильмов в таблице
            movieList = FXCollections.observableArrayList(movies);
            tableView.setItems(movieList);

            // Закрытие ридера и соединения
            reader.close();
            connection.disconnect();
        }  catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMovieToServer(MovieToSend movie) {
        try {
            // Создаем URL для API endpoint'а добавления фильма
            URL url = new URL(BASE_URL);

            // Открываем соединение
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Преобразуем объект Movie в JSON
            JSONObject jsonInputObject = new JSONObject();
            jsonInputObject.put("name", movie.getName());
            jsonInputObject.put("genre", movie.getGenre());
            jsonInputObject.put("duration", movie.getDuration());
            jsonInputObject.put("year", movie.getYear());

            // Отправляем данные на сервер
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputObject.toJSONString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Получаем код ответа от сервера
            int responseCode = conn.getResponseCode();

            // Обработка ответа сервера
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                // Читаем данные ответа сервера


                    System.out.println("Фильм успешно добавлен на сервер");
                    // Дополнительные действия при успешном добавлении фильма

            } else {
                System.out.println("Ошибка при добавлении фильма. Код ответа сервера: " + responseCode);
                // Обработка ошибки при добавлении фильма
            }

            // Закрываем соединение
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            // Обработка исключения
        }
    }

    private void delMovieFromServer(int film_id) {
        try {
            // Создаем URL для API endpoint'а добавления фильма
            URL url = new URL(BASE_URL+"/"+film_id);

            // Открываем соединение
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Преобразуем объект Movie в JSON
            JSONObject jsonInputObject = new JSONObject();

            // Отправляем данные на сервер
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputObject.toJSONString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Получаем код ответа от сервера
            int responseCode = conn.getResponseCode();

            // Обработка ответа сервера
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                // Читаем данные ответа сервера


                System.out.println("Фильм успешно удален");
                // Дополнительные действия при успешном добавлении фильма

            } else {
                System.out.println("Ошибка при удалении фильма. Код ответа сервера: " + responseCode);
                // Обработка ошибки при добавлении фильма
            }

            // Закрываем соединение
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            // Обработка исключения
        }
    }

    @FXML
    public void deleteMovie() {
        try {
            // Создаем новое окно для формы добавления фильма
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("delete_movie_dialog.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));

            // Получаем контроллер формы добавления фильма
            DeleteMovieController deleteMovieController = loader.getController();

            // Устанавливаем обработчик события добавления фильма
            deleteMovieController.setDeleteMovieCallback(film_id -> {
                // Отправляем фильм на сервер
                delMovieFromServer(film_id);

                // Закрываем окно добавления фильма
                stage.close();

                // Обновляем список фильмов на главном экране
                refreshMovies();
            });

            // Отображаем окно добавления фильма
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Обработка исключения
        }
    }
    @FXML
    public void addMovie() {
        try {
        // Создаем новое окно для формы добавления фильма
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add_movie.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));

        // Получаем контроллер формы добавления фильма
        AddMovieController addMovieController = loader.getController();

        // Устанавливаем обработчик события добавления фильма
        addMovieController.setAddMovieCallback(movie -> {
            // Отправляем фильм на сервер
            sendMovieToServer(movie);

            // Закрываем окно добавления фильма
            stage.close();

            // Обновляем список фильмов на главном экране
            refreshMovies();
        });

        // Отображаем окно добавления фильма
        stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Обработка исключения
        }
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Movie Client");

        // Создание таблицы для отображения списка фильмов
        tableView = new TableView<>();
        movieList = FXCollections.observableArrayList();
        tableView.setItems(movieList);

        // Создание столбцов таблицы
        TableColumn<Movie, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());


        TableColumn<Movie, String> nameColumn = new TableColumn<>("Наименование");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Movie, String> genreColumn = new TableColumn<>("Жанр");
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());

        TableColumn<Movie, Long> durationColumn = new TableColumn<>("Длительность");
        durationColumn.setCellValueFactory(cellData -> cellData.getValue().durationProperty().asObject());

        TableColumn<Movie, Long> yearColumn = new TableColumn<>("Год выхода");
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());

        tableView.getColumns().addAll(idColumn, nameColumn, genreColumn, durationColumn, yearColumn);

        // Создание кнопок для действий
        Button refreshButton = new Button("Обновить");
        Button addButton = new Button("Добавить");
        Button editButton = new Button("Редактировать");
        Button deleteButton = new Button("Удалить");

        // Добавление обработчиков событий для кнопок
        refreshButton.setOnAction(event -> refreshMovies());
        addButton.setOnAction(event -> addMovie());
        //editButton.setOnAction(event -> editMovie());
        deleteButton.setOnAction(event -> deleteMovie());

        // Создание панели с кнопками
        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10);
        buttonPane.setPadding(new Insets(10));
        buttonPane.add(refreshButton, 0, 0);
        buttonPane.add(addButton, 1, 0);
        buttonPane.add(editButton, 2, 0);
        buttonPane.add(deleteButton, 3, 0);

        // Создание корневого контейнера и размещение элементов
        BorderPane root = new BorderPane();
        root.setCenter(tableView);
        root.setBottom(buttonPane);

        // Создание сцены и установка на primaryStage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // При запуске приложения, загружаем список фильмов с сервера
        refreshMovies();
    }

}