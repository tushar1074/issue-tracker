package com.issuetracker.issuetracker.security;

import com.issuetracker.issuetracker.model.User;
import com.issuetracker.issuetracker.repository.UserRepository;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo){
        this.userRepo=userRepo;
    }

    public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException{
        User user=userRepo.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
