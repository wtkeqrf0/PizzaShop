package com.PizzaApi.Controllers;

import com.PizzaApi.servises.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasAuthority('user:edit&read')")
public class AdminUserController {

    private final AdminService userService;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.findUser(id));

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @PutMapping("/ban/{id}")
    public ResponseEntity<?> banUser(@PathVariable Long id) {
        try {
            userService.Ban(id);
            return ResponseEntity.ok("Saved");

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @PutMapping("/unban/{id}")
    public ResponseEntity<?> unBan(@PathVariable Long id) {
        try {
            userService.unBan(id);
            return ResponseEntity.ok("Saved");

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    public AdminUserController(AdminService userService) {
        this.userService = userService;
    }
}