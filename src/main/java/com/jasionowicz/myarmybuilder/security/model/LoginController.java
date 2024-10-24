package com.jasionowicz.myarmybuilder.security.model;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private final BuilderUserService builderUserService;

    public LoginController(BuilderUserService builderUserService) {
        this.builderUserService = builderUserService;
    }

    @GetMapping("/login")
    public String loadLoginPage() {
        return "login";
    }

    @GetMapping("/user/home")
    public String loadHomePage() {
        return "menu";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }
    @GetMapping("/")
    public String showMenu() {
        return "menu";
    }

    @GetMapping("/isUserLogged")
        public String isUserLogged() {
        System.out.println(builderUserService.getLoggedUser());
        return "menu";
    }
}
