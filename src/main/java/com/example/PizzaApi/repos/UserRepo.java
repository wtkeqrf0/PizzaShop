package com.example.PizzaApi.repos;

import com.example.PizzaApi.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String name);

    @Modifying
    @Query(value = "UPDATE users SET status= \"BANNED\" WHERE id= :user_id ",nativeQuery = true)
    void Ban(Long user_id);
}
