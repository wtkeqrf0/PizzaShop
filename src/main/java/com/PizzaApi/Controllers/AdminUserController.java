package com.PizzaApi.Controllers;

import com.PizzaApi.servises.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@PreAuthorize("hasAuthority('user:edit&read')")
public class AdminUserController {

    private final AdminService userService;

    @GetMapping
    public ResponseEntity<?> showUsers(@RequestParam(required = false,value = "id") Long[] ids) {
        return ResponseEntity.ok(ids == null ?
                userService.getUsers(): userService.findUsers(ids));
    }

    @PutMapping("/ban/{id}")
    public ResponseEntity<?> banUser(@PathVariable Long id) {
        try {
            userService.Ban(id);
            return ResponseEntity.ok("Saved");

        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("No data found", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/unban/{id}")
    public ResponseEntity<?> unBan(@PathVariable Long id) {
        try {
            userService.unBan(id);
            return ResponseEntity.ok("Saved");

        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("No data found", HttpStatus.FORBIDDEN);
        }
    }

    public AdminUserController(AdminService userService) {
        this.userService = userService;
    }
}