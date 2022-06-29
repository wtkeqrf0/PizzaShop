package com.PizzaApi.Controllers;

import com.PizzaApi.Entities.Basket;
import com.PizzaApi.Entities.User;
import com.PizzaApi.servises.AdminService;
import com.PizzaApi.servises.BasketService;
import com.PizzaApi.servises.PizzaService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;



@RestController
@RequestMapping("/basket")
@PreAuthorize("hasAuthority('user:buy')")
public class BasketController {

    private final BasketService basketService;
    private final PizzaService pizzaService;
    private final AdminService adminService;

    @PostMapping("/{pizza_id}")
    public ResponseEntity<?> add(@PathVariable Long pizza_id) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            pizzaService.findPizza(pizza_id);
            User user=adminService.findUser(userDetails.getUsername());
            basketService.addToBasket(new Basket(user.getId(),pizza_id));
            return ok("Saved");
        } catch (UsernameNotFoundException e) {
            return badRequest().body("User not found");
        } catch (ChangeSetPersister.NotFoundException e) {
            return badRequest().body("Pizza not found");
        }
    }

    @DeleteMapping("/{pizza_id}")
    public ResponseEntity<?> deleteOnePizza(@PathVariable Long pizza_id) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            pizzaService.findPizza(pizza_id);
            adminService.findUser(userDetails.getUsername());
            basketService.delete(pizza_id);
            return ok("Deleted");
        } catch (UsernameNotFoundException e) {
            return badRequest().body("User not found");
        } catch (ChangeSetPersister.NotFoundException e) {
            return badRequest().body("Pizza not found");
        }
    }

    @GetMapping
    public ResponseEntity<?> getBasket() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user=adminService.findUser(userDetails.getUsername());
            return ok(basketService.findAll(user.getId()));
        } catch (UsernameNotFoundException e) {
            return badRequest().body("User not found");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllPizzas() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user=adminService.findUser(userDetails.getUsername());
            basketService.deleteAll(user.getId());
            return ok("Cleared");
        } catch (UsernameNotFoundException e) {
            return badRequest().body("User not found");
        }
    }

    public BasketController(BasketService basketService, PizzaService pizzaService, AdminService adminService) {
        this.basketService = basketService;
        this.pizzaService = pizzaService;
        this.adminService = adminService;
    }
}