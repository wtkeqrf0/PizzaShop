package com.PizzaApi.repos;

import com.PizzaApi.Entities.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BasketRepo extends JpaRepository<Basket,Long> {
    @Query(value = "SELECT * FROM basket WHERE user_id= :user_id ;",nativeQuery = true)
    List<Basket> findAllByUser_id(@Param("user_id") Long user_id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM basket WHERE user_id= :user_id ;",nativeQuery = true)
    void deleteAllByUser_id(@Param("user_id") Long user_id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM basket WHERE ctid IN (SELECT ctid FROM basket WHERE pizza_id=:pizza_id LIMIT 1);"
            ,nativeQuery = true)
    void deleteOne(@Param("pizza_id") Long pizza_id);
}
