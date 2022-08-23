package com.PizzaApi.servises;

import com.PizzaApi.Entities.Pizza;
import com.PizzaApi.repos.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepo repo;

    public void findPizza(Long id) throws ChangeSetPersister.NotFoundException {
        repo.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public List<Pizza> getPizzas() throws ChangeSetPersister.NotFoundException {
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