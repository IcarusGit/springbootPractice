package com.registration.practice.service;

import com.registration.practice.model.Users;
import com.registration.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;

    // For password hashing / para sa database di ko makita yung password ng user
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 1. Register User
    public String registerUser(Users user){
        if (userRepository.existsByUsername(user.getUsername())){
            return "Username is already existing.";
        }

        if (userRepository.existsByEmail(user.getEmail())){
            return "Email is already existing";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "User registered successfully";
    }

    // 2. login user
    public Users loginUser(String username, String password){
        // Optional is a wrapper object that may or may not contain a value.
        // baka walang ireturn si User so optional to
        Optional<Users> loggingInUser = userRepository.findByUsername(username);

        if(loggingInUser.isEmpty()){
            return null;
        }

        // for cleaner code lang to para hindi na mag .get().getPassword()
        Users user = loggingInUser.get();

        // check hashed password
        if (!passwordEncoder.matches(password, user.getPassword())){
            return null;
        }

        return user;
    }
}
