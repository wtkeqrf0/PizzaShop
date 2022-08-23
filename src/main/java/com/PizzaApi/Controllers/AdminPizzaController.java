package com.PizzaApi.Controllers;

import com.PizzaApi.Entities.Pizza;
import com.PizzaApi.servises.PizzaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/pizzas")
@PreAuthorize("hasAuthority('user:edit&read')")
public class AdminPizzaController {

    private final PizzaService pizzaService;

    @PostMapping
    public ResponseEntity<?> addPizza(@RequestBody Pizza pizza) {
        return pizzaService.savePizza(pizza) ? ResponseEntity.ok("Saved") :
                ResponseEntity.badRequest().body("Insufficient data OR pizza already exists");
    }

    @DeleteMapping("/{id}")
    public void deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
    }

    public AdminPizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }
}