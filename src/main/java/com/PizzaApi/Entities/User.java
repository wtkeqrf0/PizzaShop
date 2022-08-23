package com.PizzaApi.Entities;

import com.PizzaApi.Enums.Role;
import com.PizzaApi.Enums.Status;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(length = 35)
    private String username;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Status status;
}