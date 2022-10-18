package com.PizzaApi.repos;

import com.PizzaApi.Entities.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PizzaRepo extends JpaRepository<Pizza,Long> {
    @Query(value = "SELECT * FROM menu WHERE menu.title= :title ;", nativeQuery = true)
    List<Pizza> findAllByTitle(@Param("title") String title);

    @Query(value = "SELECT * FROM menu WHERE menu.id IN (:ids);", nativeQuery = true)
    List<Pizza> findByIds(@Param("ids") Long[] ids);
}