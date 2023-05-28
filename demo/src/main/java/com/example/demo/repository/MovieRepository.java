package com.example.demo.repository;


import com.example.demo.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Movie findById(int id);
    List<Movie> findAll();
    Movie save(Movie movie);
    void delete(Movie movie);

}