package com.example.PizzaApi.servises;

import com.example.PizzaApi.Enums.Status;
import com.example.PizzaApi.Entities.User;
import com.example.PizzaApi.repos.UserRepo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final UserRepo repo;

    public User findUser(Long id) throws UsernameNotFoundException {
            return repo.findById(id).orElseThrow(()->new UsernameNotFoundException(""));
    }

    public User findUser(String email) throws UsernameNotFoundException {
            return repo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(""));
    }

    public void Ban(Long id) throws UsernameNotFoundException {
        repo.findById(id).orElseThrow(()->new UsernameNotFoundException(""));
            repo.Ban(id);
    }

    public void saveUser(User user) {
        repo.save(user);
    }

    public Iterable<User> getUsers() {
        return repo.findAll();
    }

    public AdminService(UserRepo repo) {
        this.repo = repo;
    }
}
