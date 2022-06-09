package com.example.PizzaApi.servises;

import com.example.PizzaApi.Entities.Basket;
import com.example.PizzaApi.repos.BasketRepo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private final BasketRepo basketRepo;

    public Map<Long, Integer> findAllByUser_id(Long user_id) throws UsernameNotFoundException{
            return basketRepo.findAllByUser_id(user_id).stream().collect(Collectors.toMap(Basket::getPizza_id, e -> 1, Integer::sum));
    }
    public void deleteAllByUser_id(Long user_id) {
        basketRepo.deleteAllByUser_id(user_id);
    }

    public void addToBasket(Basket basket) {
        basketRepo.save(basket);
    }

    public void delete(Basket basket) {
        basketRepo.delete(basket);
    }

    public BasketService(BasketRepo basketRepo) {
        this.basketRepo = basketRepo;
    }
}
