package com.PizzaApi.Controllers;

import com.PizzaApi.servises.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@PreAuthorize("hasAuthority('user:buy')")
public class AddressController {

    private final AdminService userService;

    @PostMapping
    public ResponseEntity<?> postAddress(@RequestParam String address) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        return userService.addAddress(address, userDetails.getUsername()) ? ResponseEntity.ok("Saved")
                : new ResponseEntity<>("No data found", HttpStatus.FORBIDDEN);
    }

    @GetMapping
    public ResponseEntity<?> getAddress() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        String address = userService.getAddress(userDetails.getUsername());

        return address == null ? new ResponseEntity<>("No data found", HttpStatus.FORBIDDEN)
                : ResponseEntity.ok(address);
    }

    @DeleteMapping
    public ResponseEntity<?> dropAddress() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        return userService.dropAddress(userDetails.getUsername()) ? ResponseEntity.ok("Saved")
                : new ResponseEntity<>("No data found", HttpStatus.FORBIDDEN);
    }

    public AddressController(AdminService userService) {
        this.userService = userService;
    }
}