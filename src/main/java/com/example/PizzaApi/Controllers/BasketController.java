package com.example.PizzaApi.Controllers;

import com.example.PizzaApi.Entities.Basket;
import com.example.PizzaApi.servises.AdminService;
import com.example.PizzaApi.servises.BasketService;
import com.example.PizzaApi.servises.PizzaService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;



@RestController
@RequestMapping("/basket")
@PreAuthorize("hasAuthority('user:buy')")
public class BasketController {

    private final AdminService adminService;
    private final BasketService basketService;
    private final PizzaService pizzaService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Basket basket) {
        try {
            adminService.findUser(basket.getUser_id());
            pizzaService.findPizza(basket.getPizza_id());
            basketService.addToBasket(basket);
            return ok("Saved");
        } catch (UsernameNotFoundException e) {
            return badRequest().body("User not found");
        } catch (ChangeSetPersister.NotFoundException e) {
            return badRequest().body("Pizza not found");
        }
    }

    @PostMapping("/del")
    public ResponseEntity<?> deleteOnePizza(@RequestBody Basket basket) {
        try {
            basketService.delete(basket);
            return ok("Deleted");
        } catch (UsernameNotFoundException e) {
            return badRequest().body("User not found");
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getBasket(@PathVariable Long user_id) {
        try {
            return ok(basketService.findAllByUser_id(user_id));
        } catch (UsernameNotFoundException e) {
            return badRequest().body("User not found");
        }
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteAllPizzas(@PathVariable Long user_id) {
        try {
            basketService.deleteAllByUser_id(user_id);
            return ok("Cleared");
        } catch (UsernameNotFoundException e) {
            return badRequest().body("User not found");
        }
    }

    public BasketController(AdminService adminService,BasketService basketService, PizzaService pizzaService) {
        this.adminService = adminService;
        this.basketService = basketService;
        this.pizzaService = pizzaService;
    }
}