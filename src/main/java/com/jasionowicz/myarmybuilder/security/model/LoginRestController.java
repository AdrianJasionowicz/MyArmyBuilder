package com.jasionowicz.myarmybuilder.security.model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRestController {

     BuilderUserService builderUserService;

    public LoginRestController(BuilderUserService builderUserService) {
        this.builderUserService = builderUserService;
    }

}
