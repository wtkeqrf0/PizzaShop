package com.example.PizzaApi.repos;

import com.example.PizzaApi.Entities.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BasketRepo extends JpaRepository<Basket,Long> {
    @Query(value = "SELECT * FROM basket WHERE basket.user_id= :user_id",nativeQuery = true)
    List<Basket> findAllByUser_id(@Param("user_id") Long user_id);

    @Modifying
    @Query(value = "DELETE FROM basket WHERE basket.user_id= :user_id",nativeQuery = true)
    void deleteAllByUser_id(@Param("user_id") Long user_id);
}
