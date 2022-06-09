package com.example.PizzaApi.Controllers;

import com.example.PizzaApi.Entities.User;
import com.example.PizzaApi.Enums.Role;
import com.example.PizzaApi.Enums.Status;
import com.example.PizzaApi.models.AuthenticationRequestDTO;
import com.example.PizzaApi.securities.JwtTokenProvider;
import com.example.PizzaApi.servises.AdminService;
import com.example.PizzaApi.servises.PizzaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @Value("${auth.register}")
    String mainCode;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
            User user=userService.findUser(request.getEmail());
            Map<Object,Object> response=new HashMap<>();
            response.put("token",jwtTokenProvider.createToken(request.getEmail(),user.getRole().name()));
            response.put("id",user.getId());
            response.put("email",user.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationRequestDTO request) {
        try {
            userService.findUser(request.getEmail());
            return ResponseEntity.badRequest().body("User already exists");
        } catch (UsernameNotFoundException e) {
            User user=new User();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(mainCode.equals(passwordEncoder.encode(request.getCode()))?Role.ADMIN:Role.USER);
            user.setStatus(Status.ACTIVE);

            userService.saveUser(user);
            return ResponseEntity.ok("Регистрация прошла успешно");
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler=new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request,response,null);
    }

    @GetMapping("/pizzas")
    public ResponseEntity<?> showMenu() {
        try {
            return ResponseEntity.ok(pizzaService.getPizzas());
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.badRequest().body("Menu doesn't exist");
        }
    }

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder bCryptPasswordEncoder, AdminService userService, PizzaService pizzaService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = (BCryptPasswordEncoder) bCryptPasswordEncoder;
        this.userService = userService;
        this.pizzaService = pizzaService;
    }
}
