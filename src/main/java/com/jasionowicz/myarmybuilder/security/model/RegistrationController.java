package com.jasionowicz.myarmybuilder.security.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @Autowired
    private BuilderUserRepository builderUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public BuilderUser createUser(@RequestBody BuilderUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return builderUserRepository.save(user);
    }



}
