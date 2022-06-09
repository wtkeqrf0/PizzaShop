package com.example.PizzaApi.servises;

import com.example.PizzaApi.Entities.Pizza;
import com.example.PizzaApi.repos.PizzaRepo;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {
    private final PizzaRepo repo;

    public Pizza findPizza(Long id) throws ChangeSetPersister.NotFoundException {
        return repo.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public Pizza findPizza(String title) throws ChangeSetPersister.NotFoundException {
        return repo.findByTitle(title).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public List<Pizza> getPizzas() throws ChangeSetPersister.NotFoundException {
        return repo.findAll();
    }

    public void savePizza(Pizza pizza) {
        repo.save(pizza);
    }

    public void deletePizza(Long id) {
        repo.deleteById(id);
    }

    public PizzaService(PizzaRepo repo) {
        this.repo = repo;
    }
}
