package com.jasionowicz.myarmybuilder.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository validUserRepository;
    private User user;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository validUserRepository, PasswordEncoder passwordEncoder) {
        this.validUserRepository = validUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserService(User user, PasswordEncoder passwordEncoder) {
        this.user = user;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> makeNewValidUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        validUserRepository.save(user);
        return ResponseEntity.ok().body("User created");
    }

    public void userLogin(String username, String password) {
        User user = validUserRepository.findByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) {

        }
    }
}
