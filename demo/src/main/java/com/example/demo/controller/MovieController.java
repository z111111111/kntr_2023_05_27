package com.example.demo.controller;

import com.example.demo.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.MovieRepository;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> get(@PathVariable Integer id){
        try{
            Movie movie = movieRepository.findById(id).get();
            return new ResponseEntity<>(movie, HttpStatus.OK);

        }catch (NoSuchElementException e){
            return new ResponseEntity<Movie>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie addedMovie = movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable int id) {
        Movie movie = movieRepository.findById(id);
        if (movie != null) {
            movieRepository.delete(movie);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> editMovie(@PathVariable int id, @RequestBody Movie updatedMovie) {
        Movie movie = movieRepository.findById(id);
        if (movie != null) {
            movie.setName(updatedMovie.getName());
            movie.setGenre(updatedMovie.getGenre());
            movie.setDuration(updatedMovie.getDuration());
            movie.setYear(updatedMovie.getYear());
            Movie editedMovie = movieRepository.save(movie);
            return ResponseEntity.ok(editedMovie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}