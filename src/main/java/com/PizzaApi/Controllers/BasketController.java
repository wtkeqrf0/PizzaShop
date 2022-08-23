package com.PizzaApi.Controllers;

import com.PizzaApi.servises.PizzaService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;



@RestController
@RequestMapping("/basket")
@PreAuthorize("hasAuthority('user:buy')")
public class BasketController {

    private final PizzaService pizzaService;

    @PostMapping("/{pizza_id}")
    public ResponseEntity<?> add(@PathVariable Long pizza_id, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();

            pizzaService.findPizza(pizza_id);

            Cookie[] cookies = request.getCookies(); //username is email
            if (Arrays.stream(cookies).anyMatch(e -> e.getName().equals(userDetails.getUsername()))) {

                Cookie cookie = Arrays.stream(cookies).filter(e ->
                        e.getName().equals(userDetails.getUsername())).findFirst().get();
                cookie.setValue(cookie.getValue() + " " + pizza_id);
                response.addCookie(cookie);
            } else {
                Cookie cookie = new Cookie(userDetails.getUsername(), String.valueOf(pizza_id));

                cookie.setMaxAge(-1);
                response.addCookie(cookie);
            }

            return ok("Saved");

        } catch (ChangeSetPersister.NotFoundException e) {
            return badRequest().body("Pizza doesn't exist");
        }
    }

    @DeleteMapping("/{pizza_id}")
    public ResponseEntity<?> deleteOnePizza(@PathVariable Long pizza_id, HttpServletRequest request, HttpServletResponse response) {
        try {
            pizzaService.findPizza(pizza_id);

            UserDetails userDetails = (UserDetails) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();

            Cookie[] cookies = request.getCookies();
            Cookie cookie = Arrays.stream(cookies).filter(e ->
                    e.getName().equals(userDetails.getUsername())).
                    findFirst().orElse(new Cookie("", ""));

            ArrayList<String> basket = (ArrayList<String>) Stream.of(cookie.getValue().split(" ")).
                    filter(e -> !Long.valueOf(e).equals(pizza_id)).collect(Collectors.toList());

            cookie.setValue(String.join(" ", basket));
            response.addCookie(cookie);

            return ok("Saved");

        } catch (ChangeSetPersister.NotFoundException e) {
            return badRequest().body("Pizza doesn't exist");
        }
    }

    @GetMapping
    public ResponseEntity<?> getBasket(HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        Cookie[] cookies = request.getCookies();

        return ok(Arrays.stream(cookies).filter(e -> e.getName().equals(
                userDetails.getUsername())).findFirst().orElse(new Cookie("", "")).getValue().split(" "));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllPizzas(HttpServletResponse response) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        Cookie cookie = new Cookie(userDetails.getUsername(), "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ok("Cleared");
    }

    public BasketController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }
}