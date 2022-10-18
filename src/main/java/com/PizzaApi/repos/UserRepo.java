package com.PizzaApi.repos;

import com.PizzaApi.Entities.Pizza;
import com.PizzaApi.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByPhoneNumber(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET status='BANNED' WHERE id= :user_id ;", nativeQuery = true)
    void setBanned(Long user_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET status='ACTIVE' WHERE id= :user_id ;", nativeQuery = true)
    void setActive(Long user_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET address = :address WHERE phone_number = :phoneNumber ;", nativeQuery = true)
    void setAddress(String address,String phoneNumber);

    @Query(value = "SELECT address FROM users WHERE phone_number = :phoneNumber ;", nativeQuery = true)
    Optional<String> getAddress(String phoneNumber);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET address = NULL WHERE phone_number = :phoneNumber ;", nativeQuery = true)
    void deleteAddress(String phoneNumber);

    @Query(value = "SELECT * FROM menu WHERE menu.id IN (:ids);", nativeQuery = true)
    List<User> findByIds(@Param("ids") Long[] ids);
}
