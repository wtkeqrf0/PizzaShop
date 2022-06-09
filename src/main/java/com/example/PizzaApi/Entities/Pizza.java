package com.example.PizzaApi.Entities;

import com.example.PizzaApi.Enums.Dough;
import com.example.PizzaApi.Enums.Size;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "menu")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,length = 50,nullable = false)
    private String title;
    private String description;
    private String url;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false,length = 10)
    @Enumerated(EnumType.STRING)
    private Size size;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 15)
    private Dough dough;
}