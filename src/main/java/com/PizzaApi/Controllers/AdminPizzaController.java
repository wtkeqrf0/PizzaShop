package com.PizzaApi.Controllers;

import com.PizzaApi.Entities.Pizza;
import com.PizzaApi.servises.PizzaService;
import org.springframework.http.HttpStatus;
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
        return pizzaService.savePizza(pizza) ? ResponseEntity.ok("Saved") :
                new ResponseEntity<>("Insufficient data OR pizza already exists" +
                        "\nExpected: title, price, size, dough.", HttpStatus.EXPECTATION_FAILED);
    }

    @DeleteMapping("/{id}")
    public void deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
    }

    public AdminPizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }
}