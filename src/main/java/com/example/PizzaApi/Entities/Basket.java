package com.example.PizzaApi.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "basket")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long user_id;
    @Column(nullable = false)
    private Long pizza_id;
}
