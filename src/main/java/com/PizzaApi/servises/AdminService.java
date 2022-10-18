package com.PizzaApi.servises;

import com.PizzaApi.Entities.User;
import com.PizzaApi.Enums.Role;
import com.PizzaApi.repos.UserRepo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserRepo repo;

    public List<User> findUsers(Long[] ids) {
        return repo.findByIds(ids);
    }

    public boolean addAddress(String address,String phoneNumber) {
        if (repo.findByPhoneNumber(phoneNumber).isEmpty()) return false;
        repo.setAddress(address, phoneNumber);
        return true;
    }

    public String getAddress(String phoneNumber) {
        return repo.getAddress(phoneNumber).orElse(null);
    }

    public boolean dropAddress(String phoneNumber) {
        if (repo.findByPhoneNumber(phoneNumber).isEmpty()) return false;
        repo.deleteAddress(phoneNumber);
        return true;
    }

    public User findUser(String phoneNumber) throws UsernameNotFoundException {
        return repo.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    public void Ban(Long id) throws UsernameNotFoundException {
        if (repo.findById(id).get().getRole() == Role.ADMIN)
            throw new UsernameNotFoundException("");

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