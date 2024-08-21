package com.jasionowicz.myarmybuilder.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    @Autowired
    private UserLoginService userLoginService;


    @GetMapping
    public ModelAndView showRegistrationForm() {
        return new ModelAndView("register");
    }

    @PostMapping
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String confirmPassword, @RequestParam String email, @RequestParam String confirmEmail, Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("passwordError", "Passwords do not match.");
            return "register";
        }
        if (!email.equals(confirmEmail)) {
            model.addAttribute("emailError", "Emails do not match.");
            return "register";
        }

        boolean registrationSuccess = userLoginService.makeNewUser(username, password, email);

        if (registrationSuccess) {
            return "redirect:/login";
        } else {
            model.addAttribute("registrationError", "User registration failed.");
            return "register";
        }

    }
}
