package com.jasionowicz.myarmybuilder.security.model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

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

}
