package com.PizzaApi.Entities;

import com.PizzaApi.Enums.Dough;
import com.PizzaApi.Enums.Size;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "menu")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Integer price;

    private String url;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private Dough dough;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        return Objects.equals(title,pizza.title) &&
                Objects.equals(price, pizza.price) &&
                size == pizza.size && dough == pizza.dough;
    }
}