package com.PizzaApi.Controllers;

import com.PizzaApi.Entities.User;
import com.PizzaApi.Enums.Role;
import com.PizzaApi.Enums.Status;
import com.PizzaApi.models.AuthenticationRequestDTO;
import com.PizzaApi.securities.JwtTokenProvider;
import com.PizzaApi.servises.AdminService;
import com.PizzaApi.servises.PizzaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class AuthController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AdminService userService;
    private final PizzaService pizzaService;

    @Value("${auth.register.admin}")
    String mainCode;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO request) {
        try {
            User user = userService.findUser(request.getPhoneNumber());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword()));

            Map<Object, Object> response = new HashMap<>();

            response.put("token", jwtTokenProvider.createToken(request.getPhoneNumber(), user.getRole().name()));
            response.put("id", user.getId());

            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("Register before sign up", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid phone/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationRequestDTO request) {
        try {
            userService.findUser(request.getPhoneNumber());
            return new ResponseEntity<>("This phone already exists", HttpStatus.UNAUTHORIZED);

        } catch (UsernameNotFoundException e) {
            User user = new User();
            user.setPhoneNumber(request.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            if (request.getCode() == null || request.getCode().isBlank()) user.setRole(Role.USER);

            else user.setRole(passwordEncoder.matches(request.getCode(), mainCode) ? Role.ADMIN : Role.USER);

            user.setStatus(Status.ACTIVE);
            userService.saveUser(user);

            return ResponseEntity.ok("Saved");
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @GetMapping("/pizza")
    public ResponseEntity<?> showMenu(@RequestParam(required = false,value = "id") Long[] ids) {
        return ResponseEntity.ok(ids == null ?
                pizzaService.getPizzas(): pizzaService.findPizza(ids));
    }


    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder bCryptPasswordEncoder, AdminService userService, PizzaService pizzaService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = (BCryptPasswordEncoder) bCryptPasswordEncoder;
        this.userService = userService;
        this.pizzaService = pizzaService;
    }
}