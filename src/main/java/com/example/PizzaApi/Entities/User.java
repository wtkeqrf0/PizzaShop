package com.example.PizzaApi.Entities;

import com.example.PizzaApi.Enums.Role;
import com.example.PizzaApi.Enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 50,unique = true)
    private String email;
    @Column(nullable = false,length = 100)
    private String password;
    @Column(length = 35)
    private String username;
    @Column(nullable = false,length = 10)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false,length = 10)
    @Enumerated(EnumType.STRING)
    private Status status;
}

