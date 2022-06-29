package com.PizzaApi.servises;

import com.PizzaApi.repos.BasketRepo;
import com.PizzaApi.Entities.Basket;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private final BasketRepo basketRepo;

    public List<Long> findAll(Long user_id) {
            return basketRepo.findAllByUser_id(user_id).stream().map(Basket::getPizza_id).collect(Collectors.toList());
    }
    public void deleteAll(Long user_id) {
        basketRepo.deleteAllByUser_id(user_id);
    }

    public void addToBasket(Basket basket) {
        basketRepo.save(basket);
    }

    public void delete(Long pizza_id) {
        basketRepo.deleteOne(pizza_id);
    }

    public BasketService(BasketRepo basketRepo) {
        this.basketRepo = basketRepo;
    }
}
