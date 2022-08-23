package com.PizzaApi.repos;

import com.PizzaApi.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET status='BANNED' WHERE id= :user_id ;", nativeQuery = true)
    void setBanned(Long user_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET status='ACTIVE' WHERE id= :user_id ;", nativeQuery = true)
    void setActive(Long user_id);
}
