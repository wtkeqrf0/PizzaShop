package com.PizzaApi.servises;

import com.PizzaApi.Entities.Pizza;
import com.PizzaApi.repos.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepo repo;

    public List<Pizza> findPizza(Long[] ids) {
        return repo.findByIds(ids);
    }

    public List<Pizza> getPizzas() {
        return repo.findAll();
    }

    public boolean savePizza(Pizza pizza) {
        List<Pizza> pizzas = repo.findAllByTitle(pizza.getTitle());

        for (Pizza p : pizzas) if (pizza.equals(p)) return false;
        repo.save(pizza);
        return true;
    }

    public void deletePizza(Long id) {
        repo.deleteById(id);
    }
}