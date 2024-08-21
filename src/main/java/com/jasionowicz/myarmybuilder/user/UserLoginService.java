package com.jasionowicz.myarmybuilder.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoginService {
    private final UserLoginRepository userLoginRepository;
    private UserLoginRepository userRepository;
    private UserLogin loggedUser = null;

    public UserLoginService(UserLoginRepository userRepository, UserLoginRepository userLoginRepository) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
    }


    public Optional<UserLogin> getLoggedUser() {
        return Optional.ofNullable(loggedUser);
    }

    public boolean login(String username, String password) {
        Optional<UserLogin> optionalUser = userRepository.getUserByUsername(username);

        if (optionalUser.isEmpty()) {
            return false;
        }
        UserLogin user = optionalUser.get();
        if (!user.getPassword().equals(password)) {
            return false;
        }


        loggedUser = user;
        return true;
    }

    public ResponseEntity<String> setUsername(String newUsername) {
        UserLogin userLogin = userLoginRepository.getReferenceById(loggedUser.getId());
        userLogin.setUsername(newUsername);
        userLoginRepository.save(userLogin);
        return ResponseEntity.ok().body("Username changed successfully");
    }

    public ResponseEntity<String> setPassword(String newPassword) {
        UserLogin userLogin = userLoginRepository.getReferenceById(loggedUser.getId());
        userLogin.setPassword(newPassword);
        userLoginRepository.save(userLogin);
        return ResponseEntity.ok().body("Password changed successfully");
    }

    public ResponseEntity<String> setEmail(String newEmail) {
        UserLogin userLogin = userLoginRepository.getReferenceById(loggedUser.getId());
        userLogin.setEmail(newEmail);
        userLoginRepository.save(userLogin);
        return ResponseEntity.ok().body("Email changed successfully");
    }

    public boolean makeNewUser(String username,String password,String email) {
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername(username);
        userLogin.setPassword(password);
        userLogin.setEmail(email);
        userLoginRepository.save(userLogin);
        return true;

    }
}
