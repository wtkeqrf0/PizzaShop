package com.PizzaApi.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "basket")
@NoArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long user_id;
    @Column(nullable = false)
    private Long pizza_id;

    public Basket(Long user_id,Long pizza_id) {
        this.user_id=user_id;
        this.pizza_id=pizza_id;
    }
}
