package com.issuetracker.issuetracker.service;

import com.issuetracker.issuetracker.model.User;
import com.issuetracker.issuetracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder){
        this.userRepo=userRepo;
        this.passwordEncoder=passwordEncoder;
    }

    public void registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); // not ROLE_USER
        userRepo.save(user);
    }

    public boolean emailExists(String email){
        return userRepo.findByEmail(email).isPresent();
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

}
