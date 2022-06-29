package com.PizzaApi.repos;

import com.PizzaApi.Entities.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzaRepo extends JpaRepository<Pizza,Long> {
    Optional<Pizza> findByTitle(String title);
}