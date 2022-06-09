package com.example.PizzaApi.repos;

import com.example.PizzaApi.Entities.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzaRepo extends JpaRepository<Pizza,Long> {
    Optional<Pizza> findByTitle(String title);
}