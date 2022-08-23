package com.PizzaApi.servises;

import com.PizzaApi.Entities.User;
import com.PizzaApi.Enums.Role;
import com.PizzaApi.repos.UserRepo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final UserRepo repo;

    public User findUser(Long id) throws UsernameNotFoundException {
        return repo.findById(id).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    public User findUser(String email) throws UsernameNotFoundException {
        return repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    public void Ban(Long id) throws UsernameNotFoundException {
        if (repo.findById(id).get().getRole() == Role.ADMIN)
            throw new UsernameNotFoundException("Impossible to ban a non-existent account or admin");

        repo.setBanned(id);
    }

    public void unBan(Long id) throws UsernameNotFoundException {
        repo.setActive(id);
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