package com.example.PizzaApi.Controllers;

import com.example.PizzaApi.Entities.Pizza;
import com.example.PizzaApi.servises.PizzaService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/pizza")
@PreAuthorize("hasAuthority('user:edit&read')")
public class AdminPizzaController {

    private final PizzaService pizzaService;

    @PostMapping
    public ResponseEntity<?> addPizza(@RequestBody Pizza pizza) {
        try {
            if ((!pizzaService.findPizza(pizza.getTitle()).equals(pizza))) throw new ChangeSetPersister.NotFoundException();
            pizzaService.savePizza(pizza);
            return ResponseEntity.ok("Saved");
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.badRequest().body("Pizza already exists");
        }
    }

    @DeleteMapping("/{id}")
    public void deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
    }

    public AdminPizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }
}
