package com.jasionowicz.myarmybuilder.security.model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRestController {

     BuilderUserService builderUserService;

    public LoginRestController(BuilderUserService builderUserService) {
        this.builderUserService = builderUserService;
    }

    @GetMapping("/dej")
    public BuilderUser isUserLogged() {
        System.out.println(builderUserService.getLoggedUser());
       return builderUserService.getLoggedUser().orElseThrow();

    }
}
